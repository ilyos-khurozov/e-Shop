package org.iksoft.Controller;

import org.iksoft.DTO.CreateCustomerAndUserDTO;
import org.iksoft.Entity.Customer;
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

/**
 * @author IK
 */

@Controller
public class UserController {

    private final UserService userService;
    private final CustomerService customerService;

    public UserController(UserService userService, CustomerService customerService) {
        this.userService = userService;
        this.customerService = customerService;
    }

    @GetMapping("/admin/users")
    public String getAll(Model model){
        model.addAttribute("users", userService.getAllUsers());

        return "user/list";
    }

    @GetMapping("/admin/user/info")
    public String userInfo(Model model, @RequestParam Integer id){
        User user = userService.getUserById(id);

        model.addAttribute("user", user);

        if (user.getRole().equals("ROLE_USER")){
            model.addAttribute("customer",
                    customerService.getCustomerByUsername(user.getUsername())
            );
        }

        return "user/info";
    }

    @GetMapping("/admin/user/delete")
    public String delete(@RequestParam Integer id){
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/user/settings")
    public String settingsPage(Model model, HttpSession session){
        User user = userService.getUserById((Integer) session.getAttribute("userId"));
        model.addAttribute("email", user.getEmail());

        if (user.getRole().equals("ROLE_USER")){
            Customer customer = customerService.getCustomerByUsername(user.getUsername());
            session.setAttribute("cId", customer.getId());
            model.addAttribute("customer",customer);
        }

        return "user/settings";
    }

    @PostMapping("/user/settings")
    public String saveUserSettings(@RequestParam String email,
                                   @ModelAttribute Customer customer,
                                   HttpSession session){

        userService.changeEmail(
                (Integer) session.getAttribute("userId"),
                email
        );

        if (session.getAttribute("cId") != null){
            customer.setId((Integer)session.getAttribute("cId"));
            customer.setUser(new User());
            customer.getUser().setId((Integer) session.getAttribute("userId"));

            customerService.updateCustomer(customer);
            session.removeAttribute("cId");
        }

        return "redirect:/";
    }

    @GetMapping("/user/change-password")
    public String changePasswordPage(){
        return "/user/password";
    }

    @PostMapping("/user/change-password")
    public String changePassword(@RequestParam String password,
                                 @RequestParam String confirm,
                                 HttpSession session){

        if (!password.equals(confirm)){
            session.setAttribute("notEqual", "ik");
            return "user/password";
        }

        userService.changePassword(
                (Integer) session.getAttribute("userId"),
                password
        );

        return "redirect:/user/settings";
    }

    @GetMapping("/admin/user/add")
    public String addPage(Model model){
        model.addAttribute("hasErr", false);
        model.addAttribute("errMsg", "");
        model.addAttribute("dto", new CreateCustomerAndUserDTO());

        return "user/add";
    }

    @PostMapping("/admin/user/add")
    public String add(@ModelAttribute CreateCustomerAndUserDTO dto,
                      Model model){

        if (userService.isUserRegistered(dto.getUsername())){
            model.addAttribute("hasErr", true);
            model.addAttribute("errMsg", "This username is already registered !");
            model.addAttribute("dto", dto);
            return "user/add";
        }
        if (!dto.getPassword().equals(dto.getConfirm())){
            model.addAttribute("hasErr", true);
            model.addAttribute("errMsg", "Password and confirmation must be equal !");
            model.addAttribute("dto", dto);
            return "user/add";
        }

        userService.addUser(dto.getUsername(), dto.getEmail(),
                dto.getPassword(), "ROLE_ADMIN");

        return "redirect:/admin/users";
    }
}
