package com.johnbenign.service.inventory;

import java.util.List;

import com.johnbenign.dto.ResponseDTO;
import com.johnbenign.dto.inventory.InventoryDTO;
import com.johnbenign.dto.user.UserDTO;

public interface InventoryService
{
	public ResponseDTO createInventory(InventoryDTO item, UserDTO user);
	
	public ResponseDTO deleteInventory(String userId, String itemId);
	
	public InventoryDTO getInventory(String itemId);
	
	public List<InventoryDTO> getAllInventories(String userId);
	
	public ResponseDTO updateInventory(InventoryDTO item);
	
	public ResponseDTO addStock(String itemId, Integer numberOfItems);
	
	public ResponseDTO removeStock(String itemId, Integer numberOfItems);
	
}
