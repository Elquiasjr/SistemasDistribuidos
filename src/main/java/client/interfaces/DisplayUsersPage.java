package client.interfaces;

import server.dtobject.UserDTO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import java.awt.*;

public class DisplayUsersPage extends JDialog{
    private JComboBox userComboBox;
    private JButton enterButton;
    private JTextField idField;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField typeField;
    private JButton OKButton;
    private JPanel displayUsersPage;

    private UserDTO user;

    public DisplayUsersPage(JFrame parent, List<UserDTO> users){
        super(parent);
        setTitle("Users");
        setContentPane(displayUsersPage);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        for(UserDTO curUser : users){
            userComboBox.addItem(String.valueOf(curUser.registro()));
        }

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                user = users.get(userComboBox.getSelectedIndex());
                nameField.setText(user.nome());
                emailField.setText(user.email());
                typeField.setText(String.valueOf(user.tipo()));
                idField.setText(String.valueOf(user.registro()));
            }
        });
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
        setVisible(true);
    }
}
