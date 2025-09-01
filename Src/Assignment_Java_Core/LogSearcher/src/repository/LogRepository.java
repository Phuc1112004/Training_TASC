package repository;

import entity.LogEntry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LogRepository {
    private final List<LogEntry> logs = new ArrayList<>();

    // chấp nhận cả "2025-01-04T21:51:17" lẫn "2025-01-04 21:51:17"
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd[ HH:mm:ss][\'T\'HH:mm:ss]");

    // Regex: [timestamp] [level] [service]- message
    private static final Pattern LOG_PATTERN = Pattern.compile("^\\[(.*?)\\]\\s*\\[(.*?)\\]\\s*\\[(.*?)\\]-\\s*(.*)$");

    public void loadLogs(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                LogEntry log = parseLog(line);
                if (log != null) {
                    logs.add(log);
                }
            }
            System.out.println("Đọc file log thành công, tổng số log: " + logs.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private LogEntry parseLog(String line) {
        try {
            Matcher matcher = LOG_PATTERN.matcher(line);
            if (!matcher.find()) {
                System.err.println("Không khớp regex: " + line);
                return null;
            }

            String timestampStr = matcher.group(1).trim();
            String level = matcher.group(2).trim();
            String service = matcher.group(3).trim();
            String message = matcher.group(4).trim();

            // debug in ra level để kiểm tra
//            System.out.println("DEBUG level = [" + level + "]");

            LocalDateTime timestamp = LocalDateTime.parse(timestampStr, FORMATTER);

            return new LogEntry(timestamp, level, service, message);
        } catch (Exception e) {
            System.err.println("Không parse được log: " + line);
            return null;
        }
    }

    public List<LogEntry> getLogs() {
        return logs;
    }
}
