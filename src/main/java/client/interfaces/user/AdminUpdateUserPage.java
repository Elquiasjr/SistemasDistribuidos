package client.interfaces.user;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUpdateUserPage extends JDialog{
    private JTextField nameField;
    private JTextField emailField;
    private JTextField passwordField;
    private JButton doneButton;
    private JCheckBox falseCheckBox;
    private JCheckBox trueCheckBox;
    private JPanel adminUSPage;
    private JTextField idField;

    @Getter
    private String userName;

    @Getter
    private String userEmail;

    @Getter
    private String userPassword;
    
    @Getter 
    private Long userID;

    @Getter
    private Boolean userType;


    public AdminUpdateUserPage(JFrame parent){
        super(parent);
        setTitle("Admin Update User");
        setContentPane(adminUSPage);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        ButtonGroup bg = new ButtonGroup();
        bg.add(falseCheckBox);
        bg.add(trueCheckBox);
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                userName = verify(nameField.getText());
                userEmail = verify(emailField.getText());
                userPassword = verify(passwordField.getText());
                userID = Long.parseLong(idField.getText());
                if(!falseCheckBox.isSelected() && !trueCheckBox.isSelected()){
                    userType = null;
                }
                if(falseCheckBox.isSelected()){
                    userType = false;
                }
                if(trueCheckBox.isSelected()){
                    userType = true;
                }
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
