package com.johnbenign.service.user.impl;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.johnbenign.dto.ResponseDTO;
import com.johnbenign.dto.user.UserDTO;
import com.johnbenign.repository.user.UserRepository;
import com.johnbenign.service.user.UserService;

@Service
public class UserServiceImpl implements UserService
{
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ResponseDTO response;

	
	@Override
	public ResponseDTO createUser(UserDTO user)
	{
		logger.info(" --- about to create user --- ");
		
		boolean exists = false;
		
		try
		{
			exists = userRepository.existsById(user.getUserId());
			
			if(exists)
			{
				logger.severe(" --- user id already exists in the system --- ");
				
				throw new Exception("user id already exists!");
			}
			
			exists = userRepository.existsUserDTOByEmailAndPassword(user.getEmail(), user.getPassword());
			
			if(exists)
			{
				logger.severe(" --- user already exists ---");
				
				throw new Exception(" user already exists ");
			}
			else
			{
				logger.info("it is a new user");
			}
			
			UserDTO savedUser = userRepository.saveAndFlush(user);
			
			if(savedUser == null)
			{
				throw new Exception("record not saved!");
			}
			
			logger.info(" --- record saved --- ");
			
			response.setResponse(savedUser);
			response.setResult(true);
			response.setStatusCode("0");
		}
		catch(Exception e)
		{
			logger.severe("error: " + e.getMessage());
			
			response.setErrorMsg("error: " + e.getMessage());
			response.setResult(false);
			response.setStatusCode("99");
		}
		
		return response;
	}
	
	public ResponseDTO login(String email, String password)
	{
		logger.info(" --- login started --- ");
		
		UserDTO user =  null;
		
		try
		{
			List<UserDTO> users = userRepository.findByEmailAndPassword(email, password);
			
			if(users.size() < 1)
			{
				logger.info(" --- no record found with the given credential --- ");
								
				response.setErrorMsg("no record found with the given id ");
				response.setResult(false);
				response.setStatusCode("98");
				response.setResponse("no record found with the given id");
				
				return response;
			}
			
			user = users.get(0);
			
			response.setResult(true);
			response.setResponse(user);
			response.setStatusCode("0");
			
			logger.info(" --- login is successful --- ");
			
			return response;
		}
		catch(Exception e)
		{
			logger.severe("error: " + e.getMessage());
			
			response.setErrorMsg("error: " + e.getMessage());
			response.setResult(false);
			response.setStatusCode("99");
			
			return response;
		}
		
		
	}
}
