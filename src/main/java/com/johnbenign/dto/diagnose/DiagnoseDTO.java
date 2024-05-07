package com.johnbenign.dto.diagnose;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Entity
@Component
public class DiagnoseDTO
{
	@Id
	private String diagnoseId;
	private String headache;
	private String fever;
	private String cold;
	private String bodyAche;
	private String tiredness;
	private String vomiting;
	private String cough;
	private String coughBloodOrMucus;
	private String coughDuration;
	private String chestPainWhenCoughingOrBreathing;
	private String weightLoss;
	private String thirst;
	private String rapidHeartRate;
	private String urinationInterval;
	
	public String getHeadache()
	{
		return headache;
	}
	public void setHeadache(String headache)
	{
		this.headache = headache;
	}
	public String getFever()
	{
		return fever;
	}
	public void setFever(String fever)
	{
		this.fever = fever;
	}
	public String getCold()
	{
		return cold;
	}
	public void setCold(String cold)
	{
		this.cold = cold;
	}
	public String getBodyAche()
	{
		return bodyAche;
	}
	public void setBodyAche(String bodyAche)
	{
		this.bodyAche = bodyAche;
	}
	public String getTiredness()
	{
		return tiredness;
	}
	public void setTiredness(String tiredness)
	{
		this.tiredness = tiredness;
	}
	public String getVomiting() {
		return vomiting;
	}
	public void setVomiting(String vomiting)
	{
		this.vomiting = vomiting;
	}
	public String getCough()
	{
		return cough;
	}
	public void setCough(String cough)
	{
		this.cough = cough;
	}
	public String getCoughBloodOrMucus()
	{
		return coughBloodOrMucus;
	}
	public void setCoughBloodOrMucus(String coughBloodOrMucus)
	{
		this.coughBloodOrMucus = coughBloodOrMucus;
	}
	public String getCoughDuration()
	{
		return coughDuration;
	}
	public void setCoughDuration(String coughDuration)
	{
		this.coughDuration = coughDuration;
	}
	public String getChestPainWhenCoughingOrBreathing()
	{
		return chestPainWhenCoughingOrBreathing;
	}
	public void setChestPainWhenCoughingOrBreathing(String chestPainWhenCoughingOrBreathing)
	{
		this.chestPainWhenCoughingOrBreathing = chestPainWhenCoughingOrBreathing;
	}
	public String getWeightLoss()
	{
		return weightLoss;
	}
	public void setWeightLoss(String weightLoss)
	{
		this.weightLoss = weightLoss;
	}
	public String getThirst()
	{
		return thirst;
	}
	public void setThirst(String thirst)
	{
		this.thirst = thirst;
	}
	public String getRapidHeartRate()
	{
		return rapidHeartRate;
	}
	public void setRapidHeartRate(String rapidHeartRate)
	{
		this.rapidHeartRate = rapidHeartRate;
	}
	public String getUrinationInterval()
	{
		return urinationInterval;
	}
	public void setUrinationInterval(String urinationInterval)
	{
		this.urinationInterval = urinationInterval;
	}
	public String getDiagnoseId() {
		return diagnoseId;
	}
	public void setDiagnoseId(String diagnoseId) {
		this.diagnoseId = diagnoseId;
	}
	
}
