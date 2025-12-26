package securelogx;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportGenerator {

    public void generate(List<Incident> incidents) {

        System.out.println("===== SECURITY INCIDENT REPORT =====");

        if (incidents.isEmpty()) {
            System.out.println("No suspicious activity detected.");
        } else {
            for (Incident incident : incidents) {
                System.out.println(
                        "Suspicious IP: " + incident.getIpAddress()
                                + " | Failed Attempts: " + incident.getFailedAttempts()
                                + " | Severity: " + incident.getSeverity()
                );
            }
        }

        generateJsonReport(incidents);
    }

    private void generateJsonReport(List<Incident> incidents) {

        try (FileWriter writer = new FileWriter("incident_report.json")) {

            writer.write("{\n");
            writer.write("  \"incidents\": [\n");

            for (int i = 0; i < incidents.size(); i++) {
                Incident incident = incidents.get(i);

                writer.write("    {\n");
                writer.write("      \"ipAddress\": \"" + incident.getIpAddress() + "\",\n");
                writer.write("      \"failedAttempts\": " + incident.getFailedAttempts() + ",\n");
                writer.write("      \"severity\": \"" + incident.getSeverity() + "\"\n");
                writer.write("    }");

                if (i < incidents.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }

            writer.write("  ]\n");
            writer.write("}\n");

            System.out.println("JSON report generated: incident_report.json");

        } catch (IOException e) {
            System.out.println("Failed to generate JSON report.");
        }
    }
}
