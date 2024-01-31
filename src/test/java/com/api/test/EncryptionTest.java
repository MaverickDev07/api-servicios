package com.api.test;

import org.allivia.api.alliviaapi.security.Encryption;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class EncryptionTest extends Encryption {

    @Test
    public void testEncrypt() throws Exception {
        String dataEncrypted = encrypt("4218287004315365","12");
        System.out.println(dataEncrypted);

    }
    @Test
    public void testDecrypt() throws Exception {
        Assert.assertEquals("4218287004315365",decrypt("4B5ZsQvfY9wEW5rUnOeroLtLY9r8sFBmDLSIhTiHgDQ=","12"));
    }

    @Test
    public void testGetLastNumber() throws Exception {
        String cardNumber ="4218287004315365";

        System.out.println(cardNumber.substring(cardNumber.length()-4));
    }
}