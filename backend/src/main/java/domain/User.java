package domain;

import javax.persistence.*;
import java.util.Date;

@Table(name = "user", schema = "public")
@Entity
@NamedQueries({
		@NamedQuery(name = "FIND_USER", query = "Select usr from User usr WHERE usr.username = :username"),
		@NamedQuery(name = "FIND_ALL", query = "Select usr from User usr"),
		@NamedQuery(name = "FIND_USER_WITH_EMAIL", query = "Select usr from User usr WHERE usr.email = :email")
})
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	public static final String FIND_USER = "FIND_USER";
	public static final String FIND_ALL = "FIND_ALL";
	public static final String FIND_USER_WITH_EMAIL = "FIND_USER_WITH_EMAIL";
	public static final String EMAIL_PARAM = "email";

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	private boolean activated;
	private boolean banned;

	@Column(name = "avatar_id")
	private Integer avatarId;

	@Column(name = "creation_date")
	@Temporal(TemporalType.DATE)
	private Date creationDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id")
	private Avatar avatar;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "second_name")
	private String secondName;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static String getFindUser() {
		return FIND_USER;
	}

	public static String getFindAll() {
		return FIND_ALL;
	}

	public static String getFindUserWithEmail() {
		return FIND_USER_WITH_EMAIL;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public boolean isBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Avatar getAvatar() {
		return avatar;
	}

	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public int getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(int avatarId) {
		this.avatarId = avatarId;
	}
}
