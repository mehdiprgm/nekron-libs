package com.emperor.nekron.lib.securityx.cipher;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Provides file-based encryption and decryption using the AES/GCM/NoPadding algorithm.
 * <p>
 * This class encrypts a file by generating a random initialization vector (IV),
 * prepending it to the encrypted content, and writing the result to the output file.
 * During decryption, the IV is extracted from the beginning of the file and used
 * to recover the original data.
 * <p>
 * The same {@link SecretKey} instance is used for both encryption
 * and decryption operations.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 2.0
 * @since 21
 */
@Data
@AllArgsConstructor
public class FileCipher {
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH = 128;

    private SecretKey secretKey;

    private void atomicReplace(Path temp, Path target) throws IOException {
        try {
            Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException ex) {
            Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /**
     * Encrypts the contents of the given input file and writes the encrypted result
     * to the specified output file.
     * <p>
     * A random IV is generated for each call and stored at the beginning of the
     * output file, followed by the encrypted data.
     *
     * @param inputFile  the path of the file to encrypt
     * @param outputFile the path of the file where the encrypted data will be written
     * @throws IOException                        if an I/O error occurs while reading or writing files
     * @throws NoSuchPaddingException             if the requested padding scheme is not available
     * @throws NoSuchAlgorithmException           if the AES algorithm is not available
     * @throws InvalidAlgorithmParameterException if the provided algorithm parameters are invalid
     * @throws InvalidKeyException                if the provided secret key is invalid
     * @throws IllegalBlockSizeException          if the block size is invalid for the cipher
     * @throws BadPaddingException                if the padding of the input data is incorrect
     */
    public void encrypt(String inputFile, String outputFile) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] iv = new byte[IV_LENGTH];
        new SecureRandom().nextBytes(iv);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(TAG_LENGTH, iv));

        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(iv);

            try (CipherOutputStream cos = new CipherOutputStream(fos, cipher); FileInputStream fis = new FileInputStream(inputFile)) {
                byte[] buffer = new byte[8192];
                int read;

                while ((read = fis.read(buffer)) != -1) {
                    cos.write(buffer, 0, read);
                }
            }
        }
    }

    /**
     * Decrypts the contents of the given input file and writes the recovered data
     * to the specified output file.
     * <p>
     * The IV is expected to be stored at the beginning of the input file, followed
     * by the encrypted data.
     *
     * @param inputFile  the path of the encrypted file to decrypt
     * @param outputFile the path of the file where the decrypted data will be written
     * @throws IOException                        if an I/O error occurs while reading or writing files
     * @throws NoSuchPaddingException             if the requested padding scheme is not available
     * @throws NoSuchAlgorithmException           if the AES algorithm is not available
     * @throws InvalidAlgorithmParameterException if the provided algorithm parameters are invalid
     * @throws InvalidKeyException                if the provided secret key is invalid
     * @throws IllegalBlockSizeException          if the block size is invalid for the cipher
     * @throws BadPaddingException                if the padding of the input data is incorrect
     */
    public void decrypt(String inputFile, String outputFile) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        try (FileInputStream fis = new FileInputStream(inputFile)) {
            byte[] iv = new byte[IV_LENGTH];
            if (fis.read(iv) != IV_LENGTH) {
                throw new IOException("Invalid encrypted file: missing IV");
            }

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(TAG_LENGTH, iv));

            try (CipherInputStream cis = new CipherInputStream(fis, cipher); FileOutputStream fos = new FileOutputStream(outputFile)) {
                byte[] buffer = new byte[8192];
                int read;

                while ((read = cis.read(buffer)) != -1) {
                    fos.write(buffer, 0, read);
                }
            }
        }
    }

    /**
     * Encrypts the specified file in place.
     * <p>
     * The file is first encrypted to a temporary file, and then the temporary file
     * atomically replaces the original file. If the atomic move is not supported
     * by the underlying file system, a regular replace operation is performed.
     *
     * @param filePath the path of the file to encrypt in place
     * @throws InvalidAlgorithmParameterException if the provided algorithm parameters are invalid
     * @throws NoSuchPaddingException             if the requested padding scheme is not available
     * @throws IllegalBlockSizeException          if the block size is invalid for the cipher
     * @throws IOException                        if an I/O error occurs while reading or writing files
     * @throws NoSuchAlgorithmException           if the AES algorithm is not available
     * @throws BadPaddingException                if the padding of the input data is incorrect
     * @throws InvalidKeyException                if the provided secret key is invalid
     */
    public void encryptSameFile(String filePath) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Path original = Paths.get(filePath);
        Path temp = original.resolveSibling(original.getFileName().toString() + ".tmp");

        encrypt(original.toString(), temp.toString());
        atomicReplace(temp, original);
    }

    /**
     * Decrypts the specified file in place.
     * <p>
     * The file is first decrypted to a temporary file, and then the temporary file
     * atomically replaces the original file. If the atomic move is not supported
     * by the underlying file system, a regular replace operation is performed.
     *
     * @param filePath the path of the encrypted file to decrypt in place
     * @throws InvalidAlgorithmParameterException if the provided algorithm parameters are invalid
     * @throws NoSuchPaddingException             if the requested padding scheme is not available
     * @throws IllegalBlockSizeException          if the block size is invalid for the cipher
     * @throws IOException                        if an I/O error occurs while reading or writing files
     * @throws NoSuchAlgorithmException           if the AES algorithm is not available
     * @throws BadPaddingException                if the padding of the input data is incorrect
     * @throws InvalidKeyException                if the provided secret key is invalid
     */
    public void decryptSameFile(String filePath) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Path original = Paths.get(filePath);
        Path temp = original.resolveSibling(original.getFileName().toString() + ".tmp");

        decrypt(original.toString(), temp.toString());
        atomicReplace(temp, original);
    }
}