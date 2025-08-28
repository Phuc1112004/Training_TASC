package Src.JavaCore.OOP;

import java.time.LocalDate;

public class Cat extends Animal {
//    lỗi vì phương thức run là final
//    @Override
//    public void run(){
//
//    }

    @Override
    public void speak(){
        System.out.println("Meo meo");
    }

    public void eat(){
        System.out.println("Nhăm nhăm");
    }

    public String eat(String sound){
        return "Nhăm nhăm";
    }

    public String eat(String sound, int times){
        return "Nhăm nhăm * " + times;
    }

    public void start() {
        this.setName("Mèo");
        this.age = 2;
        this.dob = LocalDate.now();

        System.out.println("Tên động vật: " + this.name);
        // không thể truy cập thuộc tính color vì là private
//        System.out.println("Màu lông: "+ this.color);
        System.out.println("Tuổi: " + this.age);
        System.out.println("Ngày sinh: " + this.dob);
    }
}
