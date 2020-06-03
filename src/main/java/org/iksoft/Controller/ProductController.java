package org.iksoft.Controller;

import org.iksoft.Entity.Product;
import org.iksoft.Service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author IK
 */

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String getAll(Model model, @RequestParam(required = false) String hasZero){
        List<Product> products = productService.getAllProducts();

        if (hasZero == null) products.removeIf(product -> product.getQuantity() == 0);

        model.addAttribute("hasZero", hasZero != null);
        model.addAttribute("products", products);

        return "product/list";
    }

    @GetMapping("/product/view")
    public String view(@RequestParam Integer id, Model model){
        model.addAttribute("product", productService.getProductById(id));

        return "product/view";
    }

    @GetMapping("/admin/product/add")
    public String addPage(Model model){
        model.addAttribute("product", new Product());
        return "product/add";
    }

    @PostMapping("/admin/product/add")
    public String add(@ModelAttribute Product product){
        productService.addOrUpdateProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/admin/product/edit")
    public String editPage(@RequestParam Integer id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        return "product/edit";
    }

    @PostMapping("/admin/product/edit")
    public String edit(@ModelAttribute Product product){
        productService.addOrUpdateProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/admin/product/delete")
    public String delete(@RequestParam Integer id){
        productService.deleteProduct(id);
        return "redirect:/products";
    }

}
