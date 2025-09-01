package Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class GenerateSampleLog {
    private static final DateTimeFormatter TIMETAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String[] LEVELS = {"ERROR", "WARN", "INFO", "DEBUG", "TRACE"};
    private static final String[] SERVICES = {"UserService", "PaymentService", "AuthService", "OrderService", "NotificationService"};
    private static final String[] MESSAGES = {
            // INFO
            "User login successful",
            "Order processed successfully",
            "Notification sent to user",
            "User profile updated",
            "Password changed successfully",
            "Item added to cart",
            "Checkout completed",
            "Email verification sent",

            // WARN
            "Authentication timeout",
            "User session expired",
            "Too many login attempts",
            "Payment retry due to timeout",
            "Slow database query detected",
            "Disk space running low",
            "High memory usage warning",

            // ERROR
            "Payment failed due to insufficient funds",
            "Database connection error",
            "Service unavailable",
            "Payment gateway timeout",
            "Invalid user input",
            "Null pointer exception encountered",
            "Unable to reach external API",
            "Order creation failed",
            "Message queue overflow",
            "File upload failed",

            // DEBUG
            "Entering authentication method",
            "User object successfully mapped",
            "Request payload validated",
            "Cache miss for user session",
            "Order status updated in memory",
            "Executing SQL query",
            "Response sent to client",

            // TRACE
            "Begin transaction",
            "End transaction",
            "Lock acquired on resource",
            "Lock released on resource",
            "Request received from client",
            "Response headers set",
            "Thread started",
            "Thread finished"
    };


    public static void main(String[] args) {
        String outputFilePath = "file_log.txt";
        Random random = new Random();
        LocalDateTime startDate = LocalDateTime.of(2024,11, 1, 0,0,0 );
        long secondsRange = Duration.between(startDate, LocalDateTime.of(2025,11,1,0,0,0)).getSeconds();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (int i = 0; i < 500000; i++) {
                long randomSeconds = random.nextLong(secondsRange);
                LocalDateTime randomTime = startDate.plusSeconds(randomSeconds);
                String timestamp = randomTime.format(TIMETAMP_FORMAT);

                String level = LEVELS[random.nextInt(LEVELS.length)];
                String services = SERVICES[random.nextInt(SERVICES.length)];
                String message = MESSAGES[random.nextInt(MESSAGES.length)];

                String logLine = String.format("[%s] [%s] [%s]- %s", timestamp, level, services, message);
                writer.write(logLine);
                writer.newLine();

                if(i % 100000 == 0) {
                    System.out.println("Đã tạo " + i + " dòng...");
                }
            }
            System.out.println("Hoàn tất! File log đã được tạo tại " + outputFilePath );
        } catch (IOException e) {
            System.err.println("Lỗi khi tạo file log: " + e.getMessage());
        }
    }
}
