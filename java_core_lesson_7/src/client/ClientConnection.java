package client;

import common.ServerConst;
import common.Server_API;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

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

    public Boolean isConnected() {
        return socket != null && !socket.isClosed();
    }


    public void init (ChatWindow view){
        try {
            connect();
            new Thread(()-> {
                try {
                    while (true) {
                        String message = in.readUTF();
                        if (message.startsWith(AUTH_SUCCESSFUL)) {
                            isAuthorized = true;
                            view.setTitle(message.substring(AUTH_SUCCESSFUL.length() + 1));
                            view.switchPanel();
                            break;
                        }
                        view.showMessage(message);
                    }
                    while (true) {
                        String message = in.readUTF();
                        if (message != null) {
                            if (message.startsWith(SYSTEM_SYMBOL)) {
                                String[] items = message.split(" ");
                                if (items[0].equals(CLOSE_CONNECTION)){
                                    setAuthorezed(false);
                                    view.showMessage(message.substring(CLOSE_CONNECTION.length() + 1));
                                    view.setTitle("myChat");
                                    view.switchPanel();
                                    view.showUsersList(new String[0]);
                                    view.authTime.setText("");
                                    disconnect();
                                }
                                else if(message.startsWith(USERS_LIST)){
                                    String msgUsers = message.substring(USERS_LIST.length() + 1);
                                    String[] users = msgUsers.split(" ");
                                    Arrays.sort(users);
                                    view.showUsersList(users);
                                }
                            } else {
                                view.showMessage(message);
                            }
                        }
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                finally {
                    disconnect();
                }
            }).start();
        }
        catch (IOException e) {
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

    public void connect() throws IOException{
        socket = new Socket(SERVER_URL, PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    public void auth(String login, String pass){
        try {
            out.writeUTF(AUTH + " " + login + " " + pass);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){
        try {
            out.writeUTF(CLOSE_CONNECTION);
            out.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
