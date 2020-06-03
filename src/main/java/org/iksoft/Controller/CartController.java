package org.iksoft.Controller;

import org.iksoft.DTO.CartItem;
import org.iksoft.Entity.Product;
import org.iksoft.Service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author IK
 */

@Controller
public class CartController {

    private final ProductService productService;

    public CartController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/customer/cart")
    public String showCart(HttpSession session, Model model){
        model.addAttribute("cart", (HashMap<Integer, CartItem>) session.getAttribute("cart"));
        return "product/cart";
    }

    @PostMapping("/customer/cart/add")
    public String addToCart(@RequestParam Integer id, HttpSession session){
        HashMap<Integer, CartItem> cart = (HashMap<Integer, CartItem>) session.getAttribute("cart");

        if (cart.get(id) == null) {
            Product product = productService.getProductById(id);
            cart.put(id, new CartItem(
                    product.getName(),
                    1,
                    product.getPrice()
            ));
            session.setAttribute("cartSize",
                    ((int)session.getAttribute("cartSize"))+1);
        } else {
            cart.get(id).plusOne();
        }

        return "redirect:/products";
    }

    @PostMapping("/customer/cart/delete")
    public String deleteFromCart(@RequestParam Integer pId,
                                 HttpSession session){

        session.setAttribute("cartSize", ((int)session.getAttribute("cartSize"))-1);
        ((HashMap<Integer, CartItem>) session.getAttribute("cart")).remove(pId);

        return "redirect:/customer/cart";
    }

    @PostMapping("/customer/cart/clear")
    public String clearCart(HttpSession session){
        session.setAttribute("cartSize", 0);
        ((HashMap<Integer, CartItem>) session.getAttribute("cart")).clear();

        return "redirect:/products";
    }
}
