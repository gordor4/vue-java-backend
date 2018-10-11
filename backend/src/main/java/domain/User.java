package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user", propOrder = {"id", "username", "password", "email"})
@Table(name = "user", schema = "public")
@Entity
@NamedQueries({
		@NamedQuery(name = "FIND_USER", query = "Select usr from User usr WHERE usr.username = :username"),
		@NamedQuery(name = "FIND_ALL", query = "Select usr from User usr"),
		@NamedQuery(name = "FIND_USER_WITH_EMAIL", query = "Select usr from User usr WHERE usr.email = :email")
})
public class User
{
	//TODO: Привести к актуальному состоянию таблицы в бд

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	public static final String FIND_USER = "FIND_USER";
	public static final String FIND_ALL = "FIND_ALL";
	public static final String FIND_USER_WITH_EMAIL = "FIND_USER_WITH_EMAIL";

	@Column(name = "username")
	@XmlAttribute
	private String username;

	@Column(name = "password")
	@XmlAttribute
	private String password;

	@Column(name = "email")
	@XmlAttribute
	private String email;

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

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
}
