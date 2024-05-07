package com.johnbenign.service.user;

import com.johnbenign.dto.ResponseDTO;
import com.johnbenign.dto.user.UserDTO;

public interface UserService
{
	/**
	 * @author Benign
	 * @apiNote This method creates a user using spring data jpa
	 * @param user
	 */
	public ResponseDTO createUser(UserDTO user);
	
	public ResponseDTO login(String email, String password);
}
