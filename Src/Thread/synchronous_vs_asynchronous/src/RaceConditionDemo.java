class Counter {
    private int count = 0;

    // Không có synchronized
    public void increment() {
        count++;
    }

    // Có synchronized
    public synchronized void safeIncrement() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

public class RaceConditionDemo {
    public static void main(String[] args) throws InterruptedException {
        Counter counter1 = new Counter();
        Counter counter2 = new Counter();

        // Hai thread cùng tăng counter1 (không synchronized)
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                counter1.increment();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                counter1.increment();
            }
        });

        // Hai thread cùng tăng counter2 (có synchronized)
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                counter2.safeIncrement();
            }
        });
        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                counter2.safeIncrement();
            }
        });

        // Chạy và chờ thread hoàn thành
        t1.start(); t2.start();
        t1.join();  t2.join();

        t3.start(); t4.start();
        t3.join();  t4.join();

        // Kết quả
        System.out.println("Không synchronized (mong đợi = 200000): " + counter1.getCount());
        System.out.println("Có synchronized (mong đợi = 200000): " + counter2.getCount());
    }
}
