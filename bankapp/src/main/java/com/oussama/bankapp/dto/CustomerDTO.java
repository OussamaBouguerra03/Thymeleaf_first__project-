package com.oussama.bankapp.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private Double balance;  // Ajout du champ balance

    // Constructeur avec tous les paramètres
    public CustomerDTO(Long id, String name, Double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    // Constructeur par défaut
    public CustomerDTO() {
    }
}
