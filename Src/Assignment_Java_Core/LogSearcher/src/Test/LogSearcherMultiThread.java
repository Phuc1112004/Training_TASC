package Test;

import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class LogSearcherMultiThread {
    private static final Pattern LOG_PATTERN = Pattern.compile("\\[(.*?)\\] \\[(.*?)\\] \\[(.*?)\\]- (.*)");
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final String POISON_PILL = "__END__"; // đánh dấu kết thúc queue

    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner scanner = new Scanner(System.in);

        // Input / Output
        System.out.print("Nhập đường dẫn file log input: ");
        String inputFilePath = scanner.nextLine().trim();
        System.out.print("Nhập đường dẫn file output (.txt): ");
        String outputFilePath = scanner.nextLine().trim();

        // Lọc theo level
        System.out.print("Nhập levels (comma-separated, ví dụ: INFO,WARN; để trống nếu all): ");
        String levelsInput = scanner.nextLine().trim();
        Set<String> targetLevels = new HashSet<>();
        if (!levelsInput.isEmpty()) {
            targetLevels.addAll(Arrays.asList(levelsInput.toUpperCase().split(",")));
        }

        // Lọc theo service
        System.out.print("Nhập services (comma-separated, ví dụ: UserService,PaymentService; để trống nếu all): ");
        String servicesInput = scanner.nextLine().trim();
        Set<String> targetServices = new HashSet<>();
        if (!servicesInput.isEmpty()) {
            targetServices.addAll(Arrays.asList(servicesInput.split(",")));
        }

        // Thời gian
        System.out.print("Nhập start time (yyyy-MM-dd HH:mm:ss; để trống nếu không lọc): ");
        LocalDateTime startTime = parseTimestamp(scanner.nextLine().trim());
        System.out.print("Nhập end time (yyyy-MM-dd HH:mm:ss; để trống nếu không lọc): ");
        LocalDateTime endTime = parseTimestamp(scanner.nextLine().trim());

        // Keyword
        System.out.print("Nhập keyword trong message (để trống nếu không lọc): ");
        String keyword = scanner.nextLine().trim().toLowerCase();


        // --- BẮT ĐẦU TÍNH THỜI GIAN ---
        long startMillis = System.currentTimeMillis();

        // Queue cho pipeline
        BlockingQueue<String> inputQueue = new ArrayBlockingQueue<>(10000);
        BlockingQueue<String> outputQueue = new ArrayBlockingQueue<>(10000);

        int numWorkers = Runtime.getRuntime().availableProcessors(); // số core CPU
        ExecutorService workers = Executors.newFixedThreadPool(numWorkers);

        // 1. Thread đọc file
        Thread reader = new Thread(() -> {
            try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    inputQueue.put(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    // gửi POISON_PILL để báo worker dừng
                    for (int i = 0; i < numWorkers; i++) {
                        inputQueue.put(POISON_PILL);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 2. Worker threads xử lý lọc
        for (int i = 0; i < numWorkers; i++) {
            workers.submit(() -> {
                try {
                    while (true) {
                        String line = inputQueue.take();
                        if (line.equals(POISON_PILL)) break;

                        Matcher matcher = LOG_PATTERN.matcher(line);
                        if (matcher.matches()) {
                            String timestampStr = matcher.group(1);
                            String level = matcher.group(2).toUpperCase();
                            String service = matcher.group(3).trim();
                            String message = matcher.group(4).toLowerCase();

                            LocalDateTime logTime = parseTimestamp(timestampStr);
                            if (logTime == null) continue;

                            // Lọc conditions
                            if (!targetLevels.isEmpty() && !targetLevels.contains(level)) continue;
                            if (!targetServices.isEmpty() && !targetServices.contains(service)) continue;
                            if (startTime != null && logTime.isBefore(startTime)) continue;
                            if (endTime != null && logTime.isAfter(endTime)) continue;
                            if (!keyword.isEmpty() && !message.contains(keyword)) continue;

                            outputQueue.put(line);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        // 3. Thread ghi file output
        Thread writer = new Thread(() -> {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {
                int count = 0;
                while (true) {
                    String line = outputQueue.poll(5, TimeUnit.SECONDS); // timeout tránh deadlock
                    if (line == null && workers.isTerminated()) break; // dừng khi worker xong
                    if (line != null) {
                        bw.write(line);
                        bw.newLine();
                        count++;
                    }
                }
                System.out.println("Đã ghi xong " + count + " dòng vào " + outputFilePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Start các thread
        reader.start();
        writer.start();
        reader.join(); // chờ reader xong
        workers.shutdown();
        workers.awaitTermination(1, TimeUnit.HOURS); // chờ workers xong
        writer.join(); // chờ writer xong


        // --- KẾT THÚC TÍNH THỜI GIAN ---
        long endMillis = System.currentTimeMillis();
        System.out.println("Hoàn tất! Thời gian tìm kiếm: " + (endMillis - startMillis) + " ms");
    }

    private static LocalDateTime parseTimestamp(String ts) {
        if (ts == null || ts.isEmpty()) return null;
        try {
            return LocalDateTime.parse(ts, TIMESTAMP_FORMAT);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}

