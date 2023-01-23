package com.artemgggi.webshop.controller.admin;

import com.artemgggi.webshop.dto.ProductRepository;
import com.artemgggi.webshop.model.Category;
import com.artemgggi.webshop.model.Product;
import com.artemgggi.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @GetMapping("/admin/index")
    public String showAddProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        model.addAttribute("categories", productService.getAllCategories());
        return "/admin/index";
    }

    @GetMapping("/admin/product")
    public String showAddProduct(Model model)
    {
        model.addAttribute("category", new Category());
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("products", productService.getAllProduct());
        return "/admin/product";
    }

    @PostMapping("/admin/addProduct")
    public String saveProduct(@RequestParam("file") MultipartFile file,
                              @RequestParam("pname") String name,
                              @RequestParam("price") int price,
                              @RequestParam("desc") String desc,
                              @RequestParam("quantity") int quantity,
                              @RequestParam("categories") String categories)
    {
        productService.saveProductToDB(file, name, price,desc, quantity, categories);
        return "redirect:/admin/index";
    }

    @PostMapping("/admin/addCategory")
    public String addCategory(@RequestParam("category") String name) {
        productService.saveCategory(name);
        return "redirect:/admin/index";
    }

    @GetMapping("/admin/deleteProd/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return "redirect:/admin/index";
    }

    @PostMapping("/admin/changeName")
    public String changePname(@RequestParam("id") Long id,
                              @RequestParam("newPname") String name) {
        productService.changeProductName(id, name);
        return "redirect:/admin/index";
    }

    @PostMapping("/admin/changePdescription")
    public String changeDescription(@RequestParam("id") Long id ,
                                    @RequestParam("newPdescription")
                                    String description) {
        productService.changeProductDescription(id, description);
        return "redirect:/admin/index";
    }

    @PostMapping("/admin/changePprice")
    public String changePrice(@RequestParam("id") Long id ,
                              @RequestParam("newPprice") int price) {
        productService.changeProductPrice(id, price);
        return "redirect:/admin/index";
    }

    @PostMapping("/admin/changeProductQuantity")
    public String changeQuantity(@RequestParam("id") Long id ,
                                 @RequestParam("newQuantity") int quantity) {
        productService.changeProductQuantity(id, quantity);
        return "redirect:/admin/index";
    }

    @PostMapping("/admin/addPictureToP")
    public String addImageToProduct(@RequestParam("file") MultipartFile file,
                                    @RequestParam("product_id") Long id ) {
        productService.addImageToProduct(file,id);
        return "redirect:/admin/index";
    }

    @PostMapping("/admin/addDiscountToP")
    public String addDiscountToProduct(@RequestParam("product_id") Long id,
                                       @RequestParam("discount") int discount) {
        productService.addDiscountToProduct(id, discount);
        return "redirect:/admin/index";
    }
}
