package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        clientMessaging();
    }

    public void clientMessaging() {
        try {
            s = new Socket("127.0.0.1", 1201);
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            String msgin = "";
            while (!msgin.equals("exit")) {
                msgin = din.readUTF();

                clientTextArea.setText(
                        clientTextArea.getText().trim() + "\n Server:\t" + msgin);
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
