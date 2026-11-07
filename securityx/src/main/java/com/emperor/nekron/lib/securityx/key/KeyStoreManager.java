package com.emperor.nekron.lib.securityx.key;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

/**
 * Manages AES secret keys stored in a PKCS12 keystore file.
 * <p>
 * This class provides a simple way to create a keystore file containing a
 * randomly generated AES-256 key, and to load that key back for later use.
 * The keystore and the stored key entry are both protected by the configured
 * password.
 * <p>
 * Typical usage involves calling {@code createKeyStore} once to initialize the
 * keystore, and then calling {@code loadKey} whenever the key is needed.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 3.0
 * @since 21
 */
@Data
@AllArgsConstructor
public class KeyStoreManager {
    private String filePath;
    private String password;
    private String keyName;

    /**
     * Creates a new PKCS12 keystore file containing a randomly generated AES-256 key.
     * <p>
     * The keystore and the key entry are protected by the configured password.
     * If the target file already exists and overwrite is not allowed, the method
     * returns without making any changes.
     *
     * @param overwriteFile whether to overwrite the existing keystore file if it already exists
     * @throws KeyStoreException        if the PKCS12 keystore type is not available
     * @throws CertificateException     if any certificate in the keystore cannot be loaded
     * @throws IOException              if an I/O error occurs while writing the keystore file
     * @throws NoSuchAlgorithmException if the AES algorithm is not available
     */
    public void createKeyStore(boolean overwriteFile) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        Path keyStoreFile = Path.of(filePath);
        if (Files.exists(keyStoreFile) && !overwriteFile) {
            return;
        }

        /* Standard */
        KeyStore ks = KeyStore.getInstance("PKCS12");

        /* Load nothing, create new keystore */
        ks.load(null, null);

        /* Create generator for encryption */
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");

        /* Key length : 256 bit */
        keyGen.init(256);

        SecretKey secretKey = keyGen.generateKey();

        /* Store SecretKey into KeyStore */
        KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(secretKey);

        /* Password protection */
        KeyStore.ProtectionParameter protection = new KeyStore.PasswordProtection(password.toCharArray());

        ks.setEntry(keyName, entry, protection);

        /* Write keystore into file */
        try (FileOutputStream fos = new FileOutputStream(keyStoreFile.toFile())) {
            ks.store(fos, password.toCharArray());
        }
    }

    /**
     * Loads the AES secret key from the configured keystore file.
     * <p>
     * The keystore and the stored key entry are unlocked using the configured
     * password.
     *
     * @return the stored AES secret key
     * @throws IOException                 if an I/O error occurs while reading the keystore file
     * @throws KeyStoreException           if the PKCS12 keystore type is not available
     * @throws UnrecoverableEntryException if the key entry cannot be recovered with the given password
     * @throws NoSuchAlgorithmException    if the AES algorithm is not available
     * @throws CertificateException        if any certificate in the keystore cannot be loaded
     */
    public SecretKey loadKey() throws IOException, KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException, CertificateException {
        KeyStore ks = KeyStore.getInstance("PKCS12");
        try (FileInputStream fis = new FileInputStream(filePath)) {
            ks.load(fis, password.toCharArray());
        }

        KeyStore.ProtectionParameter prot = new KeyStore.PasswordProtection(password.toCharArray());
        KeyStore.SecretKeyEntry entry = (KeyStore.SecretKeyEntry) ks.getEntry(keyName, prot);

        return entry.getSecretKey();
    }
}