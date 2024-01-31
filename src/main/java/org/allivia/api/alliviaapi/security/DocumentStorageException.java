package org.allivia.api.alliviaapi.security;

/**
 * Created by yeri_ on 31/03/2021.
 */
public class DocumentStorageException  extends RuntimeException{

    public DocumentStorageException(String message) {

        super(message);

    }


    public DocumentStorageException(String message, Throwable cause) {

        super(message, cause);

    }
}
