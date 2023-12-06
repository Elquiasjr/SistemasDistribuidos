package client.interfaces.map;

import server.dtobject.pdi.PDIDTO;
import server.dtobject.user.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayPDIPage extends JDialog{
    private JTextField nameField;
    private JTextField idField;
    private JButton okButton;
    private JTextField acessibleField;
    private JTextField warningField;
    private JTextField positionField;
    private JPanel displayPDIPanel;

    public DisplayPDIPage(JFrame parent, PDIDTO pdi){
        super(parent);
        setTitle("PDI Display");
        setContentPane(displayPDIPanel);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        idField.setText(String.valueOf(pdi.id()));
        nameField.setText(pdi.nome());
        positionField.setText("X: " + pdi.posicao().x()+ " Y: "+ pdi.posicao().y());
        warningField.setText(pdi.aviso());
        acessibleField.setText(String.valueOf(pdi.acessivel()));


        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
        setVisible(true);
    }
}
