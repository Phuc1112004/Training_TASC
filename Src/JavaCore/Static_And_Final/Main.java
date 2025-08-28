package Src.JavaCore.Static_And_Final;

public class Main {
    static class Vehicle {
        String name;
        int numWheel;
    }

    static {
        count = 10;
        System.out.println("Khởi tạo count = 10");
    }

    static int count;

    public static void main(String[] args) {
        System.out.println("start main");
//        up(); // lỗi vì phương thức up không phải là static
        down();
        System.out.println("count: " + count);
        System.out.println("count in Main class: " + Main.count);
    }

    private void up() {
        count += 1;
        down();
    }

    private static void down() {
        count -= 1;
    }
}

