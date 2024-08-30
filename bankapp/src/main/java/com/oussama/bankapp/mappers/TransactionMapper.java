package com.oussama.bankapp.mappers;

import com.oussama.bankapp.dto.TransactionDTO;
import com.oussama.bankapp.models.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "customerId", source = "customer.id")
    TransactionDTO toDTO(Transaction transaction);

    @Mapping(target = "customer", ignore = true) 
    Transaction toEntity(TransactionDTO transactionDTO);
}

