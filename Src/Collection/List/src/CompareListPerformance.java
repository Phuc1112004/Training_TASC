import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CompareListPerformance {
    public static void main(String[] args) {
        int n = 100000; // số lượng phần tử

        // Test ArrayList
        List<Integer> arrayList = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            arrayList.add(i); // thêm vào cuối
        }
        long end = System.currentTimeMillis();
        System.out.println("ArrayList add cuối: " + (end - start) + " ms");

        // Test LinkedList
        List<Integer> linkedList = new LinkedList<>();
        start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            linkedList.add(i); // thêm vào cuối
        }
        end = System.currentTimeMillis();
        System.out.println("LinkedList add cuối: " + (end - start) + " ms");

        // Test thêm vào đầu
        arrayList.clear();
        linkedList.clear();

        start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            arrayList.add(0, i); // thêm vào đầu
        }
        end = System.currentTimeMillis();
        System.out.println("ArrayList add đầu: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            linkedList.add(0, i); // thêm vào đầu
        }
        end = System.currentTimeMillis();
        System.out.println("LinkedList add đầu: " + (end - start) + " ms");
    }
}

