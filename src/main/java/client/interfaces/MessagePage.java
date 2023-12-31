package client.interfaces;

import com.google.protobuf.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessagePage extends JDialog{
    private JPanel messageDisplay;
    private JLabel message;
    private JLabel type;

    public MessagePage(JFrame parent, String typeMessage, String textMessage){
        super(parent);
        setTitle("Attention!");
        setContentPane(messageDisplay);
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        type.setText(typeMessage);
        message.setText(textMessage);
        setMinimumSize(new Dimension(textMessage.length()*10, 150));
        setVisible(true);
    }
}
