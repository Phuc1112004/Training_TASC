package Src.JavaCore.OOP;

abstract class Engineer extends Person{
    protected Engineer(String name){
        super(name);
    }

    protected abstract void work();
}
