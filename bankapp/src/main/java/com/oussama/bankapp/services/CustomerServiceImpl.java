package com.oussama.bankapp.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oussama.bankapp.dto.CustomerDTO;
import com.oussama.bankapp.models.Customer;
import com.oussama.bankapp.mappers.CustomerMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        System.out.println("Saving customer: " + customer);
        if (customer.getId() == null) {
            entityManager.persist(customer);
        } else {
            customer = entityManager.merge(customer);
        }
        System.out.println("Saved customer: " + customer);
        return customerMapper.toDTO(customer);
    }

    @Override
    @Transactional
    public Long getCustomerIdByUsername(String username) {
        String query = "SELECT c.id FROM Customer c WHERE c.username = :username";
        Long customerId = entityManager.createQuery(query, Long.class)
                                       .setParameter("username", username)
                                       .getSingleResult();
        return customerId;
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(Long id) {
        Customer customer = entityManager.find(Customer.class, id);
        return Optional.ofNullable(customerMapper.toDTO(customer));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = entityManager.createQuery("SELECT c FROM Customer c", Customer.class)
                .getResultList();
        return customers.stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = entityManager.find(Customer.class, id);
        if (customer != null) {
            entityManager.remove(customer);
        }
    }
}
