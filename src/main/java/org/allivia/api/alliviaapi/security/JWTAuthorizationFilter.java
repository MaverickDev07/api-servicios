package org.allivia.api.alliviaapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.ArrayList;

import static org.allivia.api.alliviaapi.security.Constants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(HEADER_AUTHORIZACION_KEY);
		logger.info(header);
		if(req.getServletPath().startsWith("/users/add")){
			chain.doFilter(req, res);
			return;
		}
		if (header == null || !header.startsWith(TOKEN_BEARER_PREFIX)) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}else{
			try {
				UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				chain.doFilter(req, res);
			}catch (ExpiredJwtException e){
				res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			} catch (SignatureException e){
				res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}catch (Exception e){
				res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		if (token != null) {
			// Se procesa el token y se recupera el usuario.
			Claims claims = Jwts.parser()
					.setSigningKey(DatatypeConverter.parseBase64Binary(SUPER_SECRET_KEY))
					.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, "")).getBody();
			String user = claims.getSubject();
			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}
			return null;
		}
		return null;
	}
}
