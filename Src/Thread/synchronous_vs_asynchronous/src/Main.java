package Src.Thread.synchronous_vs_asynchronous.src;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        synchronous();
        asynchronous();
    }

    static void synchronous() {
        System.out.println("1");
        System.out.println("2");
    }

    static String runThread() {
        byte[] data = new byte[100];
        String path = "src/main/java/org/example/logo.png";
        Thread thread = new Thread(() -> {
            try (FileInputStream fis = new FileInputStream(path)) {
                fis.read(data, 0, 100);

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Đã đọc xong");
        });
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    static synchronized String runThread2() {
        byte[] data = new byte[100];
        String path = "src/main/java/org/example/logo.png";
        Thread thread = new Thread(() -> {
            try (FileInputStream fis = new FileInputStream(path)) {
                fis.read(data, 0, 100);

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Đã đọc xong");
        });
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    static void asynchronous() throws ExecutionException, InterruptedException {
        System.out.println("Trước khi đọc file");
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> runThread());
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> runThread());
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(completableFuture1, completableFuture2);
        combinedFuture.join();
        System.out.println(completableFuture1.get());
        System.out.println(completableFuture2.get());

        System.out.println("Chạy phương thức có synchronized");
        CompletableFuture<String> completableFuture3 = CompletableFuture.supplyAsync(() -> runThread2());
        CompletableFuture<String> completableFuture4 = CompletableFuture.supplyAsync(() -> runThread2());
        CompletableFuture<Void> combinedFuture2 = CompletableFuture.allOf(completableFuture3, completableFuture4);
        combinedFuture2.join();
        System.out.println(completableFuture1.get());
        System.out.println(completableFuture2.get());

    }
}