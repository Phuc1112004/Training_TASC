package Src.JavaCore.String;

public class StringDemo {
    public static void main(String[] args) {
        stringFunction();
        stringPool();
    }

    private static void stringFunction() {
        String template = "xin chào TASC";
        System.out.println("Chuỗi mẫu: " + template);
        System.out.println("Độ dài chuỗi: " + template.length());
        System.out.println("Ký tự thứ 5: " + template.charAt(5));
        System.out.println("Chuỗi con từ vị trí 4 đến 7: " + template.substring(4, 8));
        System.out.println("Vị trí của chuỗi TASC: " + template.indexOf("TASC"));
        System.out.println("Chuyển thành dữ liệu viết thường: " + template.toLowerCase());
        System.out.println("Chuyển thành dữ liệu viết hoa: " + template.toUpperCase());
        System.out.println("thay thế chuỗi TASC thành công ty TASC: " + template.replace("TASC", "công ty TASC"));
        System.out.println("Chuyển thành mảng bằng ký tự space: ");
        String[] splitResult = template.split(" ");
        for (String item : splitResult) {
            System.out.printf(item + " | ");
        }
        System.out.printf("\n");
        String template2 = "\n\t xin chào TASC \n";
        System.out.println("Chuỗi mẫu 2: " + template2);
        System.out.println("Xoá khoảng trắng 2 đầu chuỗi: " + template2.trim());

        System.out.println("Chuỗi 1 giống chuỗi 2: " + (template.equals(template2) ? "đúng" : "sai"));
        int compareResult = template.compareTo(template2);
        if (compareResult == -1) {
            System.out.println("Chuỗi 1 đứng trước chuỗi 2");
        } else if (compareResult == 0) {
            System.out.println("Chuỗi 1 có vị trí bằng chuỗi 2");
        } else {
            System.out.println("Chuỗi 1 đứng sau hơn chuỗi 2");
        }

        System.out.println("Sau khi sử dụng 1 loạt phương thức của String chuỗi ban đầu không thay đổi: " + template);
    }

    private static void stringPool() {
        String a = "Phúc";
        String b = "Phúc";
        String c = new String("Phúc");
        String d = c.intern();
        String e = " Phúc";
        System.out.println("Chuỗi a: " + a);
        System.out.println("Chuỗi b: " + b);
        System.out.println("Chuỗi c: " + c);
        System.out.println("Chuỗi d: " + d);
        System.out.println("Chuỗi e: " + e);
        System.out.println("Chuỗi a cùng pool b: " + (a == b));
        System.out.println("Chuỗi c cùng pool b: " + (c == b));
        System.out.println("Chuỗi d cùng pool b: " + (d == b));
        System.out.println("Chuỗi e cùng pool b: " + (e.trim() == b));
        System.out.println("Nội dung chuỗi a giống nội dung chuỗi b: " + (a.equals(b)));
        System.out.println("Nội dung chuỗi c giống nội dung chuỗi b: " + (c.equals(b)));
        System.out.println("Nội dung chuỗi e giống nội dung chuỗi b: " + (e.equals(b)));
    }

}

