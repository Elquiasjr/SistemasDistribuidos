package client.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Login{
    public Login(){

    }
    @FXML
    private Button button;
    @FXML
    private Label wrongLogin;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;

    public void userLogin(ActionEvent event) throws IOException {
        checkLogin();
    }

    private void checkLogin() throws IOException {
        Main m = new Main();
        if(email.getText().equals("email@email") && password.getText().equals("123456")){
            wrongLogin.setText("Sucess!");

            m.changeScene("afterLogin.fxml");
        }else if(email.getText().isEmpty() && password.getText().isEmpty()){
            wrongLogin.setText("Please enter your data.");
        }else{
            wrongLogin.setText("Wrong email");
        }
    }
}
