package securelogx;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Vault {

    private static final String ALGORITHM = "AES";

    private SecretKey generateKey(String password) throws Exception {
        byte[] salt = "securelogx".getBytes();
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), ALGORITHM);
    }

    public void encryptFile(String inputFile, String outputFile, String password) {
        try {
            SecretKey key = generateKey(password);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] fileData = readFile(inputFile);
            byte[] encryptedData = cipher.doFinal(fileData);

            byte[] encoded = Base64.getEncoder().encode(encryptedData);
            writeFile(outputFile, encoded);

            // generate hash after encryption
            generateHash(encoded, outputFile + ".hash");

            System.out.println("Report encrypted successfully.");
            System.out.println("Integrity hash generated.");

        } catch (Exception e) {
            System.out.println("Encryption failed:");
            e.printStackTrace();
        }
    }

    public void decryptFile(String inputFile, String outputFile, String password) {
        try {
            byte[] encryptedData = readFile(inputFile);
            byte[] storedHash = readFile(inputFile + ".hash");

            byte[] currentHash = calculateHash(encryptedData);

            if (!MessageDigest.isEqual(storedHash, currentHash)) {
                System.out.println("WARNING: File integrity check failed!");
                return;
            }

            SecretKey key = generateKey(password);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decoded = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedData = cipher.doFinal(decoded);

            writeFile(outputFile, decryptedData);
            System.out.println("Report decrypted successfully.");
            System.out.println("Integrity verified.");

        } catch (Exception e) {
            System.out.println("Decryption failed:");
            e.printStackTrace();
        }
    }

    private void generateHash(byte[] data, String hashFile) throws Exception {
        byte[] hash = calculateHash(data);
        writeFile(hashFile, hash);
    }

    private byte[] calculateHash(byte[] data) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(data);
    }

    private byte[] readFile(String path) throws IOException {
        return new FileInputStream(path).readAllBytes();
    }

    private void writeFile(String path, byte[] data) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(data);
        }
    }
}
