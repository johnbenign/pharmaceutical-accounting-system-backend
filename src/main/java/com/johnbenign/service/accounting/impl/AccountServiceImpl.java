package com.johnbenign.service.accounting.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johnbenign.dto.ResponseDTO;
import com.johnbenign.dto.accounting.AccountDTO;
import com.johnbenign.dto.accounting.TransactionType;
import com.johnbenign.dto.inventory.InventoryDTO;
import com.johnbenign.dto.user.UserDTO;
import com.johnbenign.mapper.accounting.AccountMapper;
import com.johnbenign.repository.accounting.AccountRepository;
import com.johnbenign.repository.user.UserRepository;
import com.johnbenign.service.accounting.AccountService;
import com.johnbenign.service.inventory.InventoryService;

import net.bytebuddy.utility.RandomString;

@Service
public class AccountServiceImpl implements AccountService
{
	private static final Logger logger = Logger.getLogger(AccountServiceImpl.class.getName());
	
	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private AccountMapper mapper;
	
	@Autowired
	private UserRepository userRepository;
		
	@Autowired 
	private InventoryService inventoryService;
	
	@Autowired
	private ResponseDTO response;

	@Override
	public ResponseDTO addTransaction(AccountDTO account, UserDTO user)
	{
		logger.info(" --- adding a new transaction --- ");
		
		try
		{
			String txId = RandomString.make();
			
			account.setTransactionId(txId);
			
			if(user == null)
			{
				logger.severe(" --- no user details supplied --- ");
				
				throw new Exception("no user details supplied");
			}
			
			InventoryDTO itemEntity = null;
			
			if(account.getTransactionType().equals(TransactionType.MONEY_IN))
			{
				String itemId = account.getItemId();
				
				itemEntity = inventoryService.getInventory(itemId);
				
				Integer quantity = account.getQuantity();
				
				itemEntity.setNumberOfItems(itemEntity.getNumberOfItems() - quantity);
				
				//inventoryService.updateInventory(itemEntity);
				
				logger.info(" --- number of items entity: " + itemEntity.getNumberOfItems());
				
				account.setItem(itemEntity);
				
				//repository.save(account);
				
				Double costAmount = itemEntity.getCostPrice() * quantity;
				
				Double sellingAmount = itemEntity.getSellingPrice() * quantity;
				
				Double profit = sellingAmount - costAmount;
				
				account.setCurrentProfit(profit);
				
				user.setTotalProfit(user.getTotalProfit() != null? (user.getTotalProfit() + profit) : profit);
			}
			else
			{
				user.setTotalProfit(user.getTotalProfit() - account.getAmount());
			}
			
			//userRepository.save(user);
			
			account.setCreatedAt(new Date());
			
			account.setUser(user);
			
			AccountDTO savedTx = repository.saveAndFlush(account);
			
			if(savedTx == null)
			{
				logger.info(" --- transaction was unable to save --- ");
				
				throw new Exception("transaction was unable to save");
			}
		
			
			response.setResponse(account);
			response.setResult(true);
			response.setStatusCode("00");
			
			logger.info(" --- new tx recorded --- ");
			
		}
		catch(Exception e)
		{
			logger.severe("error: " + e.getMessage() + e);
			e.printStackTrace();
			response.setErrorMsg("error: " + e.getMessage());
			response.setResponse(e);
			response.setStatusCode("99");
		}
		
		return response;
	}

	@Override
	public ResponseDTO removeTransaction(String transactionId)
	{
		logger.info(" --- about to remove tx --- ");
		
		try
		{
			AccountDTO accountEntity = repository.findById(transactionId).get();
			
			UserDTO user = accountEntity.getUser();
			
			if(user == null)
			{
				logger.info(" --- user details not supplied");
				
				throw new Exception(" user details not supplied !");
			}
			
			if(accountEntity.getTransactionType().equals(TransactionType.MONEY_IN))
			{
				logger.info(" money in ....tx");
				
				String itemId = accountEntity.getItemId();
				
				logger.info(" --- get invenotry entity --- ");
				
				InventoryDTO itemEntity = inventoryService.getInventory(itemId);
				
				logger.info(" --- get the quantity from entity --- ");
				
				Integer quantity = accountEntity.getQuantity();
				
				logger.info(" --- add the quantity to the number of items");
				
				itemEntity.setNumberOfItems(itemEntity.getNumberOfItems() + quantity);
				
				logger.info(" update inventory");
				
				inventoryService.updateInventory(itemEntity);
				
				logger.info("get current profit");
				
				Double currentProfit = accountEntity.getCurrentProfit();
				
				logger.info("subtract current profit from total profit");
				
				user.setTotalProfit(user.getTotalProfit() - currentProfit);
				
				logger.info(" save ");
				
				userRepository.save(user);
				
				logger.info("Saved!!!");
			}
			else
			{
				logger.info(" money out .... tx");
				
//				Double currentProfit = accountEntity.getCurrentProfit();
				
				user.setTotalProfit(user.getTotalProfit() + accountEntity.getAmount());
				
				userRepository.saveAndFlush(user);
				
//				Double costAmount = accountEntity.getAmount();
//				
//				user.setTotalProfit(user.getTotalProfit() + costAmount);
//				
//				userRepository.save(user);
			}
			
			repository.deleteById(transactionId);
			
			logger.info("record removed successfully!");
			response.setResponse("removed record successfully!");
			response.setResult(true);
			response.setStatusCode("00");
		}
		catch(Exception e)
		{
			response.setErrorMsg(e.getMessage());
			response.setResponse(e);
			response.setResult(false);
			response.setStatusCode("99");
		}
		
		return response;
	}

