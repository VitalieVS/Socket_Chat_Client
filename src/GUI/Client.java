package GUI;

import Log.Log;

import javax.swing.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends JFrame {
    private JPanel mainPanel;
    private JTextArea clientTextArea;
    private JTextField clientTextField;
    private JButton clientSendButton;

    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;

    private Log chatLog;

    public Client() {
        initComponents();
        chatLog = new Log();
        clientSendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msgout = "";
                msgout = clientTextField.getText().trim();
                try {
                    dout.writeUTF(msgout);
                    if (clientTextArea.getText().isEmpty()) {
                        clientTextArea.setText(
                                clientTextArea.getText().trim() + msgout);
                    } else {
                        clientTextArea.setText(
                                clientTextArea.getText().trim() + "\n" + msgout);
                    }
                    chatLog.writeLog("Client:", msgout);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                chatLog.openLog();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                chatLog.closeLog();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        clientMessaging();
        clientSendButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Key:");
                super.keyPressed(e);
            }
        });
    }

    public void clientMessaging() {
        try {
            s = new Socket("127.0.0.1", 1201);
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            String msgin = "";
            while (!msgin.equals("exit")) {
                msgin = din.readUTF();
                if (clientTextArea.getText().isEmpty()) {
                    clientTextArea.setText(
                            clientTextArea.getText().trim() + "Server:" + msgin);
                }
                 else {
                    clientTextArea.setText(
                            clientTextArea.getText().trim() + "\nServer:" + msgin);
                }
                chatLog.writeLog("Client:", msgin);
            }
        } catch (Exception e) {
            System.out.println("error:" + e);
        }
    }

    void initComponents(){
        this.setTitle("Socket - Client");
        this.setContentPane(mainPanel);
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

}
