import java.util.concurrent.*;

class Task3 implements Callable<String> {
    private String name;

    public Task3(String name) {
        this.name = name;
    }

    @Override
    public String call() throws Exception {
        System.out.println(name + " bắt đầu...");
        Thread.sleep(2000); // giả lập công việc tốn 2 giây
        return name + " kết thúc!";
    }
}

public class ThreadDemo3 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Tạo Thread Pool với 3 thread
        ExecutorService executor = Executors.newFixedThreadPool(3);

        long start = System.currentTimeMillis();

        // Gửi 3 task vào pool
        Future<String> f1 = executor.submit(new Task3("Task-1"));
        Future<String> f2 = executor.submit(new Task3("Task-2"));
        Future<String> f3 = executor.submit(new Task3("Task-3"));

        // Lấy kết quả khi task xong
        System.out.println(f1.get());
        System.out.println(f2.get());
        System.out.println(f3.get());

        long end = System.currentTimeMillis();
        System.out.println("Tổng thời gian: " + (end - start) + "ms");

        // Tắt executor
        executor.shutdown();
    }
}

