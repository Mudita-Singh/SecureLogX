package securelogx;

public class Incident {

    private String ipAddress;
    private int failedAttempts;
    private String severity;

    public Incident(String ipAddress, int failedAttempts) {
        this.ipAddress = ipAddress;
        this.failedAttempts = failedAttempts;

        // simple severity logic
        if (failedAttempts >= 5) {
            this.severity = "HIGH";
        } else {
            this.severity = "MEDIUM";
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
