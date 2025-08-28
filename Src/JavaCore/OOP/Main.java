package Src.JavaCore.OOP;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Animal animal = new Animal();
        animal.setName("Mèo");
        animal.age = 2;
        animal.dob = LocalDate.now();

        System.out.println("Tên động vật: " + animal.name);
        // không thể truy cập thuộc tính color vì là private
//        System.out.println("Màu lông: "+ animal.color);
        System.out.println("Tuổi: " + animal.age);
        System.out.println("Ngày sinh: " + animal.dob);
    }
}