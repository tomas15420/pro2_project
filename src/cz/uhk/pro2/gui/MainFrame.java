package cz.uhk.pro2.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    JTextField txtInputName, txtInputMessage;
    JButton btnLogin, btnSend;
    JTextArea txtAreaChat;

    public MainFrame(int width, int height){
        super("PRO2 Chat client 2021");
        setSize(width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initGui();
    }

    private void initGui(){
        JPanel panelMain = new JPanel(new BorderLayout());

        JPanel panelLogin = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelChat = new JPanel();
        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //Login
        txtInputName = new JTextField("", 30);
        panelLogin.add(new JLabel("Jméno"));
        panelLogin.add(txtInputName);

        btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button login clicked - "+txtInputName.getText());
            }
        });
        panelLogin.add(btnLogin);

        //Chat
        panelChat.setLayout(new BoxLayout(panelChat, BoxLayout.X_AXIS));
        txtAreaChat = new JTextArea();
        txtAreaChat.setAutoscrolls(true);
        txtAreaChat.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaChat);
        panelChat.add(scrollPane);
        for(int i = 0; i < 50; i ++){
            txtAreaChat.append("Text "+i+"\n");
        }

        //Footer
        txtInputMessage = new JTextField("",50);
        panelFooter.add(txtInputMessage);
        btnSend = new JButton("Send");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("clicked on send: "+txtInputMessage.getText());
            }
        });
        panelFooter.add(btnSend);

        panelMain.add(panelLogin, BorderLayout.NORTH);
        panelMain.add(panelChat, BorderLayout.CENTER);
        panelMain.add(panelFooter, BorderLayout.SOUTH);
        add(panelMain);
    }
}
