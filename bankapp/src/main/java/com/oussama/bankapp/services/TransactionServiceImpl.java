package com.oussama.bankapp.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oussama.bankapp.dto.TransactionDTO;
import com.oussama.bankapp.exceptions.CustomerNotFoundException;
import com.oussama.bankapp.exceptions.InsufficientBalanceException;
import com.oussama.bankapp.models.Customer;
import com.oussama.bankapp.models.Transaction;
import com.oussama.bankapp.mappers.TransactionMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    @Transactional
    public void deposit(TransactionDTO transactionDTO) {
        Long customerId = transactionDTO.getCustomerId();
        double amount = transactionDTO.getAmount();
        String transactionType = transactionDTO.getType();
        LocalDateTime transactionDate = transactionDTO.getTransactionDate();

        Customer customer = entityManager.find(Customer.class, customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found.");
        }

        customer.setBalance(customer.getBalance() + amount);
        entityManager.merge(customer);

        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        transaction.setCustomer(customer);
        entityManager.persist(transaction);
    }

    @Override
     public List<Transaction> findTransactionsByUser(String username) {
        String customerQuery = "SELECT c FROM Customer c WHERE c.username = :username";
        Customer customer = entityManager.createQuery(customerQuery, Customer.class)
                                         .setParameter("username", username)
                                         .getSingleResult();
    
        if (customer == null) {
            return List.of(); 
        }
    
        List<Transaction> transactions = customer.getTransactions();
        System.out.println("Transactions for user: " + transactions); 
    
        return transactions; 
    }
    
    
    @Override
    public List<TransactionDTO> getTransactionsByCustomerId(Long customerId) {
        String query = "SELECT t FROM Transaction t WHERE t.customer.id = :customerId";
        List<Transaction> transactions = entityManager.createQuery(query, Transaction.class)
                                                       .setParameter("customerId", customerId)
                                                       .getResultList();
        return transactions.stream()
                           .map(transactionMapper::toDTO)
                           .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void withdraw(TransactionDTO transactionDTO) {
        Long customerId = transactionDTO.getCustomerId();
        double amount = transactionDTO.getAmount();
        String transactionType = transactionDTO.getType();
        LocalDateTime transactionDate = transactionDTO.getTransactionDate();

        Customer customer = entityManager.find(Customer.class, customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found.");
        }
        if (customer.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance.");
        }

        customer.setBalance(customer.getBalance() - amount);
        entityManager.merge(customer);

        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        transaction.setCustomer(customer);
        entityManager.persist(transaction);
    }

    @Override
    public Optional<TransactionDTO> getTransactionById(Long id) {
        Transaction transaction = entityManager.find(Transaction.class, id);
        return Optional.ofNullable(transactionMapper.toDTO(transaction));
    }

    @Override
    public List<TransactionDTO> getAllTransactions() {
        String query = "SELECT t FROM Transaction t JOIN FETCH t.customer";
        List<Transaction> transactions = entityManager.createQuery(query, Transaction.class)
                .getResultList();
        return transactions.stream()
                .map(transaction -> {
                    TransactionDTO dto = transactionMapper.toDTO(transaction);
                    dto.setCustomerUsername(transaction.getCustomer().getUsername()); 
                    return dto;
                })
                .collect(Collectors.toList());
    }
    

    @Override
    @Transactional
    public void deleteTransaction(Long id) {
        Transaction transaction = entityManager.find(Transaction.class, id);
        if (transaction != null) {
            entityManager.remove(transaction);
        }
    }
}
