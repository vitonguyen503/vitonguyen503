import java.io.*;

public class ReadByteArray {
    public static void main(String[] args) {
        byte[] byteArray = new byte[100]; // tạo mảng byte gồm 100 phần tử
        InputStream inputStream = System.in; // tạo luồng nhập từ bàn phím

        try {
            System.out.println("Nhap vao cac gia tri cua mang byte: ");
            inputStream.read(byteArray); // đọc dữ liệu vào mảng byte từ luồng nhập

            // in ra giá trị của mảng byte vừa đọc được
            System.out.println("Cac gia tri cua mang byte la: ");
            for(byte i : byteArray) System.out.print(i + " ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}