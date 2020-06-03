package org.iksoft.Service;

import org.iksoft.DTO.InvoiceDetailedDTO;
import org.iksoft.Entity.Invoice;
import org.iksoft.Entity.Order;
import org.iksoft.Entity.Payment;
import org.iksoft.Exception.NotFoundException;
import org.iksoft.Repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * @author IK
 */

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final PaymentService paymentService;

    public InvoiceService(InvoiceRepository invoiceRepository,
                          PaymentService paymentService) {
        this.invoiceRepository = invoiceRepository;
        this.paymentService = paymentService;
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public List<Invoice> getInvoicesByCustomerId(Integer customerId){
        return invoiceRepository.findAllByOrder_Customer_Id(customerId);
    }

    public Invoice getInvoiceById(Integer invoiceId) {
        return invoiceRepository.findById(invoiceId).orElseThrow(
                new NotFoundException("Not found invoice with id " + invoiceId)
        );
    }

    public Invoice getInvoiceByOrderId(Integer orderId){
        return invoiceRepository.findByOrder_Id(orderId).orElseThrow(
                new NotFoundException("Not found invoice with orderId "+orderId)
        );
    }

    public InvoiceDetailedDTO getDetails(Integer invoiceId) {

        Invoice invoice = getInvoiceById(invoiceId);
        InvoiceDetailedDTO dto = new InvoiceDetailedDTO();
        dto.setInvoiceId(invoiceId);
        dto.setAmount(invoice.getAmount());
        dto.setIssued(invoice.getIssued());
        dto.setDue(invoice.getDue());
        dto.setStatus(invoice.getStatus());
        dto.setPayments(paymentService.getAllPaymentsByInvoiceId(invoiceId));

        return dto;
    }

    public void addInvoice(Double amount, Integer orderId) {
        Invoice invoice = new Invoice();
        invoice.setAmount(amount);
        invoice.setOrder(new Order());
        invoice.getOrder().setId(orderId);
        invoice.setIssued(Date.valueOf(LocalDate.now()));
        invoice.setDue(Date.valueOf(LocalDate.now().plusDays(3)));
        invoice.setStatus((byte)0);
        invoiceRepository.save(invoice);
    }

    public void pay(Double amount, Integer invoiceId){
        paymentService.pay(amount, getInvoiceById(invoiceId));
        updateInvoiceStatus(invoiceId);
    }

    public void updateInvoiceStatus(Integer invoiceId) {
        Invoice invoice = getInvoiceById(invoiceId);
        double remaining = remainingAmount(invoiceId);

        if (remaining == 0) invoice.setStatus((byte) 2);
        else if (remaining == invoice.getAmount()) invoice.setStatus((byte) 0);
        else invoice.setStatus((byte) 1);

        invoiceRepository.save(invoice);
    }

    public  Double remainingAmount(Integer invoiceId){
        double remaining = getInvoiceById(invoiceId).getAmount();

        List<Payment> payments = paymentService.getAllPaymentsByInvoiceId(invoiceId);
        for (Payment payment : payments) {
            remaining -= payment.getAmount();
        }

        remaining = Math.round(remaining * 100) / 100.0;

        return remaining;
    }
}
