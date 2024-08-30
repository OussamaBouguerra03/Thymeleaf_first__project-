package com.oussama.bankapp.controllers;

import com.oussama.bankapp.dto.CustomerDTO;
import com.oussama.bankapp.services.CustomerService;

import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public String listCustomers(Model model) {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customers-list"; 
    }

    @GetMapping("/create")
    public String createCustomerForm(Model model) {
        model.addAttribute("customer", new CustomerDTO());
        return "create-customer"; 
    }

    @PostMapping("/create")
    public String createCustomer(@ModelAttribute CustomerDTO customerDTO, Model model) {
        customerService.saveCustomer(customerDTO);
        return "redirect:/customers/list"; 
    }

    @GetMapping("/{id}")
    public String getCustomerById(@PathVariable Long id, Model model) {
        Optional<CustomerDTO> customerDTO = customerService.getCustomerById(id);
        if (customerDTO.isPresent()) {
            model.addAttribute("customer", customerDTO.get());
            return "view-customer"; 
        } else {
            return "404"; 
        }
    }

    @GetMapping("/edit/{id}")
    public String editCustomerForm(@PathVariable Long id, Model model) {
        Optional<CustomerDTO> customerDTO = customerService.getCustomerById(id);
        if (customerDTO.isPresent()) {
            model.addAttribute("customer", customerDTO.get());
            return "edit-customer"; 
        } else {
            return "404"; 
        }
    }

    @PutMapping("/update/{id}")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute CustomerDTO customerDTO, Model model) {
        customerDTO.setId(id);
        customerService.saveCustomer(customerDTO);
        return "redirect:/customers/list"; 
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id, Model model) {
        customerService.deleteCustomer(id);
        return "redirect:/customers/list"; 
    }
}
