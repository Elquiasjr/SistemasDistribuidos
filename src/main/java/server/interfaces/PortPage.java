package server.interfaces;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PortPage extends JDialog {
    private JButton OKButton;
    private JTextField portField;
    private JPanel portPage;

    @Getter
    private Integer port;

    public PortPage(JFrame parent){
        super(parent);
        setTitle("Port Selection");
        setContentPane(portPage);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                port = Integer.valueOf(portField.getText());
                dispose();
            }
        });
        setVisible(true);
    }
}
