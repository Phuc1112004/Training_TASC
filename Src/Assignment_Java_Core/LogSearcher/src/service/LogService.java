package service;

import entity.LogEntry;
import repository.LogRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class LogService {
    private final LogRepository repository;
    private final ExecutorService executor = Executors.newFixedThreadPool(4); // tạo 4 thread

    public LogService(LogRepository repository) {
        this.repository = repository;
    }

    public List<LogEntry> searchByLevel(String level) {
        return repository.getLogs().stream()
                .filter(log -> log.getLevel().equalsIgnoreCase(level.trim()))
                .toList();
    }



    public List<LogEntry> searchByTimeRange(LocalDateTime start, LocalDateTime end) {
        return repository.getLogs().stream()
                .filter(log -> log.getTimestamp() != null &&
                        (log.getTimestamp().isAfter(start) || log.getTimestamp().isEqual(start)) &&
                        (log.getTimestamp().isBefore(end) || log.getTimestamp().isEqual(end)))
                .toList();
    }


    public List<LogEntry> searchByMessage(String keyword) {
        return repository.getLogs().stream()
                .filter(log -> log.getMessage() != null && log.getMessage().contains(keyword))
                .toList();
    }



//  tìm kiếm theo nhiều tiêu chí chạy đa luồng và mỗi luồng 1 tiêu chí tìm kiếm
    public Future<List<LogEntry>> searchByMultipleCriteriaAsync(String level, LocalDateTime start, LocalDateTime end, String keyword) {
        return executor.submit(() -> {
            System.out.println("Main task đang chạy trên thread: " + Thread.currentThread().getName());
            List<Future<List<LogEntry>>> futures = new ArrayList<>();

            if (level != null) {
                futures.add(executor.submit(() -> {
                    String t = Thread.currentThread().getName();
                    System.out.println("Task lọc level đang chạy trên thread: " + t);
                    return repository.getLogs().stream()
                            .filter(log -> log.getLevel().equalsIgnoreCase(level))
                            .toList();
                }));
            }

            if (start != null || end != null) {
                futures.add(executor.submit(() -> {
                    String t = Thread.currentThread().getName();
                    System.out.println("Task lọc thời gian đang chạy trên thread: " + t);
                    return repository.getLogs().stream()
                            .filter(log -> log.getTimestamp() != null)
                            .filter(log -> start == null || !log.getTimestamp().isBefore(start))
                            .filter(log -> end == null || !log.getTimestamp().isAfter(end))
                            .toList();
                }));
            }

            if (keyword != null) {
                futures.add(executor.submit(() -> {
                    String t = Thread.currentThread().getName();
                    System.out.println("Task lọc keyword đang chạy trên thread: " + t);
                    return repository.getLogs().stream()
                            .filter(log -> log.getMessage() != null && log.getMessage().toLowerCase().contains(keyword.toLowerCase()))
                            .toList();
                }));
            }

            // --- Chờ tất cả task con hoàn thành ---
            List<List<LogEntry>> results = new ArrayList<>();
            for (Future<List<LogEntry>> f : futures) {
                results.add(f.get());
            }

            // --- Gộp kết quả (giao nhau) ---
            List<LogEntry> finalResult = results.get(0);
            for (int i = 1; i < results.size(); i++) {
                List<LogEntry> r = results.get(i);
                finalResult = finalResult.stream().filter(r::contains).toList();
            }

            System.out.println("Main task kết thúc trên thread: " + Thread.currentThread().getName());
            return finalResult;
        });
    }

    public void shutdown(){
        executor.shutdown();
    }

    public void exportResultToFile(List<LogEntry> logs, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (LogEntry log : logs) {
                writer.write(log.toString() + "\n");
            }
            System.out.println("Đã xuất file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

