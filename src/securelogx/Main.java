package securelogx;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        SecureLogXService service = new SecureLogXService();

        System.out.println("=== SecureLogX Menu ===");
        System.out.println("1. Analyze logs & encrypt report");
        System.out.println("2. Decrypt existing report");
        System.out.print("Enter choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (choice == 1) {

            System.out.println("[INFO] Analyzing logs...");

            System.out.print("Enter password to encrypt report: ");
            String password = scanner.nextLine();

            String encryptedPath = service.analyzeAndEncrypt("auth.log", password);

            System.out.println("[SECURE] Encrypted report generated at:");
            System.out.println(encryptedPath);

        } else if (choice == 2) {

            System.out.print("Enter encrypted report path: ");
            String encryptedPath = scanner.nextLine();

            System.out.print("Enter password to decrypt report: ");
            String password = scanner.nextLine();

            String decryptedPath = service.decryptReport(encryptedPath, password);

            System.out.println("[INFO] Decrypted report available at:");
            System.out.println(decryptedPath);

        } else {
            System.out.println("Invalid choice.");
        }

        scanner.close();
    }
}
