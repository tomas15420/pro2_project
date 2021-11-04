package cz.uhk.pro2.gui;

import cz.uhk.pro2.models.ChatClient;
import cz.uhk.pro2.models.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    JTextField txtInputName, txtInputMessage;
    JButton btnLogin, btnSend;
    JTable tblLoggedUsers;
    JTextArea txtAreaChat;

    LoggedUsersTableModel loggedUsersTableModel;

    ChatClient chatClient;

    public MainFrame(int width, int height, ChatClient chatClient){
        super("PRO2 Chat client 2021");
        this.chatClient = chatClient;

        setSize(width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initGui();
    }

    private void initGui(){
        JPanel panelMain = new JPanel(new BorderLayout());

        JPanel panelLogin = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelChat = new JPanel();
        JPanel panelLoggedUsers = new JPanel();
        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.LEFT));

        initLoginPanel(panelLogin);
        initChatPanel(panelChat);
        initLoggedUsersPanel(panelLoggedUsers);
        initFooterPanel(panelFooter);

        panelMain.add(panelLogin, BorderLayout.NORTH);
        panelMain.add(panelChat, BorderLayout.CENTER);
        panelMain.add(panelLoggedUsers, BorderLayout.EAST);
        panelMain.add(panelFooter, BorderLayout.SOUTH);
        add(panelMain);

        refreshMessages();
    }

    private void initLoginPanel(JPanel panel){
        txtInputName = new JTextField("", 30);
        panel.add(new JLabel("Jméno"));
        panel.add(txtInputName);

        btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> {
            if(chatClient.isAuthenticated()){
                chatClient.logout();
                btnLogin.setText("Login");
                txtInputName.setEnabled(true);
                txtAreaChat.setEnabled(false);
            }
            else{
                if(txtInputName.getText().length() <= 0){
                    JOptionPane.showMessageDialog(null,
                            "Enter your username",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                chatClient.login(txtInputName.getText());
                btnLogin.setText("Logout");
                txtInputName.setEnabled(false);
                txtAreaChat.setEnabled(true);

            }
        });
        panel.add(btnLogin);
    }

    private void initChatPanel(JPanel panel){
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        txtAreaChat = new JTextArea();
        txtAreaChat.setAutoscrolls(true);
        txtAreaChat.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaChat);
        chatClient.addActionListenerUpdateMessages(e -> refreshMessages());
        panel.add(scrollPane);
    }

    private void initFooterPanel(JPanel panel){
        txtInputMessage = new JTextField("",50);
        panel.add(txtInputMessage);
        btnSend = new JButton("Send");
        btnSend.addActionListener(e -> {
            String text = txtInputMessage.getText();
            if(text.length() <= 0)
                return;
            if(!chatClient.isAuthenticated())
                return;

            chatClient.sendMessage(text);
            txtInputMessage.setText("");
        });
        panel.add(btnSend);
    }

    private void initLoggedUsersPanel(JPanel panel){
        String[] colNames = new String[] { "Col1", "Col2" };

        Object[][] data = new Object[][] {{"1:1","1:2"},{"2:1","2:2"}};

        //tblLoggedUsers = new JTable(data,colNames);
        loggedUsersTableModel = new LoggedUsersTableModel(chatClient);

        tblLoggedUsers = new JTable(data,colNames);
        tblLoggedUsers.setModel(loggedUsersTableModel);

        chatClient.addActionListenerLoggedUsersChanged(e -> loggedUsersTableModel.fireTableDataChanged());

        JScrollPane scrollPane = new JScrollPane(tblLoggedUsers);
        scrollPane.setPreferredSize(new Dimension(250,500));
        panel.add(scrollPane);
    }

    private void refreshMessages(){
        txtAreaChat.setText("");

        for(Message msg : chatClient.getMessages()){
            txtAreaChat.append(msg.toString());
            txtAreaChat.append("\n\n");
        }
    }
}
