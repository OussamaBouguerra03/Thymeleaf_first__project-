package com.oussama.bankapp.repositories;

 import org.springframework.data.jpa.repository.JpaRepository;

import com.oussama.bankapp.models.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    boolean existsByUsername(String username);

}