package client.interfaces;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateUserPage extends JDialog{
    private JTextField name;
    private JTextField email;
    private JTextField password;
    private JButton doneButton;
    private JPanel createUser;

    @Getter
    private String userName;

    @Getter
    private String userEmail;

    @Getter
    private String userPassword;


    public CreateUserPage(JFrame parent){
        super(parent);
        setTitle("Create User");
        setContentPane(createUser);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(HIDE_ON_CLOSE);


        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                userName = name.getText();
                userEmail = email.getText();
                userPassword = password.getText();
                dispose();
            }
        });
        setVisible(true);
    }
}
