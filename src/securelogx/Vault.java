package securelogx;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Vault {

    private static final String ALGORITHM = "AES";

    private SecretKey generateKey(String password) throws Exception {
        byte[] salt = "securelogx".getBytes(); // fixed salt (simple for learning)
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

            writeFile(outputFile, Base64.getEncoder().encode(encryptedData));
            System.out.println("Report encrypted successfully.");

        } catch (Exception e) {
            System.out.println("Encryption failed:");
            e.printStackTrace();
        }
    }

    public void decryptFile(String inputFile, String outputFile, String password) {
        try {
            SecretKey key = generateKey(password);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] encryptedData = Base64.getDecoder().decode(readFile(inputFile));
            byte[] decryptedData = cipher.doFinal(encryptedData);

            writeFile(outputFile, decryptedData);
            System.out.println("Report decrypted successfully.");

        } catch (Exception e) {
            System.out.println("Decryption failed:");
            e.printStackTrace();   // 👈 IMPORTANT CHANGE
        }
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
