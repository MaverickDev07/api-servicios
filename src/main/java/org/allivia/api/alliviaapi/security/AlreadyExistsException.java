package org.allivia.api.alliviaapi.security;

import org.springframework.security.core.AuthenticationException;

public class AlreadyExistsException extends AuthenticationException {
    public AlreadyExistsException(String msg) {
        super(msg);
    }
}
