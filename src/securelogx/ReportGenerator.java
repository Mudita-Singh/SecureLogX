package securelogx;

import java.util.List;

public class ReportGenerator {

    public void generate(List<Incident> incidents) {

        System.out.println("===== SECURITY INCIDENT REPORT =====");

        if (incidents.isEmpty()) {
            System.out.println("No suspicious activity detected.");
            return;
        }

        for (Incident incident : incidents) {
            System.out.println(
                    "Suspicious IP: " + incident.getIpAddress() +
                            " | Failed Attempts: " + incident.getFailedAttempts()
            );
        }
    }
}
