package com.johnbenign.mapper.inventory;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.johnbenign.dto.inventory.InventoryDTO;

@Mapper(componentModel = "spring")
public interface InventoryMapper
{
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateInventoryFromDTO(InventoryDTO itemDTO, @MappingTarget InventoryDTO entity);
}
