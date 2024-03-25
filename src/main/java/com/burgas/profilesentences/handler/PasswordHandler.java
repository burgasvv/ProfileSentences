package com.burgas.profilesentences.handler;

import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHandler {

    public static final String HASHING_ALGORITHM = "SHA-256";

    public static String hashPassword(String password) throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance(HASHING_ALGORITHM);
        byte[] bytes = messageDigest.digest(password.getBytes());

        return Base64.toBase64String(bytes);
    }
}
