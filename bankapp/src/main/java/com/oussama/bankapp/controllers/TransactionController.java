package com.oussama.bankapp.controllers;

import com.oussama.bankapp.dto.TransactionDTO;
import com.oussama.bankapp.services.TransactionService;
import com.oussama.bankapp.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
      TransactionService transactionService;

    @Autowired
      CustomerService customerService;

    @GetMapping("my/list")
    public String listTransactions(Authentication authentication, Model model) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Long customerId = customerService.getCustomerIdByUsername(username);

        List<TransactionDTO> transactions;
        if (authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
            transactions = transactionService.getAllTransactions(); 
        } else {
            transactions = transactionService.getTransactionsByCustomerId(customerId); 
        }
        model.addAttribute("transactions", transactions);
        return "transactions-list"; 
    }

    @GetMapping("my/deposit")
    public String depositForm(Model model) {
        model.addAttribute("transaction", new TransactionDTO());
        return "deposit-form"; 
    }

    @GetMapping("my/withdraw")
    public String withdrawForm(Model model) {
        model.addAttribute("transaction", new TransactionDTO());
        return "withdraw-form"; 
    }

    @PostMapping("my/save")
    public String saveTransaction(@ModelAttribute TransactionDTO transactionDTO, Authentication authentication, Model model) {
        try {
            transactionDTO.setTransactionDate(LocalDateTime.now());

            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Long customerId = customerService.getCustomerIdByUsername(username);

            if (transactionDTO.getCustomerId() == null) {
                transactionDTO.setCustomerId(customerId);
            } else if (!transactionDTO.getCustomerId().equals(customerId)) {
                throw new IllegalArgumentException("You cannot perform transactions for other customers.");
            }

            if ("DEPOSIT".equals(transactionDTO.getType())) {
                transactionService.deposit(transactionDTO);
            } else if ("WITHDRAW".equals(transactionDTO.getType())) {
                transactionService.withdraw(transactionDTO);
            }
            model.addAttribute("message", "Transaction successful.");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/home"; 
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable("id") Long id, Authentication authentication, Model model) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Long customerId = customerService.getCustomerIdByUsername(username);

        Optional<TransactionDTO> transactionDTO = transactionService.getTransactionById(id);
        if (transactionDTO.isPresent() && !transactionDTO.get().getCustomerId().equals(customerId)) {
            model.addAttribute("error", "You cannot delete transactions that do not belong to you.");
            return "result"; 
        }

        transactionService.deleteTransaction(id);
        model.addAttribute("message", "Transaction deleted.");
        return "result"; 
    }
}
