package org.iksoft.Controller;

import org.iksoft.DTO.CartItem;
import org.iksoft.DTO.CreateCustomerAndUserDTO;
import org.iksoft.Entity.User;
import org.iksoft.Service.CustomerService;
import org.iksoft.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;

/**
 * @author  IK
 */

@Controller
public class AuthController {

    private final CustomerService customerService;
    private final UserService userService;

    public AuthController(CustomerService customerService, UserService userService) {
        this.customerService = customerService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model, HttpSession session,
                        @RequestParam(required = false) String error,
                        @RequestParam(required = false) String logout,
                        @RequestParam(required = false) String linkSent){

        model.addAttribute("hasError", error != null);

        if (logout != null) {
            model.addAttribute("isLoggedOut", true);
            session.removeAttribute("cart");
            session.removeAttribute("cartSize");
            session.removeAttribute("userId");
        } else model.addAttribute("isLoggedOut", false);

        model.addAttribute("linkSent", linkSent !=null);

        return "auth/login";
    }

    @GetMapping("/login-success")
    public String loginSuccess(HttpSession session, Principal principal){
        User user = userService.getUserByUsername(principal.getName());
        if (user.getRole().equals("ROLE_USER")){
            session.setAttribute("cart", new HashMap<Integer, CartItem>());
            session.setAttribute("cartSize",0);
        }

        session.setAttribute("userId", user.getId());

        return "redirect:/";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("hasErr", false);
        model.addAttribute("errMsg", "");
        model.addAttribute("dto", new CreateCustomerAndUserDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registered(@ModelAttribute CreateCustomerAndUserDTO dto,
                             Model model){

        if (userService.isUserRegistered(dto.getUsername())){
            model.addAttribute("hasErr", true);
            model.addAttribute("errMsg", "This username is already registered !");
            model.addAttribute("dto", dto);
            return "auth/register";
        }
        if (!dto.getPassword().equals(dto.getConfirm())){
            model.addAttribute("hasErr", true);
            model.addAttribute("errMsg", "Password and confirmation must be equal !");
            model.addAttribute("dto", dto);
            return "auth/register";
        }
        User user = userService.addUser(dto.getUsername(), dto.getEmail(),
                dto.getPassword(), "ROLE_USER");

        customerService.addCustomer(dto.getName(), dto.getCountry(),
                dto.getAddress(), user.getId());

        return "redirect:/login";
    }

    @GetMapping("/restore-password")
    public String restorePassPage(@RequestParam(required = false) String notRegistered, Model model){
        model.addAttribute("notRegistered", notRegistered != null);
        return "auth/restore-password";
    }

    @PostMapping("/restore-password")
    public String restorePass(@RequestParam String username){
        if (userService.isUserRegistered(username)){
            //there should be email sending operations here
            return "redirect:/login?linkSent";
        }

        return "redirect:/restore-password?notRegistered";
    }
}
