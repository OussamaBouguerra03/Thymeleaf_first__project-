package com.oussama.bankapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.oussama.bankapp.dto.CustomerDTO;
import com.oussama.bankapp.models.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(source = "balance", target = "balance")
    CustomerDTO toDTO(Customer customer);

    @Mapping(target = "id", ignore = true) 
    @Mapping(target = "transactions", ignore = true) 
    Customer toEntity(CustomerDTO customerDTO);
}