package client.interfaces.map;

import server.dtobject.segment.SegmentDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplaySegmentPage extends JDialog{
    private JTextField initialPDIField;
    private JTextField finalPDIField;
    private JButton OKButton;
    private JTextField warningField;
    private JTextField acessibleField;
    private JPanel displaySegmentPanel;

    public DisplaySegmentPage(JFrame parent, SegmentDTO segment){
        super(parent);
        setTitle("Segment Display");
        setContentPane(displaySegmentPanel);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        initialPDIField.setText(String.valueOf(segment.pdi_inicial()));
        finalPDIField.setText(String.valueOf(segment.pdi_final()));
        warningField.setText(segment.aviso());
        acessibleField.setText(String.valueOf(segment.acessivel()));


        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
        setVisible(true);
    }
}
