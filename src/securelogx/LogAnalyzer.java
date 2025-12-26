package securelogx;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogAnalyzer {

    private static final int TIME_WINDOW_MINUTES = 5;
    private static final int ATTEMPT_THRESHOLD = 3;

    // Optional year at start
    private static final Pattern LOG_PATTERN =
            Pattern.compile("(?:(\\d{4})\\s+)?(\\w+\\s+\\d+\\s+\\d+:\\d+:\\d+).*from ([0-9.]+)");

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("MMM d HH:mm:ss");

    public List<Incident> analyze(List<String> logs) {

        Map<String, List<LocalDateTime>> ipAttempts = new HashMap<>();
        List<Incident> incidents = new ArrayList<>();

        for (String log : logs) {
            if (!log.contains("Failed password")) continue;

            Matcher matcher = LOG_PATTERN.matcher(log);
            if (!matcher.find()) continue;

            String yearPart = matcher.group(1);     // may be null
            String timestampPart = matcher.group(2);
            String ip = matcher.group(3);

            LocalDateTime time = parseTime(timestampPart, yearPart);

            ipAttempts.computeIfAbsent(ip, k -> new ArrayList<>()).add(time);
        }

        for (Map.Entry<String, List<LocalDateTime>> entry : ipAttempts.entrySet()) {
            String ip = entry.getKey();
            List<LocalDateTime> times = entry.getValue();

            times.sort(LocalDateTime::compareTo);

            int count = countWithinWindow(times);

            if (count >= ATTEMPT_THRESHOLD) {
                incidents.add(new Incident(ip, count));
            }
        }

        return incidents;
    }

    private int countWithinWindow(List<LocalDateTime> times) {
        int maxCount = 0;

        for (int i = 0; i < times.size(); i++) {
            LocalDateTime start = times.get(i);
            int count = 1;

            for (int j = i + 1; j < times.size(); j++) {
                if (times.get(j).isBefore(start.plusMinutes(TIME_WINDOW_MINUTES))) {
                    count++;
                } else {
                    break;
                }
            }

            maxCount = Math.max(maxCount, count);
        }

        return maxCount;
    }

    // ✅ YEAR-AWARE parsing
    private LocalDateTime parseTime(String timestamp, String yearPart) {

        TemporalAccessor accessor = FORMATTER.parse(timestamp);

        int year = (yearPart != null)
                ? Integer.parseInt(yearPart)
                : LocalDateTime.now().getYear();

        return LocalDateTime.of(
                year,
                accessor.get(ChronoField.MONTH_OF_YEAR),
                accessor.get(ChronoField.DAY_OF_MONTH),
                accessor.get(ChronoField.HOUR_OF_DAY),
                accessor.get(ChronoField.MINUTE_OF_HOUR),
                accessor.get(ChronoField.SECOND_OF_MINUTE)
        );
    }
}
