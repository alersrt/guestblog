package org.student.guestblog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Document(collection = "roles")
public class Role {
	@Id
	private String id;

	@NotNull
	@Field("rolename")
	private String rolename;

	@NotNull
	@DBRef(lazy = true)
	private Set<User> users;

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Role role = (Role) o;

		if (!id.equals(role.id)) return false;
		return rolename.equals(role.rolename);
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + rolename.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Role{" +
				"id='" + id + '\'' +
				", rolename='" + rolename + '\'' +
				'}';
	}
}
