package com.oussama.bankapp.controllers;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oussama.bankapp.dto.TransactionDTO;
import com.oussama.bankapp.models.Transaction;
import com.oussama.bankapp.services.TransactionService;

@Controller
public class HomeController {

    @Autowired
    private TransactionService transactionService;

   @GetMapping("/home")
public String home(@RequestParam(name = "type", required = false) String type,Authentication authentication, Model model) {
    if (authentication.getAuthorities().stream()
    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
List<TransactionDTO> transactions = transactionService.getAllTransactions();
model.addAttribute("transactions", transactions);
return "home_admin";  
}else if (authentication.getAuthorities().stream()
            .anyMatch(role -> role.getAuthority().equals("ROLE_CUSTOMER"))) {
        
        String username = authentication.getName();

        List<Transaction> transactions = transactionService.findTransactionsByUser(username);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<Map<String, Object>> formattedTransactions = transactions.stream()
                .map(t -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("transactionDate", t.getTransactionDate().format(formatter));
                    map.put("type", t.getType());
                    map.put("amount", t.getAmount());
                    return map;
                })
                .collect(Collectors.toList());
             
                if (type != null && !type.isEmpty()) {
                    transactions = transactions.stream()
                                               .filter(t -> t.getType().equals(type))
                                               .toList();
                }
    
                model.addAttribute("transactions", transactions);
                model.addAttribute("username", username); 
                model.addAttribute("type", type); 
     
        return "home_customer";  
    }

    return "redirect:/login";
}

}
