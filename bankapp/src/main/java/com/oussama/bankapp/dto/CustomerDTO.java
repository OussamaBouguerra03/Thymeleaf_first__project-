package com.oussama.bankapp.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private Double balance;   

     public CustomerDTO(Long id, String name, Double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

     public CustomerDTO() {
    }
}
