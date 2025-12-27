package securelogx;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static final Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("[WARNING] Could not load config.properties. Using defaults.");
        }
    }

    public static int getFailedAttemptThreshold() {
        return Integer.parseInt(
                properties.getProperty("failed.attempt.threshold", "3")
        );
    }

    public static int getTimeWindowMinutes() {
        return Integer.parseInt(
                properties.getProperty("time.window.minutes", "5")
        );
    }
}
