package fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RecuperarController {

    @FXML
    private Pane RecuperarController;
    @FXML
    private Label lblPreguntaSeguridad;
    @FXML
    private TextField txtRespuestaSeguridad;
    @FXML
    private Button btnAtras;
    @FXML
    private Button actualizarContraseña;
    @FXML
    private TextField confirmarContraseña;
    @FXML
    private TextField nuevaContrseña;

    private Connection connection;
    @FXML
    private Label lblStatus;

    @FXML
    
 private void actualizarContraseñaButtonClicked(ActionEvent event) {
    // Aquí puedes agregar la lógica para actualizar la contraseña

    String respuestaSeguridad = txtRespuestaSeguridad.getText();
    String nuevaContraseña = nuevaContrseña.getText();
    String confirmarContraseñaText = confirmarContraseña.getText(); // Utilizar un nombre de variable diferente

    // Verificar que la respuesta de seguridad y las contraseñas no estén vacías
    if (respuestaSeguridad.isEmpty() || nuevaContraseña.isEmpty() || confirmarContraseñaText.isEmpty()) {
        // Mostrar mensaje de error en tu ventana
        lblStatus.setText("Error: Debes completar todos los campos");
        return;
    }

    // Verificar que las contraseñas coincidan
    if (!nuevaContraseña.equals(confirmarContraseñaText)) {
        // Mostrar mensaje de error en tu ventana
        lblStatus.setText("Error: Las contraseñas no coinciden");
        return;
    }

    // Verificar que la contraseña cumpla con los requisitos
    if (nuevaContraseña.length() < 5 || nuevaContraseña.length() > 8
            || !nuevaContraseña.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{5,8}$")) {
        // Mostrar mensaje de error en tu ventana
        lblStatus.setText("Error: La contraseña debe tener entre 5 y 8 caracteres, contener letras, números y ambas mayúsculas y minúsculas");
        return;
    }

    // Verificar la respuesta de seguridad
    if (!verificarRespuestaSeguridad(respuestaSeguridad)) {
        // Mostrar mensaje de error en tu ventana
        lblStatus.setText("Error: La respuesta de seguridad es incorrecta");
        return;
    }

    // La respuesta de seguridad y las contraseñas son válidas, proceder con la actualización
    // Aquí puedes realizar la lógica de actualización de la contraseña

    // Mostrar mensaje de éxito en tu ventana
    lblStatus.setText("Contraseña actualizada exitosamente");
}

// Método para verificar la respuesta de seguridad
private boolean verificarRespuestaSeguridad(String respuestaSeguridad) {
    String respuestaCorrecta = "respuesta correcta";
   
    return respuestaSeguridad.trim().equalsIgnoreCase(respuestaCorrecta.trim());

}
    @FXML
    private void handleAtrasAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Ingreso.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejo de errores al cargar la pantalla de inicio de sesión
        }
    }

    // Método para establecer la conexión a la base de datos
    public void establecerConexion() {
    try {
        // Establecer la conexión con la base de datos
        connection = DriverManager.getConnection("jdbc:mysql://localhost/personas", "root", "123456");
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    }
}
