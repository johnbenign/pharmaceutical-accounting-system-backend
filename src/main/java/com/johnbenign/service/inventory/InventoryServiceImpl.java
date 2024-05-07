package com.johnbenign.service.inventory;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johnbenign.dto.ResponseDTO;
import com.johnbenign.dto.inventory.InventoryDTO;
import com.johnbenign.dto.user.UserDTO;
import com.johnbenign.mapper.inventory.InventoryMapper;
import com.johnbenign.repository.inventory.InventoryRepository;
import com.johnbenign.repository.user.UserRepository;

import net.bytebuddy.utility.RandomString;

@Service
public class InventoryServiceImpl implements InventoryService
{
	private static final Logger logger = Logger.getLogger(InventoryServiceImpl.class.getName());
	
	@Autowired
	private InventoryRepository repository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private InventoryMapper mapper;
	
	@Autowired
	private ResponseDTO response;

	@Override
	public ResponseDTO createInventory(InventoryDTO item, UserDTO user)
	{
		logger.info(" --- about to create inventory --- ");
		
		boolean exists = false;
		
		try
		{	
			if(item == null)
			{
				logger.info(" --- item object is null --- ");
				
				throw new Exception("item object is null");
			}
			
			if(user == null)
			{
				logger.info(" --- user object is null --- ");
				
				throw new Exception("user object is null");
			}
			
			RandomString r = new RandomString();
			
			item.setItemId(r.nextString());
			item.setCreatedAt(new java.util.Date());
//			item.setUser(user);
			
			//Double sellingPriceForSingleUnit = item.getSellingPrice()/item.getNumberOfItems();
			
			//item.setSellingPriceForSingleUnit(sellingPriceForSingleUnit);
			
			//Double costPriceForSingleUnit = item.getCostPrice()/item.getNumberOfItems();
			
			//item.setCostPriceForSingleUnit(costPriceForSingleUnit);
			
			exists = repository.existsById(item.getItemId());
			
			if(exists)
			{
				logger.severe(" --- item already exists in the system --- ");
				
				throw new Exception("item already exists!");
			}
			
			logger.info(" registering new item...");
			
			//commented this bcos userRepository will automatically save the inventory
			//InventoryDTO savedItem = repository.saveAndFlush(item);
			
			user.getItems().add(item);
			
			userRepository.saveAndFlush(user);
			
			logger.info(" --- record saved --- ");
			
			response.setResponse(item);
			response.setResult(true);
			response.setStatusCode("0");
		}
		catch(Exception e)
		{
			logger.severe("error: " + e.getMessage() + e);
			
			response.setErrorMsg("error: " + e.getMessage());
			response.setResult(false);
			response.setStatusCode("99");
			response.setResponse(e);
		}
		
		return response;
	}

	@Override
	public ResponseDTO deleteInventory(String userId, String itemId)
	{
		logger.info(" --- deleting item --- ");
				
		try
		{
			if(userId.isBlank())
			{
				logger.info(" --- no user id supplied! --- ");
				
				throw new Exception("no user id supplied!");
			}
			
			UserDTO user = userRepository.findById(userId).get();
			
			if(itemId.isBlank())
			{
				logger.info(" --- no item id supplied! --- ");
				
				throw new Exception("no item id supplied!");
			}
			
			InventoryDTO item = repository.findById(itemId).get();
			
			List<InventoryDTO> items = user.getItems().stream().filter(item2 -> !item2.getItemId().equals(item.getItemId())).collect(Collectors.toList());
			
			user.setItems(items);
			
			userRepository.saveAndFlush(user);
			
//			exists = repository.existsById(itemId);
//			
//			if(!exists)
//			{
//				logger.info(" item does not exist");
//				
//				response.setResponse("item does not exist");
//				response.setErrorMsg("item does not exist");
//				response.setResult(false);
//				response.setStatusCode("-2");
//			}
			
			repository.deleteById(itemId);
			
			logger.info(" --- item deleted !!! --- ");
			
			response.setResponse("item deleted");
			response.setResult(true);
			response.setStatusCode("0");
		}
		catch(Exception e)
		{
			logger.severe(" --- error: " + e.getMessage() + e);
			
			response.setErrorMsg("error: " + e.getMessage());
			response.setResult(false);
			response.setStatusCode("99");
			response.setResponse(e);
		}
		
		return response;
	}

	@Override
	@Transactional//hibernate require this in order for it to create a session object
	//remember spring jpa calls hibernate behind the scene
	public InventoryDTO getInventory(String itemId)
	{
		logger.info(" --- retrieving item by id --- ");
		
		InventoryDTO item = null;
		
		try
		{
			if(itemId.isBlank())
			{
				logger.info(" --- no item id supplied --- ");
				
				throw new Exception("no item id suppled");
			}
			
			item = repository.findById(itemId).get();
			
			if(item == null)
			{
				logger.info(" --- no item found with the given id --- ");
				
				throw new Exception("item not found!");
			}
			
			logger.info("item id: " + item.getItemId() + ";; item name: " + item.getItemName());
			
		}
		catch(Exception e)
		{
			logger.severe("error: " + e.getMessage() + e);
		}
		
		return item;
	}

