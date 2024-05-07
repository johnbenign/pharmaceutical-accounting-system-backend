package com.johnbenign.dto.inventory;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.stereotype.Component;

import com.johnbenign.dto.user.UserDTO;

@Component
@Entity
public class InventoryDTO
{
	@Id
	@Column
	private String itemId;
	
	@Column
	private String itemName;
	
//	@ManyToOne(cascade = CascadeType.ALL )
//	private UserDTO user;
	
	@Column
	private String description;
	
	@Column
	private Double costPrice;
	
	@Column
	private Double costPriceForSingleUnit;
	
	@Column
	private Double sellingPriceForSingleUnit;
	
	@Column
	private Double sellingPrice;
	
	@Column
	private Integer numberOfItems;
	
	@Column
	private Date createdAt;
	
	@Column
	private Date modifiedAt = new Date();
	
	public String getItemName()
	{
		return itemName;
	}
	public void setItemName(String itemName)
	{
		this.itemName = itemName;
	}
	public String getDescription() 
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public Double getCostPrice()
	{
		return costPrice;
	}
	public void setCostPrice(Double costPrice)
	{
		this.costPrice = costPrice;
	}
	public Double getSellingPrice()
	{
		return sellingPrice;
	}
	public void setSellingPrice(Double sellingPrice)
	{
		this.sellingPrice = sellingPrice;
	}
	public Integer getNumberOfItems()
	{
		return numberOfItems;
	}
	public void setNumberOfItems(Integer numberOfItems)
	{
		this.numberOfItems = numberOfItems;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
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
	public Double getCostPriceForSingleUnit() {
		return costPriceForSingleUnit;
	}
	public void setCostPriceForSingleUnit(Double costPriceForSingleUnit) {
		this.costPriceForSingleUnit = costPriceForSingleUnit;
	}
	public Double getSellingPriceForSingleUnit() {
		return sellingPriceForSingleUnit;
	}
	public void setSellingPriceForSingleUnit(Double sellingPriceForSingleUnit) {
		this.sellingPriceForSingleUnit = sellingPriceForSingleUnit;
	}
//	public UserDTO getUser() {
//		return user;
//	}
//	public void setUser(UserDTO user) {
//		this.user = user;
//	}
}
