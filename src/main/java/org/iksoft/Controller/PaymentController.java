package org.iksoft.Controller;

import org.iksoft.Service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author IK
 */

@Controller
public class PaymentController {
    
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/admin/payments")
    public String payments(Model model){

        model.addAttribute("payments", paymentService.getAllPayments());

        return "payment/list";
    }
}
