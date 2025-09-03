import java.util.concurrent.*;

public class FutureDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Tạo 1 Callable giả lập tác vụ mất thời gian
        Callable<String> task = () -> {
            try {
                System.out.println("Task đang chạy...");
                Thread.sleep(5000); // giả lập chạy lâu
                return "Kết quả của task";
            } catch (InterruptedException e) {
                System.out.println("Task bị hủy trong khi chạy!");
                throw e;
            }
        };

        Future<String> future = executor.submit(task);

        try {
            // --- 1. Kiểm tra trạng thái ban đầu ---
            System.out.println("isDone? " + future.isDone());
            System.out.println("isCancelled? " + future.isCancelled());

            // --- 2. Thử hủy task sau 2 giây ---
            Thread.sleep(2000);
            boolean cancelled = future.cancel(true); // true = cho phép interrupt
            System.out.println("Đã gọi cancel: " + cancelled);

            // --- 3. Kiểm tra trạng thái sau khi cancel ---
            System.out.println("isCancelled? " + future.isCancelled());
            System.out.println("isDone? " + future.isDone());

            // --- 4. Thử gọi get() ---
            // Nếu đã cancel thì gọi get() sẽ ném CancellationException
            try {
                String result = future.get();
                System.out.println("Kết quả: " + result);
            } catch (CancellationException e) {
                System.out.println("Task đã bị hủy -> không có kết quả.");
            }

            // --- 5. Chạy lại 1 task khác để demo get(timeout) ---
            Future<String> future2 = executor.submit(() -> {
                Thread.sleep(4000);
                return "Kết quả sau 4 giây";
            });

            try {
                // Chỉ chờ tối đa 2 giây
                String result2 = future2.get(2, TimeUnit.SECONDS);
                System.out.println("Kết quả future2: " + result2);
            } catch (TimeoutException e) {
                System.out.println("TimeoutException: Task chưa xong trong 2 giây.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}

