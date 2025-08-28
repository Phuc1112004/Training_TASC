package Src.JavaCore.DataType;

import java.util.Scanner;

public class DataTypeDemo {
    public static void main(String[] args) {
//        1. so sánh 2 biến nguyên thủy
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập hai số a, b để so sánh");
        int a = sc.nextInt();
        int b = sc.nextInt();
        System.out.println("1. So sánh hai kiểu nguyên thủy");
        System.out.println("a == b: " + (a==b));

//        2. so sánh hai object cùng giá trị
        Integer obj1 = Integer.valueOf(5);  //  = với "new Integer(5);"
        Integer obj2 = Integer.valueOf(5);
        // có thể dùng "Integer p = new Integer(5);"

        System.out.println("2. so sánh 2 object");
        System.out.println("obj1 == obj2: " + (obj2 == obj2));
        System.out.println("obj1.equals(obj2): " + obj2.equals(obj2));

        // 3. So sánh kiểu nguyên thủy với kiểu đối tượng
        Integer obj3 = 5;
        int c = 5;
        System.out.println("3. So sánh kiểu nguyên thủy với kiểu đối tượng:");
        System.out.println("obj3 == c: " + (obj3 == c));
    }
}
