package org.iksoft.Controller;

import org.iksoft.Service.CustomerService;
import org.iksoft.Service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

/**
 * @author IK
 */

@Controller
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final CustomerService customerService;

    public InvoiceController(InvoiceService invoiceService, CustomerService customerService) {
        this.invoiceService = invoiceService;
        this.customerService = customerService;
    }

    @GetMapping("/invoice/detailed")
    public String invoiceView(Model model,
                              @RequestParam Integer id){

        model.addAttribute("invoiceDetail", invoiceService.getDetails(id));
        model.addAttribute("orderId", invoiceService.getInvoiceById(id).getOrder().getId());

        return "invoice/view";
    }

    @GetMapping("/customer/invoices")
    public String customerInvoices(Model model, Principal principal){
        Integer customerId = customerService.getCustomerByUsername(
                principal.getName()
        ).getId();

        model.addAttribute("invoices",
                invoiceService.getInvoicesByCustomerId(customerId)
        );

        return "invoice/list";
    }

    @GetMapping("/admin/invoices")
    public String allInvoice(Model model){
        model.addAttribute("invoices", invoiceService.getAllInvoices());

        return "invoice/list";
    }

    @GetMapping("/customer/invoice/pay")
    public String payPage(@RequestParam Integer invoiceId,
                      Model model){

        model.addAttribute("invoiceId", invoiceId);
        model.addAttribute("remaining", invoiceService.remainingAmount(invoiceId));

        return "payment/pay";
    }

    @PostMapping("/customer/invoice/pay")
    public String pay(@RequestParam Integer invoiceId,
                      @RequestParam Double amount){

        invoiceService.pay(amount, invoiceId);

        return "redirect:/customer/invoices";
    }
}
