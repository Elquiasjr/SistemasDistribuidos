package client.interfaces.map;

import server.dtobject.pdi.PDIDTO;
import server.dtobject.user.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DisplayPDIsPage extends JDialog{
    private JTextField acessibleField;
    private JTextField warningField;
    private JTextField positionField;
    private JTextField nameField;
    private JComboBox idComboBox;
    private JButton enterButton;
    private JButton OKButton;
    private JTextField idField;
    private JPanel displayPDIsPanel;

    private PDIDTO pdi;

    public DisplayPDIsPage(JFrame parent, List<PDIDTO> pdis){
        super(parent);
        setTitle("PDIs");
        setContentPane(displayPDIsPanel);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        for(PDIDTO curPDI : pdis){
            idComboBox.addItem(String.valueOf(curPDI.id()));
        }

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                pdi = pdis.get(idComboBox.getSelectedIndex());

                idField.setText(String.valueOf(pdi.id()));
                nameField.setText(pdi.nome());
                positionField.setText("X: " + pdi.posicao().x()+ " Y: "+ pdi.posicao().y());
                warningField.setText(pdi.aviso());
                acessibleField.setText(String.valueOf(pdi.acessivel()));
            }
        });
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
        setVisible(true);
    }
}
