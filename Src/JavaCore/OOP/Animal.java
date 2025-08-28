package Src.JavaCore.OOP;

import java.time.LocalDate;

public class Animal {
    public String name;
    private String color;
    protected int age;
    LocalDate dob;

    public void setName(String name){
        this.name = name;
    }

    final void run(){
        System.out.println("running");
    }

    public void speak(){
        System.out.println("speaking");
    }
}
