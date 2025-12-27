package securelogx;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportGenerator {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    public String generate(List<Incident> incidents) {

        String timestamp = LocalDateTime.now().format(FORMATTER);
        String reportPath = "reports/incident_report_" + timestamp + ".json";

        try (FileWriter writer = new FileWriter(reportPath)) {

            writer.write("{\n  \"incidents\": [\n");

            for (int i = 0; i < incidents.size(); i++) {
                Incident incident = incidents.get(i);

                writer.write("    {\n");
                writer.write("      \"ip\": \"" + incident.getIpAddress() + "\",\n");
                writer.write("      \"failedAttempts\": " + incident.getFailedAttempts() + ",\n");
                writer.write("      \"severity\": \"" + incident.getSeverity() + "\"\n");
                writer.write("    }");

                if (i < incidents.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }

            writer.write("  ]\n}");
            System.out.println("JSON report generated: " + reportPath);

        } catch (IOException e) {
            System.out.println("Failed to write report.");
            e.printStackTrace();
        }

        return reportPath; // IMPORTANT
    }
}
