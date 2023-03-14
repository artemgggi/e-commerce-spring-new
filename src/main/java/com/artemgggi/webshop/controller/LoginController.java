package com.artemgggi.webshop.controller;

import com.artemgggi.webshop.dto.CustomerDto;
import com.artemgggi.webshop.model.Customer;
import com.artemgggi.webshop.service.CustomerService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController {

    private final CustomerService customerService;

    private final BCryptPasswordEncoder passwordEncoder;

    public LoginController(CustomerService customerService, BCryptPasswordEncoder passwordEncoder) {
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("customerDto", new CustomerDto());
        return "/register";
    }

    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute("customerDto") CustomerDto customerDto,
                                  BindingResult result,
                                  Model model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("customerDto", customerDto);
                return "/register";
            }
            Customer customer = customerService.findByUsername(customerDto.getUsername());
            if(customer != null){
                model.addAttribute("username", "Username have been registered");
                model.addAttribute("customerDto",customerDto);
                return "/register";
            }
            if (customerDto.getPassword().equals(customerDto.getRepeatPassword())){
                customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));
                customerService.save(customerDto);
                model.addAttribute("success", "Register successfully");
            } else {
                model.addAttribute("password", "Password is not same");
                model.addAttribute("customerDto",customerDto);
            }
            return "/register";
        } catch (Exception e) {
            model.addAttribute("error", "Server have ran some problems");
            model.addAttribute("customerDto",customerDto);
        }
        return "/register";
    }
}
