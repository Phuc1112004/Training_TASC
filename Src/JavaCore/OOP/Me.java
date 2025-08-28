package Src.JavaCore.OOP;

public class Me extends Person implements IPerson{

    public Me(){
        super("Phúc");
    }
    @Override
    public String eat(int a) {
        System.out.println("Tôi đang ăn");
        return "";
    }

    @Override
    public void eat() {

    }

    public void start(){
        speak();
    }
}
