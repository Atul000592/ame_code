package nic.ame.app.security.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import nic.ame.app.master.model.Login;
import nic.ame.app.master.model.UserRole;

public class UserInfoServiceMapping  implements UserDetails{

	
	private static final long serialVersionUID = 1L;

	private String userName;
	private String password;
	List<GrantedAuthority> authorities;
	
	
	public UserInfoServiceMapping(Login user) {
		userName=user.getUserName();
		password=user.getPassword();
	   
		List<nic.ame.app.master.model.UserRole> userRoles= user.getUserRoles();
		
	    List<String> userRoleName=userRoles.stream().map(s->s.getRoleId()).collect(Collectors.toList());
		authorities=userRoleName.stream().map(SimpleGrantedAuthority::new).
				collect(Collectors.toList());
				
		
	}
	
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
