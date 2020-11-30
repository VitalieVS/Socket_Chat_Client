package GUI;

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

    public Client() {
        initComponents();
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
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
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
