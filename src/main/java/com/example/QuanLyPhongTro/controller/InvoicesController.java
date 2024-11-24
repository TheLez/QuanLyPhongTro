package com.example.QuanLyPhongTro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.QuanLyPhongTro.models.Invoices;
import com.example.QuanLyPhongTro.services.InvoicesService;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoicesController {

    @Autowired
    private InvoicesService _invoicesService;

    @GetMapping("")
    public List<Invoices> getAllInvoices() {
        return _invoicesService.getAllInvoices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoices> getInvoiceById(@PathVariable int id) {
        Invoices invoice = _invoicesService.getInvoiceById(id);
        if (invoice != null) {
            return ResponseEntity.ok(invoice);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public Page<Invoices> getInvoices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return _invoicesService.getInvoices(page, size);
    }

    @PostMapping("")
    public Invoices addInvoice(@RequestBody Invoices invoice) {
        return _invoicesService.addInvoice(invoice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoices> updateInvoice(@PathVariable int id, @RequestBody Invoices invoiceDetails) {
        Invoices updatedInvoice = _invoicesService.updateInvoice(id, invoiceDetails);
        if (updatedInvoice != null) {
            return ResponseEntity.ok(updatedInvoice);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable int id) {
        if (_invoicesService.deleteInvoice(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}