	@Override
	public List<InventoryDTO> getAllInventories(String userId)
	{
		logger.info(" --- getting all items ---- ");
		
		List<InventoryDTO> items = null;
		
		try
		{
			UserDTO user = userRepository.findById(userId).get();
			
			items = user.getItems();
			
			//items = repository.findByUserUserId(userId);
			
			if(items.isEmpty())
				logger.info(" no record found!");
			
		}
		catch(Exception e)
		{
			logger.severe(" error: " + e.getMessage() + e);
		}
		
		return items;
	}

	@Transactional
	@Override
	public ResponseDTO updateInventory(InventoryDTO item)
	{
		logger.info("updating item!");
		
		try
		{
			if(item == null)
			{
				logger.severe(" --- item object is null --- ");
				
				throw new Exception(" item object is null");
			}
			
			if(item.getItemId().isBlank())
			{
				logger.info(" --- no item id supplied! --- ");
				
				throw new Exception("no item id supplied!");
			}
			
			InventoryDTO entity = repository.findById(item.getItemId()).get();
			
			mapper.updateInventoryFromDTO(item, entity);
			
			InventoryDTO updatedItem = repository.saveAndFlush(entity);
			
			if(updatedItem == null)
			{
				logger.severe(" unable to update item!");
				
				throw new Exception("failed to update item!");
			}
			
			logger.info(" record updated successfully!");
			
			response.setResponse(updatedItem);
			response.setResult(true);
			response.setStatusCode("0");
		}
		catch(Exception e)
		{
			logger.severe("error: " + e.getMessage() + e);
			
			response.setErrorMsg(e.getMessage());
			response.setResponse(e);
			response.setResult(false);
			response.setStatusCode("99");
		}
		
		return response;
	}

	@Override
	public ResponseDTO addStock(String itemId, Integer numberOfItemsToBeAdded)
	{
		logger.info(" --- adding stock --- ");
		
		boolean exists = false;
		
		try
		{
			if(itemId.isBlank())
			{
				logger.info(" --- no item id supplied --- ");
				
				throw new Exception("no item id supplied!");
			}
			
			exists = repository.existsById(itemId);
			
			if(!exists)
			{
				logger.info("item not found with given id!");
				
				throw new Exception(" item not found!");
			}
			
			InventoryDTO item = repository.findById(itemId).get();
			
			Integer currentNumberOfItems = item.getNumberOfItems();
			
			currentNumberOfItems += numberOfItemsToBeAdded;
			
			item.setNumberOfItems(currentNumberOfItems);
			
			InventoryDTO updatedItem = repository.saveAndFlush(item);
			
			logger.info(" stock value updated!");
			
			response.setResponse(updatedItem);
			response.setResult(true);
			response.setStatusCode("0");
		}
		catch(Exception e)
		{
			logger.severe("error: " + e.getMessage() + e);
			
			response.setErrorMsg(e.getMessage());
			response.setResponse(e);
			response.setResult(false);
			response.setStatusCode("99");
		}
		
		return response;
	}

	@Override
	public ResponseDTO removeStock(String itemId, Integer numberOfItemsToBeRemoved)
	{
		logger.info(" --- removing stock --- ");
		
		boolean exists = false;
		
		try
		{
			if(itemId.isBlank())
			{
				logger.info(" --- no item id supplied --- ");
				
				throw new Exception("no item id supplied!");
			}
			
			exists = repository.existsById(itemId);
			
			if(!exists)
			{
				logger.info("item not found with given id!");
				
				throw new Exception(" item not found!");
			}
			
			InventoryDTO item = repository.findById(itemId).get();
			
			Integer currentNumberOfItems = item.getNumberOfItems();
			
			if(currentNumberOfItems < numberOfItemsToBeRemoved)
			{
				logger.info(" items to be removed is greater than the current stock ");
				
				throw new Exception("items to be removed is greater than the current stock");
			}
			
			currentNumberOfItems -= numberOfItemsToBeRemoved;
			
			item.setNumberOfItems(currentNumberOfItems);
			
			InventoryDTO updatedItem = repository.saveAndFlush(item);
			
			logger.info(" stock value updated!");
			
			response.setResponse(updatedItem);
			response.setResult(true);
			response.setStatusCode("0");
		}
		catch(Exception e)
		{
			logger.severe("error: " + e.getMessage() + e);
			
			response.setErrorMsg(e.getMessage());
			response.setResponse(e);
			response.setResult(false);
			response.setStatusCode("99");
		}
		
		return response;
	}
	
	

}
