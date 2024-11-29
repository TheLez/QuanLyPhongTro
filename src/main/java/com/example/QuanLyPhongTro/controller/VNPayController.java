package com.example.QuanLyPhongTro.controller;

import com.example.QuanLyPhongTro.DTO.PaymentRestDTO;
import com.example.QuanLyPhongTro.DTO.TransactionStatusDTO;
import com.example.QuanLyPhongTro.config.VNPayConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/payment")
public class VNPayController {
    @GetMapping("/create_payment")
    public ResponseEntity<?> createPayment(HttpServletRequest req) throws UnsupportedEncodingException {
        //nhập cái giá gói dịch vụ vào request
        long amount = Integer.parseInt(req.getParameter("amount"))*100;
        //nhập ngân hàng muốn chuyển vào requyest
        String bankCode = req.getParameter("bankcode");
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = VNPayConfig.getIpAddress(req);

        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VNPayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
//        vnp_Params.put("vnp_BankCode", "NCB");
        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", VNPayConfig.orderType);
        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;

        PaymentRestDTO paymentRestDTO = new PaymentRestDTO();
        paymentRestDTO.setStatus("Ok");
        paymentRestDTO.setMessage("Successfully");
        paymentRestDTO.setURL(paymentUrl);
//        com.google.gson.JsonObject job = new JsonObject();
//        job.addProperty("code", "00");
//        job.addProperty("message", "success");
//        job.addProperty("data", paymentUrl);
//        Gson gson = new Gson();
//        resp.getWriter().write(gson.toJson(job));
        return ResponseEntity.status(HttpStatus.OK).body(paymentRestDTO);
    }

    @GetMapping("/payment_infor")
    public ResponseEntity<?> transaction(
            HttpServletRequest request,
//            @RequestParam(value = "vnp_Amount") String amount,
//            @RequestParam(value = "vnp_BankCode") String bankcode,
//            @RequestParam(value = "vnp_OrderInfo") String order,
            @RequestParam(value = "vnp_ResponseCode") String responseCode
    ){
        Map<String, String> params = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            params.put(paramName, paramValue);
        }
//        // Tạo đối tượng JSON trả về
//        Map<String, Object> response = new HashMap<>();
//        response.put("status", "Ok");
//        response.put("message", "Successfully");
//        response.put("data", params);
        TransactionStatusDTO transactionStatusDTO = new TransactionStatusDTO();
        if (responseCode.equals("00")){
            transactionStatusDTO.setStatus("Ok");
            transactionStatusDTO.setMessage("Successfully");
            transactionStatusDTO.setData(params);
        }else{
            transactionStatusDTO.setStatus("No");
            transactionStatusDTO.setMessage("Failed");
            transactionStatusDTO.setData("");
        }
        return ResponseEntity.status(HttpStatus.OK).body(transactionStatusDTO);
    }
}
