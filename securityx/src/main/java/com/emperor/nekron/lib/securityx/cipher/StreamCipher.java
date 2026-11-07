package com.emperor.nekron.lib.securityx.cipher;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.crypto.SecretKey;
import java.security.SecureRandom;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Provides in-memory encryption and decryption for byte arrays using the AES/GCM/NoPadding algorithm.
 * <p>
 * This class is intended for working with raw byte data that is already loaded into memory.
 * A random initialization vector (IV) is generated for every encryption operation and
 * prepended to the encrypted output. During decryption, the IV is extracted from the
 * beginning of the input and used to recover the original data.
 * <p>
 * The same {@link SecretKey} instance is used for both encryption and
 * decryption operations.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 2.0
 * @since 21
 */
@Data
@AllArgsConstructor
public class StreamCipher {
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private final SecureRandom random = new SecureRandom();

    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH = 128;

    private SecretKey key;

    /**
     * Encrypts the given byte array using AES/GCM/NoPadding.
     * <p>
     * A random IV is generated for each call and prepended to the encrypted output.
     * The returned array contains the IV followed by the cipher text, and can be
     * passed directly to the decrypt method to recover the original data.
     *
     * @param data the plain byte array to encrypt
     * @return a new byte array containing the IV followed by the encrypted data
     * @throws NoSuchPaddingException             if the requested padding scheme is not available
     * @throws NoSuchAlgorithmException           if the AES algorithm is not available
     * @throws InvalidAlgorithmParameterException if the provided algorithm parameters are invalid
     * @throws InvalidKeyException                if the provided secret key is invalid
     * @throws IllegalBlockSizeException          if the block size is invalid for the cipher
     * @throws BadPaddingException                if the padding of the input data is incorrect
     */
    public byte[] encrypt(byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] iv = new byte[IV_LENGTH];
        random.nextBytes(iv);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, iv);

        cipher.init(Cipher.ENCRYPT_MODE, key, spec);

        byte[] encrypted = cipher.doFinal(data);
        byte[] result = new byte[IV_LENGTH + encrypted.length];

        System.arraycopy(iv, 0, result, 0, IV_LENGTH);
        System.arraycopy(encrypted, 0, result, IV_LENGTH, encrypted.length);

        return result;
    }

    /**
     * Decrypts the given byte array using AES/GCM/NoPadding.
     * <p>
     * The input is expected to contain the IV at the beginning, followed by the
     * encrypted data. This is the same format produced by the encrypt method.
     *
     * @param encryptedData the encrypted byte array to decrypt, including the prepended IV
     * @return a new byte array containing the recovered plain data
     * @throws NoSuchPaddingException             if the requested padding scheme is not available
     * @throws NoSuchAlgorithmException           if the AES algorithm is not available
     * @throws InvalidAlgorithmParameterException if the provided algorithm parameters are invalid
     * @throws InvalidKeyException                if the provided secret key is invalid
     * @throws IllegalBlockSizeException          if the block size is invalid for the cipher
     * @throws BadPaddingException                if the padding of the input data is incorrect
     */
    public byte[] decrypt(byte[] encryptedData) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] iv = Arrays.copyOfRange(encryptedData, 0, IV_LENGTH);
        byte[] cipherText = Arrays.copyOfRange(encryptedData, IV_LENGTH, encryptedData.length);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, iv);

        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        return cipher.doFinal(cipherText);
    }
}