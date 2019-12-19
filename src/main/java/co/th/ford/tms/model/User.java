package co.th.ford.tms.model;


import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="tb_user")
public class User {	
	
	public User(){}
	
	@Id
	@Size(min=8, max=20)
	@Column(name = "username", nullable = false)
	private String username;	
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//private int id;
	
	@Size(min=8, max=30)
	@Column(name = "password", nullable = false)
	private String password;
		
	@Column(name = "name", nullable = false)
	private String name;

	/* @NotNull */
	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") 
	@Column(name = "joining_date", nullable = true)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate joiningDate;

	@Size(min=4, max=10)
	@Column(name = "role", nullable = false)
	private String role;	
	
	@Column(name = "department", nullable = false)
	private String department;
	
	@Column(name = "lastname", nullable = false)
	private String lastname;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "contactnumber", nullable = false)
	private String contactnumber;
	
	@Column(name = "status", nullable = false)
	private int status;
	
	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") 
	@Column(name = "last_login", nullable = false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime LogoutDate;
	
	@Override
	public int hashCode() {		
		return username.hashCode();
	}	
	 
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (!username.equals(other.username))
			return false;
		
		return true;
	}
	
	@Override
	public String toString() {
		return "User [username=" + username + ", name=" + name + ", joiningDate="
				+ joiningDate + ", role=" + role + ", department=" + department
				+", status=" + status  + "]";
	}		
	
}
