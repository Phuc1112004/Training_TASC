package Src.Collection.Set.src;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class SetExemple {
    public static void main(String[] args) {
        Set<Integer> hashSet = new HashSet<>();
        hashSet.add(1);
        hashSet.add(2);
        hashSet.add(3);
        hashSet.add(2);
        hashSet.size();
        hashSet.remove(1);
        System.out.println("HashSet: " + hashSet);
        System.out.println("Size: " + hashSet.size());

        Set<String> linkedHasSet = new LinkedHashSet<>();
        linkedHasSet.add("Java");
        linkedHasSet.add("Python");
        linkedHasSet.add("C++");
        linkedHasSet.add("Python");
        System.out.println("LinkedHashSet: " + linkedHasSet);

        Set<String> treeSet = new TreeSet<>();
        treeSet.add("Java");
        treeSet.add("Python");
        treeSet.add("C++");
        treeSet.add("C++");
        System.out.println("TreeSet" + treeSet);

        Set<String> nameSV = new LinkedHashSet<>();
        nameSV.add("Phuc");
        nameSV.add("phuc2");
        nameSV.add("phuc3");
        nameSV.add("phuc4");
        nameSV.add("phuc3");
        nameSV.add("phuc4");
        nameSV.add(null);
        nameSV.add(null);
        System.out.println("nameSV: " + nameSV);
        System.out.println("nameSV.size(): " + nameSV.size());
    }
}
