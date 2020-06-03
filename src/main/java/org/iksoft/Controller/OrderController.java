package org.iksoft.Controller;

import org.iksoft.DTO.CartItem;
import org.iksoft.DTO.PlaceOrderDTO;
import org.iksoft.Entity.Customer;
import org.iksoft.Entity.Invoice;
import org.iksoft.Service.CustomerService;
import org.iksoft.Service.InvoiceService;
import org.iksoft.Service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author IK
 */

@Controller
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final InvoiceService invoiceService;

    public OrderController(OrderService orderService, CustomerService customerService,
                           InvoiceService invoiceService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.invoiceService = invoiceService;
    }

    @PostMapping("/customer/place-order")
    public String placeOrder(HttpSession session, Principal principal){
        PlaceOrderDTO dto = new PlaceOrderDTO();
        HashMap<Integer, CartItem> cart = (HashMap<Integer, CartItem>)
                                            session.getAttribute("cart");

        for(Map.Entry<Integer, CartItem> entry : cart.entrySet()){
            dto.addItem(new PlaceOrderDTO.Item(
                    entry.getKey(),
                    entry.getValue().getQuantity(),
                    entry.getValue().getCurPrice()
            ));
        }

        session.setAttribute("cartSize", 0);
        ((HashMap<Integer, CartItem>) session.getAttribute("cart")).clear();

        Customer cur = customerService.getCustomerByUsername(principal.getName());

        Invoice invoice = orderService.placeOrder(cur.getId(), dto);
        invoiceService.addInvoice(invoice.getAmount(), invoice.getOrder().getId());

        return "redirect:/products";
    }

    @GetMapping("/customer/orders")
    public String orders(Model model, HttpSession session){
        model.addAttribute("orders", orderService.getAllByUserId(
                (Integer) session.getAttribute("userId")
        ));
        return "order/list";
    }

    @GetMapping("/admin/orders")
    public String ordersAdmin(Model model){
        model.addAttribute("orders", orderService.getAllOrders());

        return "order/list";
    }

    @GetMapping("/order/detailed")
    public String orderView(@RequestParam Integer id,
                            Model model){
        model.addAttribute("orderDetail", orderService.getDetails(id));
        model.addAttribute("invoiceId", invoiceService.getInvoiceByOrderId(id).getId());

        return "order/view";
    }
}
