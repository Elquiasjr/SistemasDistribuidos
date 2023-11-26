package client.interfaces;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitialPage extends JDialog {
    private JTextField IP;
    private JTextField Port;
    private JButton doneButton;
    private JPanel ServerConection;

    @Getter
    private String ip;
    @Getter
    private int port;

    public InitialPage(JFrame parent) {
        super(parent);
        setTitle("Server Connection");
        setContentPane(ServerConection);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(HIDE_ON_CLOSE);


        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ip = IP.getText();
                port = (Integer.parseInt(Port.getText()));
                dispose();
            }
        });
        setVisible(true);
    }
}
