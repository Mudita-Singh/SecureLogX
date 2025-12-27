package securelogx;

import java.util.List;

public class SecureLogXService {

    private final LogReader logReader = new LogReader();
    private final LogAnalyzer logAnalyzer = new LogAnalyzer();
    private final ReportGenerator reportGenerator = new ReportGenerator();
    private final Vault vault = new Vault();

    /**
     * Analyze logs and generate + encrypt report
     */
    public String analyzeAndEncrypt(String logFilePath, String password) {

        List<String> logs = logReader.readLogs(logFilePath);
        List<Incident> incidents = logAnalyzer.analyze(logs);

        String reportPath = reportGenerator.generate(incidents);
        String encryptedPath = reportPath.replace(".json", ".enc");

        vault.encryptFile(reportPath, encryptedPath, password);

        return encryptedPath;
    }

    /**
     * Decrypt an encrypted report
     */
    public String decryptReport(String encryptedPath, String password) {

        String decryptedPath = encryptedPath.replace(".enc", "_decrypted.json");
        vault.decryptFile(encryptedPath, decryptedPath, password);

        return decryptedPath;
    }
}
