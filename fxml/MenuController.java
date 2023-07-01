package fxml;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;


public class MenuController {
    
    private int puntos;
    public void setPuntos(int puntos) {
    this.puntos = puntos;
    }

    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;
    @FXML
    private Button nivel1Button;
    @FXML
    private Button nivel2Button;
   
    @FXML
    private void jugar(ActionEvent event) {
        bp.setCenter(ap);
    }

    @FXML
    private void ajustes(ActionEvent event) {
         loadPage("ajustes");
    }

    @FXML
    private void biblioteca(ActionEvent event) {
         loadPage("biblioteca");
    }

    @FXML
    private void salir(ActionEvent event) {
    Stage stage = (Stage) bp.getScene().getWindow();
    closeGame(stage);
    }


    private void loadPage(String page) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(page + ".fxml"));
            bp.setCenter(root);
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void closeGame(Stage stage) {
    if (stage != null) {
        stage.close();
    }
}

    @FXML
    private void handleNivel1ButtonAction(ActionEvent event) {
        
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaJuego.fxml"));
        Parent preguntasNivel1Root = loader.load();
        Scene preguntasNivel1Scene = new Scene(preguntasNivel1Root);
        
        Stage primaryStage = (Stage) nivel1Button.getScene().getWindow();
        primaryStage.setScene(preguntasNivel1Scene);
        primaryStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    @FXML
    private void handleNivel2ButtonAction(ActionEvent event) {
    if (puntos >= 130) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaJuego.fxml"));
        Parent preguntasNivel2Root = loader.load();
        Scene preguntasNivel2Scene = new Scene(preguntasNivel2Root);

        Stage primaryStage = (Stage) nivel2Button.getScene().getWindow();
        primaryStage.setScene(preguntasNivel2Scene);
        primaryStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
} else {
    // Nivel 2 bloqueado
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Nivel 2 Bloqueado");
    alert.setHeaderText(null);
    alert.setContentText("El Nivel 2 está bloqueado. Debes alcanzar 130 puntos para acceder a este nivel.");
    alert.showAndWait();
}
    }
}
    
    
