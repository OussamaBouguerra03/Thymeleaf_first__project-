package com.oussama.bankapp.services;

import java.util.List;
import java.util.Optional;

import com.oussama.bankapp.dto.TransactionDTO;
import com.oussama.bankapp.models.Transaction;
 
public interface TransactionService {
    void deposit(TransactionDTO transactionDTO); 
    Optional<TransactionDTO> getTransactionById(Long id);
    List<TransactionDTO> getAllTransactions();
    void deleteTransaction(Long id);
    void withdraw(TransactionDTO transactionDTO);
    List<TransactionDTO> getTransactionsByCustomerId(Long customerId);
    List<Transaction> findTransactionsByUser(String username);
}