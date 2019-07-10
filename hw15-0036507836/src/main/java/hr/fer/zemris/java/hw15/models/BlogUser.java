package hr.fer.zemris.java.hw15.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="blog_users")
@Cacheable(true)
public class BlogUser implements Serializable{

	@Id @GeneratedValue
	private Long id;
	
	@Column(length=20,nullable=false)
	private String firstName;
	
	@Column(length=30,nullable=false)
	private String lastName;
	
	@Column(length=20,nullable=false,unique=true)
	private String nick;
	
	@Column(length=30,nullable=false)
	private String email;
	
	@Column(nullable=false)
	private String passwordHash;
	
	@OneToMany(mappedBy="creator",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	private List<BlogEntry> blogEntries;
	
	public BlogUser() {
		// TODO Auto-generated constructor stub
	}
	
	public BlogUser(Long id, String firstName, String lastName, String nick, String email, String passwordHash) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.nick = nick;
		this.email = email;
		this.passwordHash = passwordHash;
		blogEntries = new ArrayList<BlogEntry>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, id, lastName, nick, passwordHash);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BlogUser))
			return false;
		BlogUser other = (BlogUser) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName) && id == other.id
				&& Objects.equals(lastName, other.lastName) && Objects.equals(nick, other.nick)
				&& Objects.equals(passwordHash, other.passwordHash);
	}

}
