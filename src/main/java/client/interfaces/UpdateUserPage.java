package client.interfaces;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateUserPage extends JDialog{
    private JTextField nameField;
    private JTextField emailField;
    private JTextField passwordField;
    private JButton doneButton;
    private JPanel updateUserPage;

    @Getter
    private String userName;

    @Getter
    private String userEmail;

    @Getter
    private String userPassword;

    public UpdateUserPage(JFrame parent){
        super(parent);
        setContentPane(updateUserPage);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                userName = verify(nameField.getText().trim());
                userEmail = verify(emailField.getText());
                userPassword = verify(passwordField.getText());
                dispose();
            }
        });
        setVisible(true);
    }

    private String verify(String field) {
        if(field.isBlank() || field.isEmpty()) {
            field = null;
        }
        return field;
    }
}
