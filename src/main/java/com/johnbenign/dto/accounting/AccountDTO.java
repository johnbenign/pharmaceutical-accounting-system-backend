package com.johnbenign.dto.accounting;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;

import com.johnbenign.dto.inventory.InventoryDTO;
import com.johnbenign.dto.user.UserDTO;

@Component
@Entity
public class AccountDTO
{
	@Id
	@Column
	private String transactionId;
	
	@JoinColumn
	@OneToOne
	private InventoryDTO item;
	
	
	@ManyToOne
	private UserDTO user;
	
	@Column
	private String itemId;
	
	@Column
	private String itemName;
	
	@Column
	private Integer quantity;
	
	@Column
	private Double amount;
	
	@Column
	private Double currentProfit;
	
	@Column
	private String transactionType;
	
	@Column
	private Date createdAt;
	
	@Column
	private Date modifiedAt;
	
	public String getItemId()
	{
		return itemId;
	}
	public void setItemId(String itemId)
	{
		this.itemId = itemId;
	}
	public String getItemName()
	{
		return itemName;
	}
	public void setItemName(String itemName)
	{
		this.itemName = itemName;
	}
	public Integer getQuantity()
	{
		return quantity;
	}
	public void setQuantity(Integer quantity)
	{
		this.quantity = quantity;
	}
	public Double getAmount()
	{
		return amount;
	}
	public void setAmount(Double amount)
	{
		this.amount = amount;
	}
	public Double getCurrentProfit()
	{
		return currentProfit;
	}
	public void setCurrentProfit(Double profit)
	{
		this.currentProfit = profit;
	}
	
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public InventoryDTO getItem() {
		return item;
	}
	public void setItem(InventoryDTO item) {
		this.item = item;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getModifiedAt() {
		return modifiedAt;
	}
	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
	
}
