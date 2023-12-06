package client.interfaces.map;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatePDIPage extends JDialog{
    private JTextField idField;
    private JTextField nameField;
    private JButton doneButton;
    private JTextField warningField;
    private JCheckBox yesCheckBox;
    private JCheckBox noCheckBox;
    private JPanel updatePDIPanel;
    @Getter
    private Long pdiId;

    @Getter
    private String pdiName;
    @Getter
    private Boolean pdiAcessible;
    @Getter
    private String pdiWarning;

    public UpdatePDIPage(JFrame parent){
        super(parent);
        setTitle("Admin Create PDIr");
        setContentPane(updatePDIPanel);
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
                pdiId = Long.parseLong(idField.getText());
                pdiName = verify(nameField.getText());
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
