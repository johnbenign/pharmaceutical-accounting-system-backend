package com.johnbenign.repository.inventory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.johnbenign.dto.inventory.InventoryDTO;

public interface InventoryRepository extends JpaRepository<InventoryDTO, String>
{

}
