import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Thread1 implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println("Thread1 đang chạy");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class Thread2 extends Thread {
    @Override
    public void run() {
        try {
            System.out.println("Thread2 đang chạy");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new Thread1());
        thread1.start();
        while (thread1.isAlive()){
            System.out.println("Thread1 vẫn đang chạy");
        }
        Thread thread2 = new Thread2();
        thread2.start();
        thread2.join();
        System.out.println("Thread2 đã chạy xong");

        new Thread(()-> {
            try {
                System.out.println("Thread3 đang chạy");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("Thực hiện tác vụ: " + taskId + " bởi " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }
}