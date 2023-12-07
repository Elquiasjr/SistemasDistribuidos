package client.interfaces.map;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePDIPage extends JDialog{
    private JButton doneButton;
    private JTextField nameField;
    private JTextField warningField;
    private JCheckBox yesCheckBox;
    private JCheckBox noCheckBox;
    private JTextField xField;
    private JTextField yField;
    private JPanel createPDIPanel;
    @Getter
    private String pdiName;
    @Getter
    private Boolean pdiAcessible;
    @Getter
    private String pdiWarning;
    @Getter
    private Double pdiX;
    @Getter
    private Double pdiY;

    public CreatePDIPage(JFrame parent){
        super(parent);
        setTitle("Admin Create PDI");
        setContentPane(createPDIPanel);
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
                pdiName = verify(nameField.getText());
                pdiX = Double.parseDouble(xField.getText());
                pdiY = Double.parseDouble(yField.getText());
                pdiWarning = warningField.getText();
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
