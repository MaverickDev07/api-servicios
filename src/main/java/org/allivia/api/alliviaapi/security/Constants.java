package org.allivia.api.alliviaapi.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class Constants {

	// Spring Security
	public static final String LOGIN_URL = "/login";
	public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
	public static final String TOKEN_BEARER_PREFIX = "Bearer ";

	// JWT
	public static final String ISSUER_INFO = "https://www.autentia.com/";
	public static final String SUPER_SECRET_KEY = "1234";
	public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 day
	public static String generateToken(String userName){
		return Jwts.builder()
				.setIssuedAt(new Date()).setIssuer(ISSUER_INFO)
				.setSubject(userName)
//				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SUPER_SECRET_KEY).compact();
	}
}
