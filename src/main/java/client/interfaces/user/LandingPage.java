package client.interfaces.user;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class LandingPage extends JDialog{
    private JButton doneButton;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JPanel initialPage;

    @Getter
    private String email;

    @Getter
    private String password;

    @Getter
    private String operation;

    @SuppressWarnings("deprecation")
    public LandingPage(JFrame parent) {
        super(parent);
        setTitle("Welcome!");
        setContentPane(initialPage);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                 email = emailField.getText();
                 password = passwordField.getText();
//                email = "email@email.com";
//                password = "123456";
                System.out.println("password: " + password);
                operation = "LOGIN";
                dispose();
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                operation = "CADASTRAR_USUARIO";
                dispose();
            }
        });
        setVisible(true);
    }
}
