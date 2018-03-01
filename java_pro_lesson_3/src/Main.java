import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Задание 1\n");
            readFile("1.txt");

            System.out.println("\nЗадание 2\n");
            ArrayList<File> list = new ArrayList<>();
            list.add(new File("1.txt"));
            list.add(new File("2.txt"));
            list.add(new File("3.txt"));
            list.add(new File("4.txt"));
            list.add(new File("5.txt"));
            glueFiles(list);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFile(String path) throws IOException {

        File file = new File(path);
        if (file.exists()) {
            FileInputStream in = new FileInputStream(file);
            byte[] bArr = new byte[512];
            while (in.read(bArr) > 0) {
                System.out.println(new String(bArr));
            }
        }
    }

    public static void glueFiles(ArrayList<File> lFiles) throws IOException{
        ArrayList<InputStream> al = new ArrayList<>();
        for (File file : lFiles) {
            al.add(new FileInputStream(file));
        }
        Enumeration<InputStream> e = Collections.enumeration(al);
        SequenceInputStream sin = new SequenceInputStream(e);
        byte[] bArr = new byte[25];
        while (sin.read(bArr) > 0){
            System.out.println(new String(bArr));
        }
    }
}