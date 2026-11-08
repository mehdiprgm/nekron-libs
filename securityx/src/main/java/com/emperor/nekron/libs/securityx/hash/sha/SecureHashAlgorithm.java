package com.emperor.nekron.libs.securityx.hash.sha;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Provides cryptographic hashing using the SHA-2 family of algorithms.
 * <p>
 * This class supports SHA-256 and SHA-512 through the {@code Algorithm} enum.
 * It can hash both raw byte arrays and text, and returns either raw bytes or
 * a lowercase hexadecimal string depending on the chosen method.
 * <p>
 * SHA-2 is a one-way hash function suitable for data integrity checks and
 * digital signatures. It is not recommended for password storage; use a
 * dedicated password hashing algorithm instead.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 1.0
 * @since 21
 */
public class SecureHashAlgorithm {
    private static String bytesToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte byt : bytes) {
            sb.append(String.format("%02x", byt));
        }

        return sb.toString();
    }

    private static String getAlgorithm(Algorithm algorithm) {
        return switch (algorithm) {
            case SHA256 -> "SHA-256";
            case SHA512 -> "SHA-512";
        };
    }

    public enum Algorithm {
        SHA256,
        SHA512
    }


    /**
     * Hashes the given byte array using the specified SHA-2 algorithm.
     *
     * @param algorithm the SHA-2 algorithm to use
     * @param data      the byte array to hash
     * @return a new byte array containing the raw hash
     * @throws NoSuchAlgorithmException if the requested algorithm is not available
     */
    public static byte[] hash(Algorithm algorithm, byte[] data) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(getAlgorithm(algorithm)).digest(data);
    }

    /**
     * Hashes the given text using the specified SHA-2 algorithm.
     * <p>
     * The text is encoded as UTF-8 before hashing. The returned string is the
     * lowercase hexadecimal representation of the hash.
     *
     * @param algorithm the SHA-2 algorithm to use
     * @param text      the text to hash
     * @return a lowercase hexadecimal string representing the hash
     * @throws NoSuchAlgorithmException if the requested algorithm is not available
     */
    public static String hash(Algorithm algorithm, String text) throws NoSuchAlgorithmException {
        return bytesToString(MessageDigest.getInstance(getAlgorithm(algorithm)).digest(
                text.getBytes(StandardCharsets.UTF_8)
        ));
    }
}