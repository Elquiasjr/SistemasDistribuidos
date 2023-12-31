package client.interfaces.map;

import lombok.Getter;
import org.hibernate.boot.jaxb.mapping.JaxbDatabaseObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateSegmentPage extends JDialog {

    private JTextField initialPDIField;
    private JTextField finalPDIField;
    private JButton doneButton;
    private JTextField warningField;
    private JCheckBox yesCheckBox;
    private JCheckBox noCheckBox;
    private JPanel updateSegmentPanel;

    @Getter
    private Long initialPDI;
    @Getter
    private Long finalPDI;
    @Getter
    private Boolean pdiAcessible;
    @Getter
    private String pdiWarning;


    public UpdateSegmentPage(JFrame parent){
        super(parent);
        setTitle("Admin Update Segment");
        setContentPane(updateSegmentPanel);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        ButtonGroup bg = new ButtonGroup();
        bg.add(yesCheckBox);
        bg.add(noCheckBox);
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                initialPDI = Long.parseLong(initialPDIField.getText());
                finalPDI = Long.parseLong(finalPDIField.getText());
                pdiWarning = verify(warningField.getText());
                if(!noCheckBox.isSelected() && !yesCheckBox.isSelected()){
                    pdiAcessible = null;
                }
                if(noCheckBox.isSelected()){
                    pdiAcessible = false;
                }
                if(yesCheckBox.isSelected()){
                    pdiAcessible = true;
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
