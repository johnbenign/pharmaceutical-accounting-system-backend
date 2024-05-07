package com.johnbenign.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.johnbenign.dto.user.UserDTO;

public interface UserRepository extends JpaRepository<UserDTO, String>
{
	public List<UserDTO> findByEmailAndPassword(String email, String password);
	
	public boolean existsUserDTOByEmailAndPassword(String email, String password);
}
