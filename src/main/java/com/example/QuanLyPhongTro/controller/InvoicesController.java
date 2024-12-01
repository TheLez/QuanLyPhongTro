package com.example.QuanLyPhongTro.controller;

import com.example.QuanLyPhongTro.config.VNPayConfig;
import com.example.QuanLyPhongTro.models.Rooms;
import com.example.QuanLyPhongTro.payload.InvoiceRequest;
import com.example.QuanLyPhongTro.services.EmailService;
import com.example.QuanLyPhongTro.services.RoomsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongTro.models.Invoices;
import com.example.QuanLyPhongTro.services.InvoicesService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/invoices")
public class InvoicesController {

    @Autowired
    private InvoicesService _invoicesService;

    @Autowired
    private RoomsService _roomsService;

    @Autowired(required = false)
    private EmailService emailService;

    // Lấy tất cả hóa đơn
    @GetMapping("/all")  // Thay đổi đường dẫn để tránh xung đột
    public List<Invoices> getAllInvoices() {
        return _invoicesService.getAllInvoices();
    }

    // Lấy hóa đơn theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Invoices> getInvoiceById(@PathVariable int id) {
        Invoices invoice = _invoicesService.getInvoiceById(id);
        if (invoice != null) {
            return ResponseEntity.ok(invoice);
        }
        return ResponseEntity.notFound().build();
    }

    // Lấy hóa đơn với phân trang
    @GetMapping("")  // Giữ nguyên để trả về danh sách hóa đơn với phân trang
    public Page<Invoices> getInvoices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return _invoicesService.getInvoices(page, size);
    }

    // Thêm hóa đơn mới
    @PostMapping("")
    public Invoices addInvoice(@RequestBody Invoices invoice) {
        return _invoicesService.addInvoice(invoice);
    }

    // Cập nhật hóa đơn theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Invoices> updateInvoice(@PathVariable int id, @RequestBody Invoices invoiceDetails) {
        Invoices updatedInvoice = _invoicesService.updateInvoice(id, invoiceDetails);
        if (updatedInvoice != null) {
            return ResponseEntity.ok(updatedInvoice);
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa hóa đơn theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable int id) {
        if (_invoicesService.deleteInvoice(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createInvoice(@RequestBody InvoiceRequest request, HttpServletRequest req) {
        // Tạo hóa đơn mới với thông tin từ yêu cầu
        Rooms room = _roomsService.getRoomById(request.getRoomId());

        if (room == null) {
            return ResponseEntity.badRequest().body("Phòng không tồn tại.");
        }

        Invoices invoice = _invoicesService.createInvoice(request.getTotal(), room); // Tạo hóa đơn
        String paymentLink = createPaymentLink(invoice, req); // Tạo link thanh toán

        // Tạo thông điệp gửi tới người dùng
        String message = request.getMessage() + "\nVui lòng thanh toán hóa đơn của bạn tại: " + paymentLink;
        emailService.sendEmail(request.getEmail(), "Hóa Đơn Thanh Toán", message); // Gửi email

        return ResponseEntity.ok("Hóa đơn đã được tạo và gửi đến email của bạn.");
    }

    private String createPaymentLink(Invoices invoice, HttpServletRequest req) {
        long amount = (long)(invoice.getTotal() * 100); // Chuyển đổi sang đơn vị tiền tệ (VND)
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8); // Tạo số tham chiếu giao dịch
        String vnp_IpAddr = VNPayConfig.getIpAddress(req); // Địa chỉ IP của người dùng
        String vnp_OrderInfo = Base64.getEncoder().encodeToString((invoice.getId() + "").getBytes()); // Thông tin đơn hàng

        // Thiết lập các tham số cần thiết
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0"); // Phiên bản API VNPay
        vnp_Params.put("vnp_Command", "pay"); // Lệnh thanh toán
        vnp_Params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode); // Mã đối tác
        vnp_Params.put("vnp_Amount", String.valueOf(amount)); // Số tiền
        vnp_Params.put("vnp_CurrCode", "VND"); // Đơn vị tiền tệ
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef); // Số tham chiếu giao dịch
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo); // Thông tin đơn hàng
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl); // URL trả về sau khi thanh toán
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr); // Địa chỉ IP của người dùng

        // Tạo thời gian tạo và thời gian hết hạn (nếu cần)
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(new Date());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        // Tạo chuỗi hash cho bảo mật
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();

        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                hashData.append(fieldName).append("=").append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                hashData.append("&");
            }
        }
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        String queryUrl = VNPayConfig.vnp_PayUrl + "?" + hashData.toString() + "vnp_SecureHash=" + vnp_SecureHash;

        return queryUrl; // Trả về URL thanh toán đầy đủ
    }

    @GetMapping("/payment_infor")
    public ResponseEntity<?> transaction(
            HttpServletRequest request,
            @RequestParam(value = "vnp_TxnRef") String txnRef,
            @RequestParam(value = "vnp_ResponseCode") String responseCode,
            @RequestParam(value = "vnp_Amount") String amount,
            @RequestParam(value = "vnp_OrderInfo") String orderInfo
    ) {
        // Giải mã thông tin đơn hàng
        String decodedOrderInfo = new String(Base64.getDecoder().decode(orderInfo));
        // Tách thông tin cần thiết
        String[] orderDetails = decodedOrderInfo.split("\\|");
        int invoiceId = Integer.parseInt(orderDetails[0]); // Lấy ID hóa đơn từ thông tin

        // Kiểm tra mã phản hồi
        if ("00".equals(responseCode)) {
            // Thanh toán thành công, cập nhật trạng thái hóa đơn
            Invoices invoice = _invoicesService.getInvoiceById(invoiceId);
            if (invoice != null) {
                invoice.setStatus(1); // 1: đã thanh toán
                _invoicesService.updateInvoice(invoice); // Cập nhật hóa đơn
            }
            return ResponseEntity.status(HttpStatus.OK).body("Thanh toán thành công!");
        } else {
            // Thanh toán không thành công
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thanh toán không thành công!");
        }
    }
}