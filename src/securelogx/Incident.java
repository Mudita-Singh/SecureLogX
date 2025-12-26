package securelogx;

public class Incident {

    private String ipAddress;
    private int failedAttempts;

    public Incident(String ipAddress, int failedAttempts) {
        this.ipAddress = ipAddress;
        this.failedAttempts = failedAttempts;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }
}
