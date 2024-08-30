package com.oussama.bankapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.oussama.bankapp.dto.UserRegisterDTO;
 import com.oussama.bankapp.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

    @Autowired
     UserService userService;

     @GetMapping("/login")
    public String getLoginPage() {
        return "login";  
    }
   
    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username, 
                            @RequestParam("password") String password, 
                            RedirectAttributes redirectAttributes) {
        if (userService.validateCredentials(username, password)) {
            return "redirect:/home";  
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password.");
            return "redirect:/login";  
        }
    }
    @GetMapping("register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegisterDTO()); // Initialisez l'objet UserRegisterDTO
        return "register";  
    }

   @PostMapping("register")
public String registerUser(@Valid @ModelAttribute("user") UserRegisterDTO userRegisterDTO, 
                           BindingResult result, 
                           RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
        return "register";  
    }
    
    try {
        userService.register(userRegisterDTO);
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Registration failed: " + e.getMessage());
        return "redirect:/register";
    }
    
    return "redirect:/login";
}


}