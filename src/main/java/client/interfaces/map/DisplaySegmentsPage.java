package client.interfaces.map;

import server.dtobject.segment.SegmentDTO;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplaySegmentsPage extends JDialog{
    private JTextField acessibleField;
    private JTextField warningField;
    private JTextField finalPDIField;
    private JTextField initialPDIField;
    private JButton OKButton;

    private JPanel displaySegmentsPanel;
    private JButton subButton;
    private JButton addButton;

    private int i = 0;
    public DisplaySegmentsPage(JFrame parent, List<SegmentDTO> segments){
        super(parent);
        setTitle("Segments Display");
        setContentPane(displaySegmentsPanel);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        subButton.setSize(150,100);
        addButton.setSize(150,100);
        if(segments.isEmpty()){
            subButton.setVisible(false);
            addButton.setVisible(false);
        }else{
            setSegment(segments.get(i));
        }

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

        subButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if((i - 1) < 0){
                    i = segments.size()-1;
                }else{
                    i = i - 1;
                }
                setSegment(segments.get(i));
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if((i + 1) > segments.size() - 1){
                    i = 0;
                }else{
                    i = i + 1;
                }
                setSegment(segments.get(i));
            }
        });
        setVisible(true);
    }
    public void setSegment(SegmentDTO segment){
        initialPDIField.setText(String.valueOf(segment.pdi_inicial()));
        finalPDIField.setText(String.valueOf(segment.pdi_final()));
        warningField.setText(segment.aviso());
        acessibleField.setText(String.valueOf(segment.acessivel()));
    }
}
