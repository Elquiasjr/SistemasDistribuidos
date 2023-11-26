package client.interfaces;

import lombok.Getter;
import protocol.request.RequisitionOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserOptions extends JDialog {
    private JButton adminCreateUserButton;
    private JButton adminSearchUserButton;
    private JButton adminSearchUsersButton;
    private JButton adminUpdateUserButton;
    private JButton createUserButton;
    private JButton adminDeleteUserButton;
    private JButton deleteUserButton;
    private JButton logoutButton;
    private JButton searchUserButton;
    private JButton updateUserButton;
    private JPanel userPage;

    @Getter
    private String operation;

    public UserOptions(JFrame parent){
        super(parent);
        setMinimumSize(new Dimension(500, 500));
        setContentPane(userPage);
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        adminCreateUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                operation = RequisitionOperations.ADMIN_CADASTRAR_USUARIO;
                dispose();
            }
        });
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                operation = RequisitionOperations.CADASTRAR_USUARIO;
                dispose();
            }
        });
        adminSearchUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                operation = RequisitionOperations.ADMIN_BUSCAR_USUARIO;
                dispose();
            }
        });
        adminSearchUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                operation = RequisitionOperations.ADMIN_BUSCAR_USUARIOS;
                dispose();
            }
        });
        searchUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                operation = RequisitionOperations.BUSCAR_USUARIO;
                dispose();
            }
        });
        adminUpdateUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                operation = RequisitionOperations.ADMIN_ATUALIZAR_USUARIO;
                dispose();
            }
        });
        updateUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                operation = RequisitionOperations.ATUALIZAR_USUARIO;
                dispose();
            }
        });
        adminDeleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                operation = RequisitionOperations.ADMIN_DELETAR_USUARIO;
                dispose();
            }
        });
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                operation = RequisitionOperations.DELETAR_USUARIO;
                dispose();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                operation = RequisitionOperations.LOGOUT;
                dispose();
            }
        });
        setVisible(true);
    }
}
