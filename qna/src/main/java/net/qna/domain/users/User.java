package net.qna.domain.users;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class User { 
	@NotEmpty @Size(min=4, max=12)
	private String userId;
	@NotEmpty @Size(min=4, max=12)
	private String password;
	@NotEmpty
	private String name;
	@Email
	private String email;
	
	private String profileimg;
	private Date joindate;
	public User(){
		
	}
	
	public User(String userId, String password, String name, String email, String profileimg, Date joindate) {
		super();
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.profileimg = profileimg;
		this.joindate = joindate;
	}
	
	
	public Date getJoindate() {
		return joindate;
	}

	public void setJoindate(Date joindate) {
		this.joindate = joindate;
	}

	public String getProfileimg() {
		return profileimg;
	}

	public void setProfileimg(String profileimg) {
		this.profileimg = profileimg;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean matchPassword(Authenticate authenticate){
		if(this.password ==null){
			return false;
		}
		return authenticate.matchPassword(this.password);
	}
	public boolean matchUserId(String inputuserId) {
		if(inputuserId == null){
			return false;
		}
		return inputuserId.equals(this.userId);
	}
	
	public User update(User updateUser){
		if(!matchUserId(updateUser.userId)){
			throw new IllegalArgumentException();
		}
		
		return new User(this.userId, updateUser.password, updateUser.name, updateUser.email ,updateUser.profileimg, updateUser.joindate);
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + ", profileimg="+profileimg+", joindate=" +joindate+" ]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	
}
