package com.convo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.convo.communicator.UserServiceCommunicator;
import com.convo.datamodel.User;
import com.convo.util.SystemContextHolder;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserServiceCommunicator userServiceCommunicator;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader("Authorization");
		try {
			User user = userServiceCommunicator.authenticate(token);
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					user, null, user.getAuthorities());
			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SystemContextHolder.setLoggedInUser(user);
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
	}

}
