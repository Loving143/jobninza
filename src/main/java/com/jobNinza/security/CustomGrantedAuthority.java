package com.jobNinza.security;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthority implements GrantedAuthority{

	private String authority;
	public CustomGrantedAuthority(String authority) {
		this.authority = authority;
	}
	@Override
	public @Nullable String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
