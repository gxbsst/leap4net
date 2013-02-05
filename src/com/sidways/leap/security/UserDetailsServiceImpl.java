package com.sidways.leap.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.sidways.leap.dao.UserDao;
import com.sidways.leap.entity.Role;
import com.sidways.leap.entity.User;

/**
 * @author Kim 2012-6-18
 */
public class UserDetailsServiceImpl implements UserDetailsService {

	private final static UserDetails EMPTY = new UserDetails(){

		private static final long serialVersionUID = 1L;
		
		private GrantedAuthority[] getAuthorities;
		
		{
			this.getAuthorities = new GrantedAuthority[]{};
		}
		
		@Override
		public GrantedAuthority[] getAuthorities() {
			return getAuthorities;
		}

		@Override
		public String getPassword() {
			return "-1";
		}

		@Override
		public String getUsername() {
			return "-1";
		}

		@Override
		public boolean isAccountNonExpired() {
			return false;
		}

		@Override
		public boolean isAccountNonLocked() {
			return false;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return false;
		}

		@Override
		public boolean isEnabled() {
			return false;
		}
	};
	
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
		User user = this.userDao.getByUsername(userName);
		if (user != null) {
			return new LoginDetailsImpl(user.getUsername(), user.getPassword(), this.collectGrantedAuthority(user.getRole()));
		}
		return UserDetailsServiceImpl.EMPTY;
	}

	protected GrantedAuthority[] collectGrantedAuthority(Role role) {
		String[] roleLevel = role.getRole().split(";");
		GrantedAuthority[] list = new GrantedAuthority[roleLevel.length];
		for (int index = 0; index < roleLevel.length; index++) {
			list[index] = new GrantedAuthorityImpl(roleLevel[index]);
		}
		return list;
	}
	
	public static class GrantedAuthorityImpl implements GrantedAuthority {

		private static final long serialVersionUID = 1L;
		
		private String role;

		public GrantedAuthorityImpl(String role) {
			this.role = role;
		}

		public String getAuthority() {
			return role;
		}

		public int compareTo(Object t) {
			return 0;
		}
	}
	
	public static class LoginDetailsImpl implements UserDetails {

		private static final long serialVersionUID = 1L;

		private String username;

		private String password;

		private GrantedAuthority[] authorities;;

		public LoginDetailsImpl(String username, String password, GrantedAuthority[] authorities) {
			super();
			this.username = username;
			this.password = password;
			this.authorities = authorities;
		}

		public GrantedAuthority[] getAuthorities() {
			return authorities;
		}

		public String getUsername() {
			return username;
		}

		public String getPassword() {
			return password;
		}

		public boolean isAccountNonExpired() {
			return true;
		}

		public boolean isAccountNonLocked() {
			return true;
		}

		public boolean isCredentialsNonExpired() {
			return true;
		}

		public boolean isEnabled() {
			return true;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
	}
}
