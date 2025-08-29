import java.util.concurrent.*;

class Task4 implements Callable<String> {
    private String name;

    public Task4(String name) {
        this.name = name;
    }

    @Override
    public String call() throws Exception {
        System.out.println(name + " bắt đầu...");
        Thread.sleep(2000); // giả lập công việc tốn 2 giây
        return name + " kết thúc!";
    }
}

public class ThreadDemo4 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Tạo Thread Pool chỉ có 2 thread
        ExecutorService executor = Executors.newFixedThreadPool(2);

        long start = System.currentTimeMillis();

        // Nộp 5 task vào pool
        Future<String> f1 = executor.submit(new Task4("Task-1"));
        Future<String> f2 = executor.submit(new Task4("Task-2"));
        Future<String> f3 = executor.submit(new Task4("Task-3"));
        Future<String> f4 = executor.submit(new Task4("Task-4"));
        Future<String> f5 = executor.submit(new Task4("Task-5"));

        // Lấy kết quả (get sẽ chờ task hoàn thành)
        System.out.println(f1.get());
        System.out.println(f2.get());
        System.out.println(f3.get());
        System.out.println(f4.get());
        System.out.println(f5.get());

        long end = System.currentTimeMillis();
        System.out.println("Tổng thời gian: " + (end - start) + "ms");

        executor.shutdown();
    }
}

