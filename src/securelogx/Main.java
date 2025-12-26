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
        scanner.nextLine(); // consume leftover newline

        if (choice == 1) {

            String logFilePath = "auth.log";

            LogReader reader = new LogReader();
            LogAnalyzer analyzer = new LogAnalyzer();
            ReportGenerator generator = new ReportGenerator();

            List<String> logs = reader.readLogs(logFilePath);
            List<Incident> incidents = analyzer.analyze(logs);

            generator.generate(incidents);

            System.out.print("Enter password to encrypt report: ");
            String password = scanner.nextLine();

            vault.encryptFile(
                    "incident_report.json",
                    "incident_report.enc",
                    password
            );

            System.out.println("Report encrypted successfully.");

        } else if (choice == 2) {

            System.out.print("Enter password to decrypt report: ");
            String password = scanner.nextLine();

            vault.decryptFile(
                    "incident_report.enc",
                    "incident_report_decrypted.json",
                    password
            );

            System.out.println("Report decrypted successfully.");

        } else {
            System.out.println("Invalid choice.");
        }

        scanner.close();
    }
}
