package org.iksoft.Security;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author IK
 *
 * Hashing is more secure than Encryption
 * because it's one-way function
 * also keys must be saved in memory on encryption
 */

public class PasswordHashEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("md5");

            byte[] hashByte = md.digest(
                    input.toString().getBytes(StandardCharsets.UTF_8)
            );

            return new BigInteger(1, hashByte).toString();

        } catch (NoSuchAlgorithmException e) {
            return "0";
        }
    }

    @Override
    public boolean matches(CharSequence input, String password) {
        return encode(input).equals(password);
    }
}
