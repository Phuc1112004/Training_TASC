import java.util.concurrent.*;

public class FutureUseCase {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3); // 3 thread

        // 3 nhiệm vụ (task) chạy song song
        Future<Long> future1 = executor.submit(() -> sum(1, 1000000));
        Future<Long> future2 = executor.submit(() -> sum(1000001, 2000000));
        Future<Long> future3 = executor.submit(() -> sum(2000001, 3000000));

        System.out.println("Đang tính toán...");


        System.out.println("Future1: " + future1.get());
        System.out.println("Future2: " + future2.get());
        System.out.println("Future3: " + future3.get());

        // Lấy kết quả từ các Future
        long result = future1.get() + future2.get() + future3.get();
        System.out.println("Tổng = " + result);

        executor.shutdown();
    }

    // Hàm tính tổng từ start đến end
    static long sum(int start, int end) {
        int s = 0;
        for (int i = start; i <= end; i++) {
            s += i;
        }
        return s;
    }
}

