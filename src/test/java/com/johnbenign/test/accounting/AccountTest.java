package com.johnbenign.test.accounting;

import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.johnbenign.dto.ResponseDTO;
import com.johnbenign.dto.accounting.AccountDTO;
import com.johnbenign.dto.accounting.TransactionType;
import com.johnbenign.dto.user.UserDTO;
import com.johnbenign.repository.user.UserRepository;
import com.johnbenign.service.accounting.AccountService;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountTest
{
	private static final Logger logger = Logger.getLogger(AccountTest.class.getName());
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UserRepository userRepository;
	
	//@Test
	public void addTransaction() 
	{
		logger.info(" --- adding tx --- ");
		
		AccountDTO account = new AccountDTO();
		
		//money_out tx
//		account.setItemName("Rice and Beans");
//		account.setAmount(1000.00);
//		account.setTransactionType(TransactionType.MONEY_OUT);
		
		//money_in tx
		account.setItemId("JCtXD2RR");
		account.setQuantity(50);
		account.setTransactionType(TransactionType.MONEY_IN);
		
		UserDTO userEntity = userRepository.findById("benign60").get();
		
		ResponseDTO response = accountService.addTransaction(account, userEntity);
		
		assertNotNull(response, "response not null");
		
		assertTrue(response.getResult(), "result is true");
	}
	
	@Test
	public void removeTransaction() 
	{
		logger.info(" --- removing tx --- ");
		
		String transactionId = "8V7TdpCG";
		
//		account.setItemId("LdsnBJhp");
//		account.setQuantity(1);
//		account.setTransactionType(TransactionType.MONEY_IN);
				
		ResponseDTO response = accountService.removeTransaction(transactionId);
		
		assertNotNull(response, "response not null");
		
		assertTrue(response.getResult(), "result is true");
	}
	
	//@Test
	public void getTodayProfit()
	{
		logger.info(" --- getting today profit --- ");
		
		Double profit = accountService.getTodayProfit("johnbenign6");
		
		assertNotEquals(0.0, profit);
		
		logger.info("the profit is : " + profit);
	}
	
	//@Test
	public void getAllTransactions()
	{
		logger.info(" --- getting all transactions --- ");
		
		String userId = "johnbenign6";
		
		List<AccountDTO> txs = accountService.getAllTransactions(userId);
		
		assertNotNull(txs, "tx will not be null");
		
		txs.forEach(tx -> {logger.info(tx.getItem().getItemName());});
	}
}
