package Src.JavaCore.OOP;

public class Employee implements IPerson, IChilden {
    @Override
    public int eat(int a) {
        System.out.println("Nhăm nhăm");
        return 1;
    }

    @Override
    public void eat() {
        System.out.println("Nhăm nhăm");
    }

    @Override
    public void sleep() {
        System.out.println("go to sleep");
    }
}
