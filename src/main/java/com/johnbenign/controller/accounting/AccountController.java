package com.johnbenign.controller.accounting;

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
import com.johnbenign.dto.accounting.AccountDTO;
import com.johnbenign.dto.user.UserDTO;
import com.johnbenign.service.accounting.AccountService;

@RestController
@CrossOrigin(origins = { "http://localhost:3000" }, allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/pharm/accounting")
public class AccountController
{
	private static final Logger logger = Logger.getLogger(AccountController.class.getName());
	
	@Autowired
	private AccountService service;
	
//	@Autowired
//	private HttpSession session;
	
	@RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> addTransaction(@RequestBody AccountDTO tx, HttpServletRequest req)
	{
		logger.info(" --- adding tx in controller! --- ");
		
		HttpSession session = req.getSession();
		
		UserDTO user = (UserDTO) session.getAttribute("userDTO");
		
		ResponseDTO response = service.addTransaction(tx, user);
		
		if(response.getResult())
		{
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}
		
		return ResponseEntity.badRequest().body(response);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{txId}")
	public ResponseEntity<ResponseDTO> removeTransaction(@PathVariable String txId)
	{
		logger.info(" --- removing tx from controller! --- ");
				
		ResponseDTO response = service.removeTransaction(txId);
		
		if(response.getResult())
		{
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		
		return ResponseEntity.badRequest().body(response);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/todayProfit")
	public ResponseEntity<Double> getTodayProfit(HttpServletRequest req)
	{
		logger.info(" --- getting today profit controller! --- ");
		
		HttpSession session = req.getSession();
		
		UserDTO user = (UserDTO) session.getAttribute("userDTO");
		
		String userId = user.getUserId();
				
		Double profit = service.getTodayProfit(userId);
		
		logger.info(" today profit : " + profit);
		
		return ResponseEntity.ok(profit);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/totalProfit")
	public ResponseEntity<Double> getTotalProfit(HttpServletRequest req)
	{
		logger.info(" --- getting total profit for logged in user controller! --- ");
		
		HttpSession session = req.getSession();
		
		UserDTO user = (UserDTO) session.getAttribute("userDTO");
		
		String userId = user.getUserId();
				
		Double totalProfit = service.getTotalProfit(userId);
		
		logger.info(" total profit : " + totalProfit);
		
		return ResponseEntity.ok(totalProfit);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<AccountDTO>> getAllTransactions(HttpServletRequest req)
	{
		logger.info(" --- getting all tx controller! --- ");
		
//		ServletRequestAttributes attr = (ServletRequestAttributes) 
//			    RequestContextHolder.currentRequestAttributes();
//			UserDTO user= (UserDTO) attr.getRequest().getAttribute("userDTO");
		
		HttpSession session = req.getSession();
		
		logger.info(" session id in account controller: " + session.getId());
		
		
		
		UserDTO user = (UserDTO) session.getAttribute("userDTO");

		String userId = user.getUserId();
				
		List<AccountDTO> txs = service.getAllTransactions(userId);
		
		return ResponseEntity.ok(txs);
	}
}
