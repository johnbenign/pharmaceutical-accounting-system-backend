package com.johnbenign.controller.user;

import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.johnbenign.dto.Credential;
import com.johnbenign.dto.ResponseDTO;
import com.johnbenign.dto.user.UserDTO;
import com.johnbenign.service.user.UserService;

@RestController
@CrossOrigin(origins = { "http://localhost:3000" }, allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/pharm")
public class UserController
{
	private static final Logger logger = Logger.getLogger(UserController.class.getName());
	
//	@Autowired
//	private HttpSession session;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	//@RequestBody MultiValueMap<String, String> credentials
	public ResponseEntity<ResponseDTO> login(@RequestBody Credential credential, HttpServletRequest req)
	{
		logger.info(" --- login controller called --- ");
		
		String email = credential.getEmail();
		
		String password = credential.getPassword();
		
		logger.info(" --- email is : " + email + " ;; password is : " + password);
		
		ResponseDTO response = userService.login(email, password);
		
		if(response.getResult())
		{
//			ServletRequestAttributes attr = (ServletRequestAttributes) 
//				    RequestContextHolder.currentRequestAttributes();
//				
//			attr.getRequest().setAttribute("userDTO", (UserDTO) response.getResponse());
			
			HttpSession session = req.getSession();
			
			logger.info(" session id in user controller: " + session.getId());
			
			
			session.setAttribute("userDTO", (UserDTO)response.getResponse());
			
			logger.info(" user info stored in session!");
			
			return ResponseEntity.ok().body(response);
		}
		
		return ResponseEntity.badRequest().body(response);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/createUser", consumes = MediaType.APPLICATION_JSON_VALUE)
	//@RequestBody MultiValueMap<String, String> credentials
	public ResponseEntity<ResponseDTO> createUser(@RequestBody UserDTO user)
	{
		logger.info(" --- create user controller called --- ");
		
		logger.info(" --- user first name is : " + user.getFirstName() + " ;; user last name is : " + user.getLastName());
		
		ResponseDTO response = userService.createUser(user);
		
		if(response.getResult())
		{	
			logger.info(" new user successfully created!");
			
			return ResponseEntity.ok().body(response);
		}
		
		return ResponseEntity.badRequest().body(response);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/logout")
	public void logout(HttpServletRequest req)
	{
		logger.info(" --- logging out and invalidating the session --- ");
		
		HttpSession session = req.getSession();
		
		session.invalidate();
		
		logger.info("invalidated!!!");
		
	}
	
}
