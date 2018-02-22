import java.io.IOException;
import java.net.Socket;

public class Main1 {
    public static void main(String[] args){
//        Object obj = 10;
//        String str = (String) obj.toString();
//        System.out.println(str);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run(){
                System.out.println(1);
            }
        });
        Thread t = new Thread(t1);
        t.start();
        try{

        }

    }

}
