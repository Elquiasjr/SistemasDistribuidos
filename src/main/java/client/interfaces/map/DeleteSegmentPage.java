package client.interfaces.map;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteSegmentPage extends JDialog {
    private JTextField finalPDIField;
    private JButton doneButton;
    private JPanel deleteSegmentPanel;
    private JTextField initialPDIField;

    @Getter
    private Long initialPDI;
    @Getter
    private Long finalPDI;

    public DeleteSegmentPage(JFrame parent) {
        super(parent);
        setTitle("Admin Create PDIr");
        setContentPane(deleteSegmentPanel);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                initialPDI = Long.parseLong(initialPDIField.getText());
                finalPDI = Long.parseLong(finalPDIField.getText());
                dispose();
            }
        });
        setVisible(true);
    }
}
