class Task implements Runnable {
    private String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name + " bắt đầu...");
        try {
            // giả lập công việc tốn 2 giây
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + " kết thúc!");
    }
}

public class ThreadDemo2 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("===== Single-thread (chạy tuần tự) =====");
        long start1 = System.currentTimeMillis();

        // chạy lần lượt từng task
        new Task("Task-1").run();
        new Task("Task-2").run();
        new Task("Task-3").run();

        long end1 = System.currentTimeMillis();
        System.out.println("Thời gian single-thread: " + (end1 - start1) + "ms");

        System.out.println("\n===== Multi-thread (chạy song song) =====");
        long start2 = System.currentTimeMillis();

        Thread t1 = new Thread(new Task("Task-1"));
        Thread t2 = new Thread(new Task("Task-2"));
        Thread t3 = new Thread(new Task("Task-3"));

        t1.start();
        t2.start();
        t3.start();

        // đợi tất cả thread hoàn thành
        t1.join();
        t2.join();
        t3.join();

        long end2 = System.currentTimeMillis();
        System.out.println("Thời gian multi-thread: " + (end2 - start2) + "ms");
    }
}
