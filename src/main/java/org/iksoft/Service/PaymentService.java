package org.iksoft.Service;

import org.iksoft.Entity.Invoice;
import org.iksoft.Entity.Payment;
import org.iksoft.Repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author IK
 */

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void pay(Double amount, Invoice invoice){
        if (amount == 0) throw
                new IllegalArgumentException("Amount of payment can't be zero");

        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setTime(Timestamp.valueOf(LocalDateTime.now()));
        payment.setInvoice(invoice);
        paymentRepository.save(payment);
    }

    public List<Payment> getAllPaymentsByInvoiceId(Integer invoiceId){
        return paymentRepository.findAllByInvoiceId(invoiceId);
    }

    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }
}
