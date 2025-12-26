package securelogx;

import java.util.List;

public class Main {
    public static void main(String[] args){

        String logFilePath = "auth.log";

        LogReader reader = new LogReader();
        LogAnalyzer analyzer = new LogAnalyzer();
        ReportGenerator generator = new ReportGenerator();

        List<String> logs = reader.readLogs(logFilePath);
        List<Incident> incidents = analyzer.analyze(logs);

        generator.generate(incidents);

        Vault vault = new Vault();

        vault.encryptFile("incident_report.json", "incident_report.enc", "mypassword");
        vault.decryptFile("incident_report.enc", "incident_report_decrypted.json", "mypassword");


    }
}
