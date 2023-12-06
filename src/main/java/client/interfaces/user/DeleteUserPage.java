package client.interfaces.user;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteUserPage extends JDialog{
    private JButton doneButton;
    private JTextField emailField;
    private JTextField passwordField;
    private JPanel deleteAccountPage;

    @Getter
    private String userEmail;

    @Getter
    private String userPassword;

    public DeleteUserPage(JFrame parent){
        super(parent);
        setTitle("Delete User");
        setContentPane(deleteAccountPage);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(HIDE_ON_CLOSE);


        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                userEmail = emailField.getText();
                userPassword = passwordField.getText();
                dispose();
            }
        });
        setVisible(true);
    }
}
