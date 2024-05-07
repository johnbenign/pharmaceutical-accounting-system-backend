package com.johnbenign.dto.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

import com.johnbenign.dto.inventory.InventoryDTO;

@Component
@Entity
public class UserDTO
{
	@Id
	@Column
	private String userId;
	
	@Column
	private String password;
	
	@Column
	private String firstName;
	
	@Column
	private String lastName;
	
	@Column
	private Integer age;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	private List<InventoryDTO> items;
	
	@Column
	private String address;
	
	@Column
	private String phoneNumber;
	
	@Column
	private String email;
	
	@Column(columnDefinition = "decimal default 0")
	private Double totalProfit;
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public Integer getAge()
	{
		return age;
	}
	
	public void setAge(Integer age)
	{
		this.age = age;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(Double totalProfit) {
		this.totalProfit = totalProfit;
	}

	public List<InventoryDTO> getItems() {
		return items;
	}

	public void setItems(List<InventoryDTO> items) {
		this.items = items;
	}
}