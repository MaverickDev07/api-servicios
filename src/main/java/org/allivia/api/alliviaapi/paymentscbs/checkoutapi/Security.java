package org.allivia.api.alliviaapi.paymentscbs.checkoutapi;

import javax.xml.bind.DatatypeConverter;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;

public class Security {
    private static final String HMAC_SHA256 = "HmacSHA256";
    // private static final String SECRET_KEY = "78e5d93d58314555a2bc5f24073e3514e000a5922bb248b6bf068ac0baa9fa93815ffdcbed0f4d2dbe33e4e93bafaf3250175c81872d414cb1284c3ff1ea395256f9a4b5be874e33abfd09d6eebf2b02d5b29b336917406387d00da092ba3a02a5503a1edd53461cac51dc69fe24f05e3e68806821f8458d813700c03a1d2677";

    private String secretKey;

    public Security(String secretKey) {
        this.secretKey = secretKey;
    }

    public String sign(HashMap params) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        return sign(buildDataToSign(params), secretKey);
    }

    private String sign(String data, String secretKey) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), HMAC_SHA256);
        Mac mac = Mac.getInstance(HMAC_SHA256);
        mac.init(secretKeySpec);
        byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
        return DatatypeConverter.printBase64Binary(rawHmac).replace("\n", "");
    }

    private String buildDataToSign(HashMap params) {
        String[] signedFieldNames = String.valueOf(params.get("signed_field_names")).split(",");
        ArrayList<String> dataToSign = new ArrayList<String>();
        for (String signedFieldName : signedFieldNames) {
            dataToSign.add(signedFieldName + "=" + String.valueOf(params.get(signedFieldName)));
        }
        return commaSeparate(dataToSign);
    }

    private String commaSeparate(ArrayList<String> dataToSign) {
        StringBuilder csv = new StringBuilder();
        for (Iterator<String> it = dataToSign.iterator(); it.hasNext(); ) {
            csv.append(it.next());
            if (it.hasNext()) {
                csv.append(",");
            }
        }
        return csv.toString();
    }
}
