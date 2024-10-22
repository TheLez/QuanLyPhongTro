package com.example.QuanLyPhongTro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.QuanLyPhongTro.models.Invoices;
import com.example.QuanLyPhongTro.repositories.InvoicesRepository;

import java.util.List;

@Service
public class InvoicesService {
    @Autowired
    private InvoicesRepository _invoicesRepository;

    public List<Invoices> getAllInvoices() {
        return _invoicesRepository.findAll();
    }

    public Invoices getInvoiceById(int id) {
        return _invoicesRepository.findById(id).orElse(null);
    }

    public Invoices addInvoice(Invoices invoice) {
        return _invoicesRepository.save(invoice);
    }

    public Invoices updateInvoice(int id, Invoices invoiceDetails) {
        Invoices invoice = getInvoiceById(id);
        if (invoice != null) {
            invoice.setTotal(invoiceDetails.getTotal());
            invoice.setCreatedAt(invoiceDetails.getCreatedAt());
            invoice.setRoom(invoiceDetails.getRoom());
            return _invoicesRepository.save(invoice);
        }
        return null;
    }

    public boolean deleteInvoice(int id) {
        if (_invoicesRepository.existsById(id)) {
            _invoicesRepository.deleteById(id);
            return true;
        }
        return false;
    }
}