package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user", propOrder = {"id", "username", "password"})
@Table(name = "user", schema = "public")
@Entity
@NamedQueries({
		@NamedQuery(name = "FIND_USER", query = "Select usr from User usr WHERE usr.username = :username and usr.password = :password"),
		@NamedQuery(name = "FIND_ALL", query = "Select usr from User usr")
})
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	public static final String FIND_USER = "FIND_USER";
	public static final String FIND_ALL = "FIND_ALL";

	@Column(name = "username")
	@XmlAttribute
	private String username;

//	@Column(name = "email")
//	private String email;

	@Column(name = "password")
	@XmlAttribute
	private String password;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}
