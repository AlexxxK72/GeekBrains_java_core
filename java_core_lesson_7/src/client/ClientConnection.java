package client;

import common.ServerConst;
import common.Server_API;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection implements ServerConst, Server_API {
    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    private Boolean isAuthorized = false;

    public Boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorezed(Boolean authorized) {
        isAuthorized = authorized;
    }

    public ClientConnection() {
    }

    public void init (ChatWindow view){
        try {
            this.socket = new Socket(SERVER_URL, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            new Thread(()-> {
                try {
                   while(true){
                       String message = in.readUTF();
                       if(message.startsWith(AUTH_SUCCESSFUL)){
                           isAuthorized = true;
                           view.switchPanel();
                           break;
                       }
                       view.showMessage(message);
                   }
                   while(true){
                       String message = in.readUTF();
                       if(message.startsWith(SYSTEM_SYMBOL)){
                           String[] items = message.split("");
                           if(items[0].equals(CLOSE_CONNECTION)) view.switchPanel();
                       }
                       else {
                           view.showMessage(message);
                       }
                   }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg){
        try {
            out.writeUTF(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void auth(String login, String pass){
        try {
            out.writeUTF(AUTH + " " + login + " " + pass);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnected(){
        try {
            out.writeUTF(CLOSE_CONNECTION);
            out.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
