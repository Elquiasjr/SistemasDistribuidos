package client.interfaces.map;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeletePDIPage extends JDialog {
    private JTextField idField;
    private JButton doneButton;
    private JPanel deletePDIPanel;
    @Getter
    private Long idPDI;

    public DeletePDIPage(JFrame parent) {
        super(parent);
        setTitle("Admin Create PDIr");
        setContentPane(deletePDIPanel);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                idPDI = Long.parseLong(idField.getText());
                dispose();
            }
        });
        setVisible(true);
    }
}

