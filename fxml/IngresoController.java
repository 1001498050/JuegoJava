
package fxml;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Conexion.ConexionUtil;

public class IngresoController implements Initializable {

    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtContraseña;
    @FXML
    private Button btnSesion;
    @FXML
    private Button btnRecuperar;
    @FXML
    private Button btnRegistrarse;
    @FXML
    private Label lblErrors;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        con = ConexionUtil.conDB();
        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Error del servidor: Comprobar");
        } else {
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("El servidor está activo: listo");
        }
    }

    @FXML
    private void handleRegistrarseAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Registro.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            // Cerrar la ventana actual (ventana de inicio de sesión)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSesionAction(ActionEvent event) {
        String loginStatus = logIn();
        if (loginStatus.equals("Success")) {
            // Inicio de sesión exitoso, redirigir a la siguiente ventana (por ejemplo, el menú principal)
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleRecuperarAction(ActionEvent event) {
        if (event.getSource() == btnRecuperar) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Recuperar.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

                // Cerrar la ventana actual (ventana de inicio de sesión)
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String logIn() {
        String status = "Success";
        String Usuario = txtUsuario.getText();
        String Contraseña = txtContraseña.getText();
        if (Usuario.isEmpty() || Contraseña.isEmpty()) {
            setLblError(Color.TOMATO, "Credenciales vacías");
            status = "Error";
        } else {
            String sql = "SELECT * FROM personas WHERE Correo = ? AND Contraseña = ?";
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, Usuario);
                preparedStatement.setString(2, Contraseña);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    setLblError(Color.TOMATO, "Ingrese el correo electrónico/contraseña correctos");
                    status = "Error";
                } else {
                    setLblError(Color.GREEN, "Inicio de sesión exitoso... Redirigir...");
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }

        return status;
    }

    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }
}

   


   







    

    
        
    

    

   
    

   


    
  