	@Override
	public Double getTodayProfit(String userId)
	{
		DateFormat format = new SimpleDateFormat("dd MM yyyy");
		
		Calendar cal = Calendar.getInstance();
		
		Date from = null;
		try {
			from = format.parse(format.format(cal.getTime()));
			
			logger.info("from date : " + from);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cal.add(Calendar.DAY_OF_YEAR, 1);
		
		Date to = null;
		try {
			to = format.parse(format.format(cal.getTime()));
			
			logger.info("to date : " + to);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Double currentProfit = 0.0;
		Double expenditure = 0.0;
				
		List<AccountDTO> txs = repository.findByUserUserIdAndCreatedAtBetween(userId,from, to);
		
		for(AccountDTO tx: txs)
		{
			
			currentProfit += tx.getCurrentProfit()==null?0.0:tx.getCurrentProfit();
			
			expenditure += tx.getAmount()== null?0.0:tx.getAmount();
		}
		
		Double todayProfit = currentProfit - expenditure;
		
//		if(txs.isEmpty())
//		{
//			logger.info(" --- no record found !" );
//		}
//		
//		txs.forEach(tx -> {
//			logger.info(" ---- tx id: " + tx.getItemId());
//		});
		
		return todayProfit;
	}
	
	@Override
	public Double getTotalProfit(String userId)
	{
		if(userId.isBlank())
		{
			logger.info(" --- no user id supplied --- ");
			
			return 0.0;
		}
		
		UserDTO user = userRepository.findById(userId).get();
		
		Double totalProfit = user.getTotalProfit();
		
		return totalProfit;
	}
	
	public List<AccountDTO> getAllTransactions(String userId)
	{
		logger.info(" getting all transactions");
		
		List<AccountDTO> txs = null;
		
		try
		{
			txs = repository.findByUserUserId(userId);
			
			if(txs.isEmpty())
				logger.info(" --- no record found! --- ");
		}
		catch(Exception e)
		{
			logger.info(" error: " + e.getMessage() + e);
		}
		
		return txs;
	}

	@Override
	public ResponseDTO updateTransaction(AccountDTO tx)
	{
//		logger.info(" --- updating tx --- ");
//		
//		try
//		{
//			if(tx == null)
//			{
//				logger.severe(" --- tx object is null --- ");
//				
//				throw new Exception(" tx object is null");
//			}
//			
//			if(tx.getTransactionId().isBlank())
//			{
//				logger.info(" --- no tx id supplied! --- ");
//				
//				throw new Exception("no tx id supplied!");
//			}
//			
//			AccountDTO entity = repository.findById(tx.getTransactionId()).get();
//			
//			mapper.updateTransactionFromDTO(tx, entity);
//			
//			InventoryDTO updatedItem = repository.saveAndFlush(entity);
//			
//			if(updatedItem == null)
//			{
//				logger.severe(" unable to update item!");
//				
//				throw new Exception("failed to update item!");
//			}
//			
//			logger.info(" record updated successfully!");
//			
//			response.setResponse(updatedItem);
//			response.setResult(true);
//			response.setStatusCode("0");
//		}
//		catch(Exception e)
//		{
//			logger.severe("error: " + e.getMessage() + e);
//			
//			response.setErrorMsg(e.getMessage());
//			response.setResponse(e);
//			response.setResult(false);
//			response.setStatusCode("99");
//		}
		return null;
	}
	
}
