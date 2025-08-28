package Src.JavaCore.OOP;
abstract class Person {
    private String name;
    // không hỗ trợ thuộc tính trừu tượng
//    abstract int age;

    protected Person(String name){
        this.name = name;
    }
    public abstract String eat(int a);
    public void speak(){
        System.out.println("Xin chào tôi tên: " + name);
    }
}
