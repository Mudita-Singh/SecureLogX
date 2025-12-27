package securelogx;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Vault vault = new Vault();

        System.out.println("=== SecureLogX Menu ===");
        System.out.println("1. Analyze logs & encrypt report");
        System.out.println("2. Decrypt existing report");
        System.out.print("Enter choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (choice == 1) {

            String logFilePath = "auth.log";

            LogReader reader = new LogReader();
            LogAnalyzer analyzer = new LogAnalyzer();
            ReportGenerator generator = new ReportGenerator();

            System.out.println("[INFO] Analyzing logs...");

            List<String> logs = reader.readLogs(logFilePath);
            List<Incident> incidents = analyzer.analyze(logs);

            if (incidents.isEmpty()) {
                System.out.println("[INFO] No suspicious activity detected.");
            } else {
                System.out.println("===== SECURITY INCIDENT REPORT =====");
                for (Incident incident : incidents) {
                    System.out.println(
                            "Suspicious IP: " + incident.getIpAddress() +
                                    " | Failed Attempts: " + incident.getFailedAttempts() +
                                    " | Severity: " + incident.getSeverity()
                    );
                }
            }

            // Generate timestamped JSON report
            String reportPath = generator.generate(incidents);

            System.out.print("Enter password to encrypt report: ");
            String password = scanner.nextLine();

            // Encrypt using same timestamped name
            String encryptedPath = reportPath.replace(".json", ".enc");

            vault.encryptFile(reportPath, encryptedPath, password);

        } else if (choice == 2) {

            System.out.print("Enter encrypted report path: ");
            String encryptedPath = scanner.nextLine();

            System.out.print("Enter password to decrypt report: ");
            String password = scanner.nextLine();

            String decryptedPath = encryptedPath.replace(".enc", "_decrypted.json");

            vault.decryptFile(encryptedPath, decryptedPath, password);

        } else {
            System.out.println("Invalid choice.");
        }

        scanner.close();
    }
}
