package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "payment", propOrder = { "id", "amount", "description" })
@Table(name = "payment")
@NamedQuery(name = "getAllUserConsumption", query = "Select con from Payment con WHERE con.owner.id = :owner_id")
public class Payment
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private Double amount;
	private String description;

	@ManyToOne
	private User owner;

	public Payment() {}

	public Payment(Double amount, String description)
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