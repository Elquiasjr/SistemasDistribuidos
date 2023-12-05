package client.interfaces;

import server.dtobject.user.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayUserPage extends JDialog{
    private JLabel message;
    private JButton OKButton;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField typeField;
    private JPanel DisplayUserPage;
    private JTextField idField;

    public DisplayUserPage(JFrame parent, String messageText, UserDTO user){
        super(parent);
        setTitle("User Display");
        setContentPane(DisplayUserPage);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        message.setText(messageText);
        nameField.setText(user.nome());
        emailField.setText(user.email());
        typeField.setText(String.valueOf(user.tipo()));
        idField.setText(String.valueOf(user.registro()));


        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
        setVisible(true);
    }
}
