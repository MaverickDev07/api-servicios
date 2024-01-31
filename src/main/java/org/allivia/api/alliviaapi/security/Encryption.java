package org.allivia.api.alliviaapi.security;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Encryption {
    public static final Logger logger = LogManager.getLogger(Encryption.class);
    private SecretKeySpec createKey(String valueKey) throws NoSuchAlgorithmException {
        byte[] emcryptionKey = valueKey.getBytes(StandardCharsets.UTF_8);
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        emcryptionKey = sha.digest(emcryptionKey);
        emcryptionKey = Arrays.copyOf(emcryptionKey, 16);
        return new SecretKeySpec(emcryptionKey, "AES");
    }

    public String encrypt(String data, String key) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String result = "";
        try {
            SecretKeySpec secretKey = this.createKey(key);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] dataEncrypt = data.getBytes(StandardCharsets.UTF_8);
            byte[] dataEncrypted = cipher.doFinal(dataEncrypt);
            result = Base64.getEncoder().encodeToString(dataEncrypted);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            logger.log(Level.ERROR,"Error al encriptar");
            throw e;
        }
        return result;
    }

    public String decrypt(String dataEncrypted, String key) throws Exception {
        String result;
        try {
            SecretKeySpec secretKey = this.createKey(key);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] bytesEncrypted = Base64.getDecoder().decode(dataEncrypted);
            byte[] dataDecrypt = cipher.doFinal(bytesEncrypted);
            result = new String(dataDecrypt);
        } catch ( IllegalBlockSizeException e){
            logger.log(Level.ERROR,"Valor de desencriptacion no válido");
            throw new Exception("Valor de desencriptacion no válido");
        }catch ( BadPaddingException e){
            logger.log(Level.ERROR,"Llave no válida");
            throw new Exception("Llave no válida");
        } catch (Exception e){
            logger.log(Level.ERROR,"Error al desencriptar");
            throw e;
        }
       // validate(result);
        return result;
    }

    private boolean validate(String value){
        Pattern pat = Pattern.compile("[a-zA-Z]");
        Matcher mat = pat.matcher(value);
        if (mat.matches()) {
            System.out.println("SI");
            return true;
        } else {
            System.out.println("NO");
            return false;
        }
    }
}
