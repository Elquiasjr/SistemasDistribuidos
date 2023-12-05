package client.interfaces;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDeleteUserPage extends JDialog{
    private JTextField IDField;
    private JButton doneButton;
    private JPanel adminDUPage;

    @Getter
    private Long id;

    public AdminDeleteUserPage(JFrame parent){
        super(parent);
        setTitle("Server Connection");
        setContentPane(adminDUPage);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                id = Long.parseLong(IDField.getText());
                dispose();
            }
        });
        setVisible(true);
    }
}
