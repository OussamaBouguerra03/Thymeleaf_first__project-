package com.oussama.bankapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.oussama.bankapp.dto.UserLoginDTO;
import com.oussama.bankapp.dto.UserRegisterDTO;
import com.oussama.bankapp.models.Users;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    @Mapping(target = "role", constant = "CUSTOMER")
    Users toEntity(UserRegisterDTO dto);

    UserRegisterDTO toDTO(Users entity);
    
    @Mapping(target = "role", ignore = true) 
    Users toEntity(UserLoginDTO dto);
}
