package com.johnbenign.controller.inventory;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.johnbenign.dto.ResponseDTO;
import com.johnbenign.dto.inventory.InventoryDTO;
import com.johnbenign.dto.user.UserDTO;
import com.johnbenign.service.inventory.InventoryService;

@RestController
@CrossOrigin(origins="http://localhost:3000/inventory")
@RequestMapping("/pharm/inventory")
public class InventoryController
{	
	@Autowired
	private InventoryService service;
	
	private static final Logger logger = Logger.getLogger(InventoryController.class.getName());
	
	@RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> createInventory(@RequestBody InventoryDTO item, HttpServletRequest req)
	{
		logger.info(" --- creating inventory! --- ");
		
		HttpSession session = req.getSession();
		
		UserDTO user = (UserDTO) session.getAttribute("userDTO");
		
		ResponseDTO response = service.createInventory(item, user);
		
		if(response.getResult())
		{
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}
		
		return ResponseEntity.badRequest().body(response);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<InventoryDTO>> getAllInventories(HttpServletRequest req)
	{
		logger.info(" --- getting all inventories! --- ");
		
		HttpSession session = req.getSession();
		
		UserDTO user = (UserDTO) session.getAttribute("userDTO");
		
		String userId = user.getUserId();
		
		List<InventoryDTO> items = service.getAllInventories(userId);
		
		return ResponseEntity.ok().body(items);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{itemId}")
	public ResponseEntity<InventoryDTO> getInventory(@PathVariable String itemId)
	{
		logger.info(" --- getting inventory by id! --- ");
		
		InventoryDTO item = service.getInventory(itemId);
		
		if(item == null)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body(item);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/{itemId}")
	public ResponseEntity<ResponseDTO> updateInventory(@RequestBody InventoryDTO item, @PathVariable String itemId)
	{
		logger.info(" --- about to update an entity --- ");
		
		item.setItemId(itemId);
		
		ResponseDTO response = service.updateInventory(item);
		
		if(response.getResult())
		{
			return ResponseEntity.ok(response);
		}
		
		return ResponseEntity.badRequest().body(response);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{itemId}")
	public ResponseEntity<ResponseDTO> deleteInventory(@PathVariable String itemId, HttpServletRequest req)
	{
		logger.info(" --- about to delete an entity --- ");
		
		HttpSession session = req.getSession();
		
		UserDTO user = (UserDTO) session.getAttribute("userDTO");
				
		ResponseDTO response = service.deleteInventory(user.getUserId(), itemId);
		
		if(response.getResult())
		{
			return ResponseEntity.ok(response);
		}
		
		return ResponseEntity.badRequest().body(response);
	}
}
