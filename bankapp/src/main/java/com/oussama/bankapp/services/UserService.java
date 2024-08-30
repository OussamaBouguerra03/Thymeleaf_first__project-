package com.oussama.bankapp.services;

import com.oussama.bankapp.dto.UserRegisterDTO;
import com.oussama.bankapp.models.Users;

public interface UserService {
    void register(UserRegisterDTO userDTO);
    Users findByLogin(String login);
    boolean validateCredentials(String username, String password);
}
