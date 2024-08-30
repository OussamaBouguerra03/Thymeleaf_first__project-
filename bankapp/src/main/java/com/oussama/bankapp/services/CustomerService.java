package com.oussama.bankapp.services;

import java.util.List;
import java.util.Optional;

import com.oussama.bankapp.dto.CustomerDTO;
 
public interface CustomerService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    Optional<CustomerDTO> getCustomerById(Long id);
    List<CustomerDTO> getAllCustomers();
    void deleteCustomer(Long id);
    Long getCustomerIdByUsername(String username);

}