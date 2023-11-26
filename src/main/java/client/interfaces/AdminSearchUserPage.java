package client.interfaces;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminSearchUserPage extends JDialog{
    private JTextField idField;
    private JButton doneButton;
    private JPanel adminSUPage;

    @Getter
    private Long id;

    public AdminSearchUserPage(JFrame parent){
        super(parent);
        setTitle("Server Connection");
        setContentPane(adminSUPage);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            id = Long.parseLong(idField.getText());
            dispose();
            }
        });
        setVisible(true);
    }
}
