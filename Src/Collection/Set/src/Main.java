import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("HashSet");
        set(new HashSet<>());
        System.out.println("LinkedHashSet");
        set(new LinkedHashSet<>());
        System.out.println("TreeSet");
        set(new TreeSet<>());

    }

    static void set(Set<Object> set) {
        // set không thêm phần tử đã tồn tại
        set.add(1);
        set.add(3);
        set.add(2);
        set.add(1);
        System.out.println(set);
        try {
            set.add(null);
        } catch (NullPointerException e) {
            System.out.println("Lỗi khi thêm null");
        }


        try {
            set.add(new Person("Phuc"));
            set.add(new Person("Phuc"));
            System.out.println(set);
        }catch (ClassCastException e){
            System.out.println("Lỗi thêm person");
        }

        System.out.println("1 tồn tại trong set: " + set.contains(1));
    }
}

class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }
}