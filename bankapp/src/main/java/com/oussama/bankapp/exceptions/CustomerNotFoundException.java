package com.oussama.bankapp.exceptions;


public class CustomerNotFoundException extends TransactionException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}