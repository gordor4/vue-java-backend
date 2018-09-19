package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "budget", propOrder = { "id", "amount" })
@Table(name = "budget")
@NamedQuery(name = "getAllUserConsumption", query = "Select con from Consumption con")
public class Consumption
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private Double amount;
	private String description;

	public Consumption() {}

	public Consumption(Double amount, String description)
	{
		this.amount = amount;
		this.description = description;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Double getAmount()
	{
		return amount;
	}

	public void setAmount(Double amount)
	{
		this.amount = amount;
	}
}