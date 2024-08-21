package com.convo.datamodel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import org.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class User extends DbBaseModel implements UserDetails {
	private static final long serialVersionUID = 1L;
	private String userId;
	private String name;
	private String email;
	private String phone;
	private Boolean enabled;

	public static User createUserFromJsonObjet(JSONObject jsonObject) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss");
		User user = User.builder().build();
		user.setId(jsonObject.getLong("id"));
		user.setUserId(jsonObject.getString("userId"));
		user.setName(jsonObject.getString("name"));
		user.setEmail(jsonObject.getString("email"));
		user.setPhone(jsonObject.getString("phone"));
		user.setEnabled(jsonObject.getBoolean("enabled"));
		user.setCreatedOn(LocalDateTime.parse(jsonObject.getString("createdOn"), formatter));
		user.setProcessedOn(LocalDateTime.parse(jsonObject.getString("processedOn"), formatter));
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}