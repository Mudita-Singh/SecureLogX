package securelogx;

public class Incident {

    private String ipAddress;
    private int failedAttempts;
    private String severity;

    public Incident(String ipAddress, int failedAttempts) {
        this.ipAddress = ipAddress;
        this.failedAttempts = failedAttempts;
        this.severity = calculateSeverity(failedAttempts);
    }

    private String calculateSeverity(int attempts) {
        if (attempts >= 8) {
            return "HIGH";
        } else if (attempts >= 5) {
            return "MEDIUM";
        } else {
            return "LOW";
        }
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public String getSeverity() {
        return severity;
    }
}
