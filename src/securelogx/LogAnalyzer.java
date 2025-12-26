package securelogx;

import java.util.*;
import java.util.regex.*;

public class LogAnalyzer {

    private static final int THRESHOLD = 3;

    public List<Incident> analyze(List<String> logs) {

        Map<String, Integer> ipFailures = new HashMap<>();
        List<Incident> incidents = new ArrayList<>();

        Pattern pattern = Pattern.compile(
                "Failed password.*from (\\d+\\.\\d+\\.\\d+\\.\\d+)"
        );

        for (String log : logs) {
            Matcher matcher = pattern.matcher(log);
            if (matcher.find()) {
                String ip = matcher.group(1);
                ipFailures.put(ip, ipFailures.getOrDefault(ip, 0) + 1);
            }
        }

        for (String ip : ipFailures.keySet()) {
            int count = ipFailures.get(ip);
            if (count >= THRESHOLD) {
                incidents.add(new Incident(ip, count));
            }
        }

        return incidents;
    }
}
