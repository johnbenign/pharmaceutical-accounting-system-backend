package com.johnbenign.test.inventory;

import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.johnbenign.dto.ResponseDTO;
import com.johnbenign.dto.inventory.InventoryDTO;
import com.johnbenign.dto.user.UserDTO;
import com.johnbenign.repository.user.UserRepository;
import com.johnbenign.service.inventory.InventoryService;

@SpringBootTest
public class InventoryTest
{
	private static final Logger logger = Logger.getLogger(InventoryTest.class.getName());
	
	@Autowired
	private InventoryService service;
	
	@Autowired
	private UserRepository rep;
	
	//@Test
	public void createInventory()
	{
		logger.info(" about to create inventory!");
		
		InventoryDTO item = new InventoryDTO();
		
		item.setCostPrice(120.00);
		item.setItemName("Pro Cold");
		item.setDescription("For extreme cold");
		item.setNumberOfItems(100);
		item.setSellingPrice(150.00);
		
		UserDTO user = rep.findById("benign60").get();
		
		ResponseDTO response = service.createInventory(item, user);
		
		assertNotNull(response, "response is not null");
		
		assertTrue(response.getResult(), "result should be true");
	}
	
	//@Test
	public void updateInventory()
	{
		logger.info(" --- about to update inventory !");
		
		InventoryDTO item = new InventoryDTO();
		
		///item.setDescription("Paracetamol for soft headache");
		item.setModifiedAt(new java.util.Date());
		item.setNumberOfItems(19);
		//item.setItemName("Paracetamol");
		//item.setSellingPrice(200000.00);
		item.setItemId("ZRzVPYVv");
		
		ResponseDTO response = service.updateInventory(item);
		
		assertNotNull(response, "response should not be null");
		assertTrue(response.getResult(), "result should be true");
		
		logger.info(" updated successfully");
	}
	
	//@Test
	public void getAllInventories()
	{
		logger.info(" --- getting all inventories --- ");
		
		List<InventoryDTO> items = service.getAllInventories("benign60");
		
		assertNotNull(items, "not null");
		
		items.forEach(item -> {
			logger.info("item id: " + item.getItemId() + ";; item name: " + item.getItemName());
		});
	}
	
	//@Test
	public void getInventory()
	{
		logger.info(" getting item by id ");
		
		String itemId = "3Me7Hocc";
		
		InventoryDTO item = service.getInventory(itemId);
		
		assertNotNull(item, "not null");
		
		logger.info("item id: " + item.getItemId() + ";; item name: " + item.getItemName());
	}
	
	@Test
	public void deleteInventory()
	{
		logger.info(" --- deleting item by id --- ");
		
		String itemId = "FYNIEKUd";
				
		ResponseDTO response = service.deleteInventory("benign60", itemId);
		
		assertNotNull(response, "not null");
		
		assertTrue(response.getResult(), "result must be true");
		
		logger.info(" --- deleted successfully! --- ");
	}
}
