package com.oussama.bankapp.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transaction")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")  
    private Long id;

    @Column(name = "transaction_type", nullable = false, length = 50)  
    private String type;  

    @Column(name = "amount", nullable = false) 
    private double amount;

    @Column(name = "transaction_date", nullable = false)  
    private LocalDateTime transactionDate;

    @ManyToOne
@JoinColumn(name = "customer_id", nullable = false)  
private Customer customer; // Assurez-vous que ceci correspond à 'mappedBy'
}
