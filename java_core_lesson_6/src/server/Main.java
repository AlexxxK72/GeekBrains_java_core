package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    private static final int PORT = 8189;
    private static ServerSocket serv;
    private static Socket sock;
    private static Scanner in;
    private static PrintWriter out;

    public static void main(String[] args) {


        try {
            serv = new ServerSocket(PORT);
            System.out.println("Сервер запущен ожидаем подключения!");
            sock = serv.accept();
            System.out.println("Клиент подключился!");
            in = new Scanner(sock.getInputStream());
            out = new PrintWriter(sock.getOutputStream());
            consoleListener();
            inListener();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                serv.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                        if (st.equalsIgnoreCase("end Session")) System.exit(0);
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
                    }
                }
            }
        });
        inT.start();
    }
}
