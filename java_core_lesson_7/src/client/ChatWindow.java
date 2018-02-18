package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChatWindow extends JFrame {
    JTextArea jText;
    JTextArea usersList;
    JTextField authTime;
    JPanel input;
    JTextField jInput;
    JButton btnSend;
    JTextField login;
    JPasswordField password;
    JPanel authPanel;
    ClientConnection clientConnection;
    private Boolean timeoutAuth;
    private long time;


    public ChatWindow(){
        clientConnection = new ClientConnection();
        timeoutAuth = false;

        setTitle("myChat");
        setSize(new Dimension(400, 400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        login = new JTextField();
        password = new JPasswordField();
        JButton auth = new JButton("Login");
        auth.setBackground(new Color(255, 248, 220));
        auth.setForeground(new Color(46, 139, 87));
        auth.addActionListener(e -> auth());
        authPanel = new JPanel(new GridLayout(1, 3));
        authPanel.add(login);
        authPanel.add(password);
        authPanel.add(auth);
        add(authPanel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new BorderLayout());
        authTime = new JTextField();
        authTime.setForeground(new Color(128, 0, 0));
        authTime.setHorizontalAlignment(SwingConstants.CENTER);
        usersList = new JTextArea();
        usersList.setEditable(false);
        usersList.setBackground(new Color(255, 248, 220));
        usersList.setForeground(new Color(46, 139, 87));
        JScrollPane scroll1 = new JScrollPane(usersList);
        scroll1.setPreferredSize(new Dimension(128, 350));
        infoPanel.add(authTime, BorderLayout.NORTH);
        infoPanel.add(scroll1, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.EAST);

        jText = new JTextArea();
        jText.setEditable(false);
        jText.setLineWrap(true);
        jText.setBackground(new Color(255, 248, 220));
        jText.setForeground(new Color(128, 0, 0));
        JScrollPane scroll2 = new JScrollPane(jText);
        add(scroll2, BorderLayout.CENTER);

        input = new JPanel();
        input.setLayout(new BorderLayout());
        input.setPreferredSize(new Dimension(400, 25));
        jInput = new JTextField();
        jInput.setBackground(new Color(255, 248, 220));
        jInput.setForeground(new Color(128, 0, 0));
        jInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 10)
                    sendMessage();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        input.add(jInput, BorderLayout.CENTER);
        btnSend = new JButton("Отправить");
        btnSend.setBackground(new Color(255, 248, 220));
        btnSend.setForeground(new Color(128, 0, 0));
        btnSend.setPreferredSize(new Dimension(128, 25));
        btnSend.addActionListener(e -> sendMessage());
        input.add(btnSend, BorderLayout.EAST);
        add(input, BorderLayout.SOUTH);

        switchPanel();



        setVisible(true);
    }


    public void sendMessage(){
        String message = jInput.getText();
        clientConnection.sendMessage(message);
        jInput.setText("");
    }

    public void auth(){
        if(!timeoutAuth) {
            if (!clientConnection.isConnected()) {
                clientConnection.init(this);

                new Thread(() -> {
                    time = System.currentTimeMillis();
                    long timeCurrent = 0;
                    while (timeCurrent < 120000 && !clientConnection.isAuthorized()) {
                        try {
                            Thread.sleep(1000);
                            int min = (int) (timeCurrent / 60000);
                            int sec = (int) (timeCurrent / 1000) - (min * 60);
                            authTime.setText(String.format("%02d:%02d", min, sec));
                            timeCurrent = System.currentTimeMillis() - time;

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (clientConnection.isAuthorized()) authTime.setText("Auth Ok");
                    else {
                        authTime.setText("Auth TimeOut");
                        timeoutAuth = true;
                        clientConnection.disconnect();
                    }
                }).start();
            }

            String l = login.getText();
            String p = new String(password.getPassword());
            if(!l.equals("") && !p.equals("")) {
                clientConnection.auth(l, p);
                login.setText("");
                password.setText("");
            }
        }
        else showMessage("Authorization timeout, restart chat");
    }

    public void showMessage(String message){
        jText.append(message + "\n");
        jText.setCaretPosition(jText.getDocument().getLength());
    }

    public void switchPanel() {
        authPanel.setVisible(!clientConnection.isAuthorized());
        input.setVisible(clientConnection.isAuthorized());
    }

    public void showUsersList(String[] users){
        usersList.setText("");
        for (String user : users) {
            usersList.append(user + "\n");
        }
    }

    public void setChatTitle(String title){
        setTitle(title);
    }
}
