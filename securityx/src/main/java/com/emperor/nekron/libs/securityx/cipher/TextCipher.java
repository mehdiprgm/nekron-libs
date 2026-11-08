package com.emperor.nekron.libs.securityx.cipher;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * Provides password-based encryption and decryption for text using AES/GCM/NoPadding
 * with PBKDF2 key derivation.
 * <p>
 * This class is intended for encrypting and decrypting string data using a
 * user-supplied password. A random salt and IV are generated for every encryption
 * operation and combined with the cipher text into a single Base64-encoded output.
 * During decryption, the salt and IV are extracted from the input and used to
 * re-derive the key and recover the original text.
 * <p>
 * The same password must be used for both encryption and decryption operations.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 2.0
 * @since 21
 */
@Data
@AllArgsConstructor
public class TextCipher {
    private static final int AES_KEY_SIZE = 256;
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 128;
    private static final int SALT_LENGTH = 16;
    private static final int PBKDF2_ITERATIONS = 600000;

    private String password;

    private SecretKey deriveKey(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, PBKDF2_ITERATIONS, AES_KEY_SIZE);

        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    /**
     * Encrypts the given text using AES/GCM/NoPadding with a key derived from the
     * configured password.
     * <p>
     * A random salt and IV are generated for each call and combined with the
     * cipher text into a single Base64-encoded string.
     *
     * @param text the plain text to encrypt
     * @return a Base64-encoded string containing the salt, IV, and encrypted data
     * @throws NoSuchPaddingException             if the requested padding scheme is not available
     * @throws NoSuchAlgorithmException           if the AES algorithm is not available
     * @throws InvalidAlgorithmParameterException if the provided algorithm parameters are invalid
     * @throws InvalidKeyException                if the provided secret key is invalid
     * @throws IllegalBlockSizeException          if the block size is invalid for the cipher
     * @throws BadPaddingException                if the padding of the input data is incorrect
     * @throws InvalidKeySpecException            if the provided key specification is invalid
     */
    public String encrypt(String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        byte[] salt = new byte[SALT_LENGTH];
        byte[] iv = new byte[GCM_IV_LENGTH];

        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        random.nextBytes(iv);

        SecretKey key = deriveKey(password, salt);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);

        byte[] cipherText = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        ByteBuffer buffer = ByteBuffer.allocate(salt.length + iv.length + cipherText.length);

        buffer.put(salt);
        buffer.put(iv);
        buffer.put(cipherText);

        return Base64.getEncoder().encodeToString(buffer.array());
    }

    /**
     * Decrypts the given Base64-encoded string using AES/GCM/NoPadding with a key
     * derived from the configured password.
     * <p>
     * The input is expected to contain the salt and IV at the beginning, followed
     * by the encrypted data. This is the same format produced by the encrypt method.
     *
     * @param encryptedText the Base64-encoded string to decrypt
     * @return the recovered plain text
     * @throws NoSuchAlgorithmException           if the AES algorithm is not available
     * @throws InvalidKeySpecException            if the provided key specification is invalid
     * @throws NoSuchPaddingException             if the requested padding scheme is not available
     * @throws InvalidAlgorithmParameterException if the provided algorithm parameters are invalid
     * @throws InvalidKeyException                if the provided secret key is invalid
     * @throws IllegalBlockSizeException          if the block size is invalid for the cipher
     * @throws BadPaddingException                if the padding of the input data is incorrect
     */
    public String decrypt(String encryptedText) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] decoded = Base64.getDecoder().decode(encryptedText);
        ByteBuffer buffer = ByteBuffer.wrap(decoded);

        byte[] salt = new byte[SALT_LENGTH];
        buffer.get(salt);

        byte[] iv = new byte[GCM_IV_LENGTH];
        buffer.get(iv);

        byte[] cipherText = new byte[buffer.remaining()];
        buffer.get(cipherText);

        SecretKey key = deriveKey(password, salt);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, spec);

        byte[] plainText = cipher.doFinal(cipherText);
        return new String(plainText, StandardCharsets.UTF_8);
    }
}