import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3); // 3 nhân viên

        // 4 khách rút tiền
        Future<String> f1 = executor.submit(() -> simulateWithdraw("Khách 1"));
        Future<String> f2 = executor.submit(() -> simulateWithdraw("Khách 2"));
        Future<String> f3 = executor.submit(() -> simulateWithdraw("Khách 3"));
        Future<String> f4 = executor.submit(() -> simulateWithdraw("Khách 4"));

        // lấy kết quả khi khách xong
        System.out.println(f1.get());
        System.out.println(f2.get());
        System.out.println(f3.get());
        System.out.println(f4.get());

        executor.shutdown();
    }

    static String simulateWithdraw(String name) throws InterruptedException {
        Thread.sleep((long)(Math.random() * 3000)); // mô phỏng thời gian rút tiền khác nhau
        return name + " đã rút tiền xong ở " + Thread.currentThread().getName();
    }
}
