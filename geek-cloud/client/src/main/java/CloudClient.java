import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class CloudClient implements iServer{
    private Controller controller;

    private String login;
    private String pass;
    private Socket socket;
    private ObjectDecoderInputStream in = null;
    private ObjectEncoderOutputStream out = null;
    private Boolean isAuthorized = false;


    public CloudClient(Controller controller) {
        try {
            this.controller = controller;
            socket = new Socket(HOST, PORT);
            in = new ObjectDecoderInputStream(socket.getInputStream());
            out = new ObjectEncoderOutputStream(socket.getOutputStream());
            readIn();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLogin() {
        return login;
    }

    private void readIn() {
        Thread rIn = new Thread(() -> {
            try {
                while (!isAuthorized){
                    Object msg = in.readObject();

                    if (msg != null && msg instanceof CommandMessage){
                        CommandMessage message = (CommandMessage) msg;
                        CommandMessage.Commands cmd = message.getCmd();
                        if(cmd == CommandMessage.Commands.Auth_Result) {
                            Object[] args = message.getArgs();
                            if((boolean)args[0]){
                                this.login = args[1].toString();
                                this.pass = args[2].toString();
                                controller.setCloudPath(args[3].toString());
                                isAuthorized = true;
                                controller.flipAuthPanel();
                                controller.refreshLocalList();
                                getList();
                            }
                        }
                    }
                    while (isAuthorized) {
                        msg = in.readObject();

                        if (msg != null && msg instanceof CommandMessage) {
                            CommandMessage message = (CommandMessage) msg;
                            CommandMessage.Commands cmd = message.getCmd();
                            if(cmd == CommandMessage.Commands.LogOut) break;
                            switch (cmd) {
                                case List:
                                    controller.setCloudListFile((ArrayList<String>) message.getArgs()[0]);
                                    controller.refreshCloudList();
                                    break;
                                case IsDirectory:
                                    controller.setCloudDirectory((Boolean) message.getArgs()[0]);
                                    break;
                                case Download:
                                    controller.downloadFile(message.getArgs()[0].toString(), (byte[]) message.getArgs()[1]);
                                    break;
                            }
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        rIn.setDaemon(true);
        rIn.start();
    }

    public void auth(String login, String pass) {
        Object[] args = {login, pass};
        CommandMessage msg = new CommandMessage(CommandMessage.Commands.Auth, args);
        sendMessage(msg);
    }

    public void logout(){
        isAuthorized = false;
        login = null;
        pass = null;
        sendMessage(new CommandMessage(CommandMessage.Commands.LogOut,
                null));
    }

    public void getList() {
        Object[] args = {controller.getCloudCurrentPath()};
        CommandMessage msg = new CommandMessage(CommandMessage.Commands.List, args);
        sendMessage(msg);
    }

    public void isDirectory(String file) {
        Object[] args = {file};
        CommandMessage msg = new CommandMessage(CommandMessage.Commands.IsDirectory, args);
        sendMessage(msg);
    }

    public void deleteFiles(String name) {
        Object[] args = {name};
        CommandMessage msg = new CommandMessage(CommandMessage.Commands.Delete, args);
        sendMessage(msg);
    }

    public void renameFile(String oldName, String newName) {
        Object[] args = {oldName, newName};
        CommandMessage msg = new CommandMessage(CommandMessage.Commands.Rename, args);
        sendMessage(msg);
    }

    public void uploadFile(String name, byte[] file) {
        Object[] args = {name, file};
        CommandMessage msg = new CommandMessage(CommandMessage.Commands.Upload, args);
        sendMessage(msg);
    }

    public void downloadFile(String name) {
        Object[] args = {name};
        CommandMessage msg = new CommandMessage(CommandMessage.Commands.Download, args);
        sendMessage(msg);
    }

    private void sendMessage(CommandMessage msg) {
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
