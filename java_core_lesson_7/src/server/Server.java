package server;

import common.ServerConst;
import common.Server_API;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server implements ServerConst, Server_API {
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
            if(!client.getNick().equals("undefined"))
                client.sendMessage(msg);
        }
    }

    public void broadcastUsersList(){
        StringBuffer sb = new StringBuffer(USERS_LIST);
        for (ClientHandler client : clients) {
            if(!client.getNick().equals("undefined"))
                sb.append(" " + client.getNick());
        }
        broadcast(sb.toString());
    }
    
    public void unicast(ClientHandler from, String to, String msg) {
        boolean nickFound = false;
        for (ClientHandler client : clients) {
            if(client.getNick().equals(to)){
                nickFound = true;
                client.sendMessage("from " + from.getNick() + ": " + msg);
                from.sendMessage("to " + from.getNick() + ": " + msg);
                break;
            }
        }
        if(!nickFound){
            from.sendMessage("User not found!");
        }
    }

    public void unSubscribeMe(ClientHandler client){
        clients.remove(client);
        broadcastUsersList();
    }

    public boolean isNickBusy(String nick){
        for (ClientHandler client : clients) {
            if(client.getNick().equals(nick)) return true;
        }
        return false;
    }
}
