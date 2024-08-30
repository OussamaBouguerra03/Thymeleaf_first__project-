package com.oussama.bankapp.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Data;
@Data
public class TransactionDTO  {
    private Long id;
    private String type;
    private double amount;
    private LocalDateTime transactionDate;
    private Long customerId;
    private String customerUsername; 

    public TransactionDTO() {
    }
 public String getFormattedTransactionDate() {
        return transactionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
    public Long getCustomerId() {
        return customerId;
    }
    public String getCustomerUsername() {
        return customerUsername;
    }


    public TransactionDTO(Long id, String type, double amount, LocalDateTime transactionDate,Long customerId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.customerId=customerId;
    }
}
