public class Main {
    public static void main(String[] args) {
        // khởi tạo mảng
        int[] arr = new int[3];
        arr[0] = 1;
        arr[1] = 2;
        arr[2] = 3;

        int[] arr2 = {1, 2, 3};
        int[] arr3 = arr2;

        // so sánh địa chỉ biến
        if (arr3 == arr2) {
            System.out.println("arr3 = arr2");
        } else {
            System.out.println("arr3 khác arr2");
        }

        if (arr == arr2) {
            System.out.println("arr = arr2");
        } else {
            System.out.println("arr khác arr2");
        }

        int n = 1000000000;
        int[] arr4 = new int[n];
        for (int i = 0; i < n; i++) {
            arr4[i] = i;
        }

        long startTime = System.nanoTime();
        int a = arr[arr.length - 1];
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Thời gian thực thi: " + duration + " ns");

        System.out.println(arr4[0]);
        // lỗi
//        System.out.println(arr4[n]);
        arr2();
    }

    static void arr2(){
        int[][] arr = new int[3][3];
        arr[1][1] = 2;
        System.out.println("Phần tử vị trí 1,1: "+ arr[1][1]);
        System.out.println(arr.length);
    }
}