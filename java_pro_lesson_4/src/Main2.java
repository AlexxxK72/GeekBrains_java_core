import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main2 {
    public  static File file = new File("1.txt");
    public static FileOutputStream out;
    public static int currThread = 1;
    public static long millSec = System.currentTimeMillis();

    static {
        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(()->{
            try {
                synchronized (file){
                    for (int i = 0; i < 10; i++) {
                        while (currThread != 1) file.wait();
                        writeFile("Поток: 1 Запись: " + i + " " + (millSec - System.currentTimeMillis()) + "\n");
                        file.notifyAll();
                    }
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                synchronized (file){
                    for (int i = 0; i < 10; i++) {
                        while (currThread != 2) file.wait();
                        writeFile("Поток: 2 Запись: " + i + " " + (millSec - System.currentTimeMillis()) + "\n");
                        file.notifyAll();
                    }
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                synchronized (file){
                    for (int i = 0; i < 10; i++) {
                        while (currThread != 3) file.wait();
                        writeFile("Поток: 3 Запись: " + i + " " + (millSec - System.currentTimeMillis()) + "\n");
                        file.notifyAll();
                    }
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }).start();
    }

    public static void writeFile(String rec){
        byte[] arr = rec.getBytes();
        try {
            out.write(arr);
            Thread.sleep(20);
            if(currThread == 3) currThread = 1;
            else currThread++;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
