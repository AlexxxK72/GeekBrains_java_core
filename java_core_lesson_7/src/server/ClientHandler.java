package server;

import common.Server_API;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Server_API{
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nick;

    public ClientHandler(Server server, Socket socket) {

        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            nick = "undefined";
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                //Auth
                while (true) {
                    String message = in.readUTF();
                    if (message.startsWith(AUTH)) {
                        String[] items = message.split(" ");
                        String nick = server.getAuthService().getNickByLoginAndPass(items[1], items[2]);
                        if(nick != null){
                            if(!server.isNickBusy(nick)){
                                sendMessage(AUTH_SUCCESSFUL + " " + nick);
                                this.nick = nick;
                                server.broadcastUsersList();
                                server.broadcast(this.nick + " has entered the chat room");
                                break;
                            }
                            else sendMessage("This account is already in use!");
                        }
                        else sendMessage("Wrong login/password!");
                    }
                    else sendMessage("You should authorize first!");
                }
                while (true) {
                    String message = in.readUTF();
                    if (message.startsWith(SYSTEM_SYMBOL)) {
                        if (message.equalsIgnoreCase(CLOSE_CONNECTION)) break;
                        else if(message.startsWith(UNICAST)){
                            String nameTo = message.split(" ")[1];
                            String messageText = message.substring(UNICAST.length() + nameTo.length() + 2);
                            server.unicast(this, nameTo, messageText);
                        }
                        else sendMessage("Command doesn't exist!");
                    } else {
                        System.out.println("client " + message);
                        server.broadcast(this.nick + ": " + message);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                disconnected();
            }
        }).start();
    }

    public String getNick() {
        return nick;
    }

    public void sendMessage(String msg){
        try {
            out.writeUTF(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnected(){
        try {
            sendMessage(CLOSE_CONNECTION + " You have been disconnected!");
            server.unSubscribeMe(this);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
