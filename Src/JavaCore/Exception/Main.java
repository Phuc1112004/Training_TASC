package Src.JavaCore.Exception;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        demoThrow();
        try {
            demoThrows();
        }catch (ExceptionCustom2 e){
            System.out.println("Bắt lỗi throws");
        }finally {
            System.out.println("Kết thúc");
        }

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Nhập một số nguyên: ");
            int number = scanner.nextInt(); // Đọc một số nguyên từ người dùng

            System.out.println("Bạn đã nhập: " + number);
        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
    }

    public static void demoThrow(){
        // java không bắt buộc phải bắt exception này nhưng khi chạy thì lỗi
        int a  = 5 / 0;

        // unchecked
        throw new ExceptionCustom("throw");
    }

    public static void demoThrows() throws ExceptionCustom2{
        // checked
        throw new ExceptionCustom2("error");
    }
}