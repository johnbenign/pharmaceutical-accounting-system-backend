package com.johnbenign.repository.accounting;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.johnbenign.dto.accounting.AccountDTO;

@Repository
public interface AccountRepository extends JpaRepository<AccountDTO, String>
{
	public List<AccountDTO> findByUserUserIdAndCreatedAtBetween(String userId, Date from, Date to);
	
	public List<AccountDTO> findByUserUserId(String userId);
}
