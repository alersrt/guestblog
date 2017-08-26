package org.student.guestblog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Document(collection = "users")
public class User {
	@Id
	private String id;

	@NotNull(message = "required")
	@Field("username")
	private String username;

	@NotNull(message = "required")
	@Field("password")
	private String password;

	@NotNull(message = "required")
	@Email(message = "email.correct")
	@Field("email")
	private String email;

	@NotNull
	@DBRef(lazy = true)
	private Set<Role> roles;

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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (!id.equals(user.id)) return false;
		if (!username.equals(user.username)) return false;
		if (!password.equals(user.password)) return false;
		return email.equals(user.email);
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + username.hashCode();
		result = 31 * result + password.hashCode();
		result = 31 * result + email.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", username='" + username + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
