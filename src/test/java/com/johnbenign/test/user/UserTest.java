package com.johnbenign.test.user;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.johnbenign.dto.ResponseDTO;
import com.johnbenign.dto.user.UserDTO;
import com.johnbenign.service.user.UserService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserTest
{
	private static final Logger logger = Logger.getLogger(UserTest.class.getName());
	
	@Autowired
	private UserService userService;
	
	//@Test
	public void createUser()
	{
		UserDTO user = new UserDTO();
		
		user.setAddress("Block L5 Flat 21, NAF Base Ikeja");
		user.setAge(20);
		user.setEmail("johnbenign@gmail.com");
		user.setFirstName("Benign");
		user.setLastName("Ihugba");
		user.setPassword("Ogechukwu");
		user.setPhoneNumber("07036544572");
		user.setUserId("benign60");
		
		logger.info(" --- about to create user --- ");		
		
		ResponseDTO response = userService.createUser(user);
		
		assertNotNull(response, "response should not be null");
		assertTrue(response.getResult(), "result should be true");
		
		logger.info(" --- user created successfully --- ");
	}
	
	//@Test
	public void login()
	{
		String email = "johnbenign6@gmail.com";
		
		String password = "Ogechukwu";
		
		ResponseDTO response = userService.login(email, password);
		
		assertNotNull(response, "response should not be null");
		assertTrue(response.getResult(), "result should be true");
		
		logger.info(" --- login successful --- ");
	}
}
