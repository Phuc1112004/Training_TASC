package Src.JavaCore;

public class StaticAndFinalDemo {
    // ----- STATIC -----
    static int count; // biến static dùng chung cho cả class

    static {
        count = 10; // static block chạy 1 lần khi load class
        System.out.println("Khởi tạo count = " + count);
    }

    static class Vehicle { // inner class static
        String name;
        int numWheel;

        static int totalVehicle = 0; // biến static cho cả Vehicle

        Vehicle(String name, int numWheel) {
            this.name = name;
            this.numWheel = numWheel;
            totalVehicle++; // mỗi lần tạo object thì tăng lên
        }
    }

    // phương thức static
    private static void down() {
        count -= 1;
    }

    // phương thức non-static
    private void up() {
        count += 1;
        down(); // non-static vẫn gọi được static
    }

    // ----- FINAL -----
    final int MAX_SPEED = 120; // biến final -> không thể thay đổi

    final void showFinalMethod() {
        System.out.println("Đây là phương thức final, không thể override.");
    }

    final class Car { // class final -> không thể kế thừa
        String brand = "Toyota";
    }

    public static void main(String[] args) {
        System.out.println("Start main");

        // Dùng static
        down();
        System.out.println("count: " + count);
        System.out.println("count trong Main class: " + StaticAndFinalDemo.count);

        // Tạo đối tượng Vehicle
        Vehicle v1 = new Vehicle("Xe máy", 2);
        Vehicle v2 = new Vehicle("Ô tô", 4);
        System.out.println("Tổng số Vehicle: " + Vehicle.totalVehicle);

        // Dùng final
        StaticAndFinalDemo mainObj = new StaticAndFinalDemo();
        System.out.println("MAX_SPEED: " + mainObj.MAX_SPEED);

        Car car = mainObj.new Car();
        System.out.println("Car brand: " + car.brand);

        // Thử gọi method final
        mainObj.showFinalMethod();
    }
}