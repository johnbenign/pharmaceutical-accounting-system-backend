package com.johnbenign.service.accounting;

import java.util.List;

import com.johnbenign.dto.ResponseDTO;
import com.johnbenign.dto.accounting.AccountDTO;
import com.johnbenign.dto.user.UserDTO;

public interface AccountService
{
	//record transaction
	ResponseDTO addTransaction(AccountDTO account, UserDTO user);
	
	ResponseDTO removeTransaction(String transactionId);
	
	//get all items that was transacted by that user
	//Double getTotalProfit();
	
	Double getTodayProfit(String userId);
	
	public List<AccountDTO> getAllTransactions(String userId);
	
	public ResponseDTO updateTransaction(AccountDTO transaction);
	
	public Double getTotalProfit(String userId);
}
