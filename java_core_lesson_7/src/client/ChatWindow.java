package client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChatWindow extends JFrame {
    JTextArea jText;
    JPanel input;
    JTextField jInput;
    JButton btnSend;
    JTextField login;
    JPasswordField password;
    JPanel authPanel;
    ClientConnection clientConnection;


    public ChatWindow(){
        clientConnection = new ClientConnection();
        clientConnection.init(this);

        setTitle("myChat");
        setSize(new Dimension(400, 350));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        login = new JTextField();
        password = new JPasswordField();
        JButton auth = new JButton("Login");
        auth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                auth();
            }
        });
        authPanel = new JPanel(new GridLayout(1, 3));
        authPanel.add(login);
        authPanel.add(password);
        authPanel.add(auth);
        add(authPanel, BorderLayout.NORTH);
        jText = new JTextArea();
        jText.setEditable(false);
        jText.setLineWrap(true);
        jText.setBackground(new Color(255, 248, 220));
        jText.setForeground(new Color(128, 0, 0));
        JScrollPane scroll = new JScrollPane(jText);
        scroll.setPreferredSize(new Dimension(400, 350));
        add(scroll, BorderLayout.CENTER);
        input = new JPanel();
        input.setBorder(new EmptyBorder(5, 5, 5, 5));
        input.setLayout(new FlowLayout());
        input.setPreferredSize(new Dimension(400, 50));
        jInput = new JTextField();
        jInput.setPreferredSize(new Dimension(250, 30));
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
        input.add(jInput);
        btnSend = new JButton("Отправить");
        btnSend.setBackground(new Color(255, 248, 220));
        btnSend.setForeground(new Color(128, 0, 0));
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        input.add(btnSend);
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
        clientConnection.auth(login.getText(), new String(password.getPassword()));
        login.setText("");
        password.setText("");
    }

    public void showMessage(String message){
        jText.append(message + "\n");
        jText.setCaretPosition(jText.getDocument().getLength());
    }

    public void switchPanel() {
        authPanel.setVisible(!clientConnection.isAuthorized());
        input.setVisible(clientConnection.isAuthorized());
    }
}
