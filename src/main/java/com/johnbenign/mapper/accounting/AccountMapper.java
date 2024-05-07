package com.johnbenign.mapper.accounting;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.johnbenign.dto.accounting.AccountDTO;

@Mapper(componentModel = "spring")
public interface AccountMapper
{
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateTransactionFromDTO(AccountDTO txDTO, @MappingTarget AccountDTO entity);
}
