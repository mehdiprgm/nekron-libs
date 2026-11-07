package com.emperor.nekron.lib.securityx.hash.argon2id;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Provides password hashing using the Argon2id algorithm.
 * <p>
 * Argon2id is a memory-hard and computationally expensive key derivation function,
 * designed to resist brute-force, dictionary, and GPU-based attacks. It is the
 * recommended algorithm for storing passwords securely.
 * <p>
 * The hash method generates a random salt for each call and returns the result
 * as a PHC-formatted string that contains the algorithm identifier, version,
 * parameters, salt, and hash. This format allows the hash to be verified later
 * without needing to store any additional information.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 1.0
 * @since 21
 */
public class Argon2id {
    /* OWASP 2025 recommendation */
    private static final int ITERATIONS = 3;
    private static final int MEMORY_KB = 65536; /* 64MB */
    private static final int PARALLELISM = 1;
    private static final int HASH_LENGTH = 32; /* 256 Bit */
    private static final int SALT_LENGTH = 16; /* 128 Bit */

    /**
     * Hashes the given text using Argon2id with a randomly generated salt.
     * <p>
     * The returned string is in PHC format and includes all parameters required
     * for later verification, such as the salt and configuration values.
     *
     * @param text the plain text to hash
     * @return a PHC-formatted string containing the Argon2id hash and its parameters
     */
    public static String hash(String text) {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);

        Argon2Parameters params = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
                .withVersion(Argon2Parameters.ARGON2_VERSION_13) /* RFC 9106 */
                .withSalt(salt)
                .withIterations(ITERATIONS)
                .withMemoryAsKB(MEMORY_KB)
                .withParallelism(PARALLELISM)
                .build();

        Argon2BytesGenerator generator = new Argon2BytesGenerator();
        generator.init(params);

        byte[] hash = new byte[HASH_LENGTH];
        generator.generateBytes(text.toCharArray(), hash);

        return "$argon2id$v=19$m=65536,t=3,p=1$" +
                Base64.getEncoder().encodeToString(salt) + "$" +
                Base64.getEncoder().encodeToString(hash);
    }
}