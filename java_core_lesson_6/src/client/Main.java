package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    private static final String SERVER_ADDR = "localhost";
    private static final int SERVER_PORT = 8189;
    private static Socket sock;
    private static Scanner in;
    private static PrintWriter out;

    public static void main(String[] args) {

        try {
            sock = new Socket(SERVER_ADDR, SERVER_PORT);
            in = new Scanner(sock.getInputStream());
            out = new PrintWriter(sock.getOutputStream());
            inListener();
            consoleListener();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void consoleListener() {
        Thread cT = new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner scan = new Scanner(System.in);
                while (true) {
                    if (scan.hasNext()) {
                        String st = scan.nextLine();
                        out.println(st);
                        out.flush();
                        /*for (int i = 0; i < 100; i++) {

                            out.println("Message " + i);
                            out.flush();
                        }*/
                    }
                }
            }
        });
        cT.start();
    }

    public static void inListener(){
        Thread inT = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(in.hasNext()){
                        String st = in.nextLine();
                        System.out.println(st);
                        if(st.equalsIgnoreCase("end Session")) System.exit(0);
                    }
                }
            }
        });
        inT.start();
    }
}
