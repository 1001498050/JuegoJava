package fxml;

import Conexion.ConexionUtil;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RegistroController implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtContraseña;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnVolverInicio;
    @FXML
private TextField txtPreguntaSeguridad;
    @FXML
    private TextField txtRespuestaSeguridad;
    @FXML
    private Label lblStatus;

    private PreparedStatement preparedStatement;
    private Connection connection;

    public RegistroController() {
        connection = ConexionUtil.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void handleguardarAction(ActionEvent event) {
        if (txtCorreo.getText().isEmpty() || txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty()
                || txtContraseña.getText().isEmpty() || txtPreguntaSeguridad.getText().isEmpty()) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Ingrese todos los detalles");
        } else {
            guardar();
        }
    }

    @FXML
    private void handleVolverInicioAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Ingreso.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            // Cerrar la ventana actual (ventana de registro)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRecuperarContraseña(ActionEvent event) {
        String correo = txtCorreo.getText();
        String preguntaSeguridad = txtPreguntaSeguridad.getText();
        String respuestaSeguridad = txtRespuestaSeguridad.getText();

        if (correo.isEmpty() || preguntaSeguridad.isEmpty() || respuestaSeguridad.isEmpty()) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Ingrese todos los detalles para recuperar la contraseña");
        } else {
            recuperarContraseña(correo, preguntaSeguridad, respuestaSeguridad);
        }
    }

    private void clearFields() {
        txtNombre.clear();
        txtApellido.clear();
        txtCorreo.clear();
        txtContraseña.clear();
        txtPreguntaSeguridad.clear();
        txtRespuestaSeguridad.clear();
    }

    private void guardar() {
        try {
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String correo = txtCorreo.getText();
            String contraseña = txtContraseña.getText();
            String preguntaSeguridad = txtPreguntaSeguridad.getText();
            String respuestaSeguridad = txtRespuestaSeguridad.getText();

            // Verificar que la contraseña cumpla con los requisitos
            if (contraseña.length() < 5 || contraseña.length() > 8
                    || !contraseña.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{5,8}$")) {
                lblStatus.setTextFill(Color.TOMATO);
                lblStatus.setText("La contraseña debe tener entre 5 y 8 caracteres, contener letras, números y ambas mayúsculas y minúsculas");
                return;
            }

            String sql = "INSERT INTO personas (Nombre, Apellido, Correo, Contraseña, PreguntaSeguridad, RespuestaSeguridad) VALUES (?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            preparedStatement.setString(3, correo);
            preparedStatement.setString(4, contraseña);
            preparedStatement.setString(5, preguntaSeguridad);
            preparedStatement.setString(6, respuestaSeguridad);

            preparedStatement.executeUpdate();
            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Agregado exitosamente");

            clearFields();

            // Redirigir a la ventana de inicio de sesión
            redireccionarInicioSesion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText(ex.getMessage());
        }
    }

    private void recuperarContraseña(String correo, String preguntaSeguridad, String respuestaSeguridad) {
        try {
            String sql = "SELECT Contraseña FROM personas WHERE Correo = ? AND PreguntaSeguridad = ? AND RespuestaSeguridad = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, correo);
            preparedStatement.setString(2, preguntaSeguridad);
            preparedStatement.setString(3, respuestaSeguridad);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String contraseña = resultSet.getString("Contraseña");
                lblStatus.setTextFill(Color.GREEN);
                lblStatus.setText("Tu contraseña es: " + contraseña);
            } else {
                lblStatus.setTextFill(Color.TOMATO);
                lblStatus.setText("No se encontró ninguna cuenta con los detalles proporcionados");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText(ex.getMessage());
        }
    }

    private void redireccionarInicioSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Ingreso.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            // Cerrar la ventana actual (ventana de registro)
            Stage currentStage = (Stage) btnGuardar.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
