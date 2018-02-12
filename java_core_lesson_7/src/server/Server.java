package server;

import common.ServerConst;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server implements ServerConst {
    private Vector<ClientHandler> clients;
    private AuthService authService;
    public AuthService getAuthService(){
        return authService;
    }
    public Server() {
        ServerSocket serverSocket = null;
        authService = new BaseAuthService();
        authService.start(); //placeholder
        Socket sock = null;
        clients = new Vector<>();
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер запущен ожидаем подключения!");
            while (true) {
                sock = serverSocket.accept();
                clients.add(new ClientHandler(this, sock));
                System.out.println("Клиент подключился!");
            }
        }
        catch (IOException e){
            System.out.println("Ошибка инициализации");
        }
        finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcast(String msg){
        for (ClientHandler client : clients){
            client.sendMessage(msg);
        }
    }

    public void unicast(String nick, String msg){
        for (ClientHandler client : clients){
            //if(client.n)
        }
    }


    public void unSubscribeMe(ClientHandler client){
        clients.remove(client);
    }
}
