package Layout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChatLayout extends JFrame {
    JTextArea jText;
    JPanel jPanel;
    JTextField jInput;
    JButton btnSend;

    public ChatLayout(){
        setTitle("myChat");
        //setIconImage();
        setSize(new Dimension(400, 350));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        jText = new JTextArea();
        jText.setPreferredSize(new Dimension(400, 350));
        jText.setEditable(false);
        jText.setLineWrap(true);
        jText.setBackground(new Color(255, 248, 220));
        jText.setForeground(new Color(128, 0, 0));
        JScrollPane scroll = new JScrollPane(jText);
        add(scroll, BorderLayout.CENTER);
        jPanel = new JPanel();
        jPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        jPanel.setLayout(new FlowLayout());
        jPanel.setPreferredSize(new Dimension(400, 50));
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
                    send();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        jPanel.add(jInput);
        btnSend = new JButton("Отправить");
        btnSend.setBackground(new Color(255, 248, 220));
        btnSend.setForeground(new Color(128, 0, 0));
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                send();
            }
        });
        jPanel.add(btnSend);
        add(jPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void send(){
        if(!jInput.getText().equals(""))
        jText.append(jInput.getText() + "\n");
        jText.setCaretPosition(jText.getDocument().getLength());
        jInput.setText("");
    }
}
