import java.util.*;

public class MapDemo {
    public static void main(String[] args) {
        // Tạo các Map khác nhau
        Map<String, Integer> hashMap = new HashMap<>();
        Map<String, Integer> linkedHashMap = new LinkedHashMap<>();
        Map<String, Integer> treeMap = new TreeMap<>();

        // Thêm cùng 1 tập dữ liệu
        hashMap.put("Banana", 2);
        hashMap.put("Apple", 5);
        hashMap.put("Orange", 3);
        hashMap.put("Grape", 4);

        linkedHashMap.put("Banana", 2);
        linkedHashMap.put("Apple", 5);
        linkedHashMap.put("Orange", 3);
        linkedHashMap.put("Grape", 4);

        treeMap.put("Banana", 2);
        treeMap.put("Apple", 5);
        treeMap.put("Orange", 3);
        treeMap.put("Grape", 4);

        // In kết quả
        System.out.println("HashMap (không đảm bảo thứ tự):");
        System.out.println(hashMap);

        System.out.println("\nLinkedHashMap (giữ thứ tự insertion):");
        System.out.println(linkedHashMap);

        System.out.println("\nTreeMap (sorted theo key):");
        System.out.println(treeMap);
    }
}

