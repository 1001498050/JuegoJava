package fxml;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class Nivel2 {

    
    @FXML
    private Label tiempoLabel;

    @FXML
    private Label questionLabel;
    
    @FXML
    private RadioButton optionARadioButton;

    @FXML
    private RadioButton optionBRadioButton;

    @FXML
    private RadioButton optionCRadioButton;

    @FXML
    private RadioButton optionDRadioButton;

    private ToggleGroup toggleGroup;
    private int currentQuestion = 1;
    private int score = 15;
    private Timeline timeline;
    private boolean retryAttempted = false;

    private final String[] questions = {
        "¿Quién fue el líder político y fundador del partido liberal que desempeñó un papel importante en la historia de Colombia en el siglo XX?",
    "¿Cuál es el nombre del famoso cañón situado en el departamento de Santander, conocido como uno de los más profundos del mundo?",
    "¿Cuál fue el tratado firmado en 1903 que permitió la separación de Panamá de Colombia y la creación del Canal de Panamá?",
    "¿Cuál fue el presidente colombiano que gobernó durante el período conocido como la 'Concordancia' en la década de 1950 y que implementó políticas de desarrollo económico y modernización del país?",
    "¿Cuál es el punto más alto de Colombia?",
    "¿Quién fue la reconocida pintora y escultora colombiana, considerada una de las principales representantes del arte contemporáneo en América Latina?",
    "¿Cuál es la obra literaria del escritor colombiano José Eustasio Rivera, considerada una de las más importantes de la literatura colombiana y latinoamericana?",
    "¿En qué año se culminó la expedición botánica en Colombia, liderada por José Celestino Mutis?",
    "¿Con cuántos países comparte Colombia fronteras terrestres?",
    "¿Cuál es la etnia indígena más numerosa de Colombia?",
    "¿Cuántas lenguas indígenas hay en Colombia?",
    "¿Cuál es el nombre que recibió el fenómeno de un ruido extraño e indeterminable que sacudió y espantó el 9 de marzo de 1687 en la entonces villa de Santafé de Bogotá, Colombia?",
    "¿En qué año los jesuitas trajeron una imprenta a Bogotá?",
    "¿Cuándo se fundó la ciudad de Bogotá?"
    };
    
private final String[][] options = {
    {"a) Jorge Eliécer Gaitán", "b) Alfonso López Pumarejo", "c) Rafael Uribe Uribe", "d) Carlos Lleras Restrepo"},
    {"a) Cañón del Chicamocha", "b) Cañón del Sumapaz", "c) Cañón del Combeima", "d) Cañón del Quindío"},
    {"a) Tratado de Versalles", "b) Tratado de París", "c) Tratado de Tordesillas", "d) Tratado Hay-Bunau Varilla"},
    {"a) Gustavo Rojas Pinilla", "b) Alberto Lleras Camargo", "c) Mariano Ospina Pérez", "d) Laureano Gómez"},
    {"a) Nevado del Ruiz", "b) Pico Cristóbal Colón", "c) Pico Simón Bolívar", "d) Cerro Tatamá"},
    {"a) Frida Kahlo", "b) Remedios Varo", "c) Doris Salcedo", "d) Beatriz González"},
    {"a) Cien años de soledad", "b) El coronel no tiene quien le escriba", "c) María", "d) La vorágine"},
    {"a) 1800", "b) 1820", "c) 1840", "d) 1860"},
    {"a) 2", "b) 3", "c) 4", "d) 5"},
    {"a. Wayuu", "b. Emberá.", "c. Sikuani.", "d) uitoto"},
    {"a) 60", "b) 63", "c) 65", "d) 68"},
    {"a) El Tiempo del Ruido", "b) El Fenómeno Sonoro", "c) El Ruido Inexplicable", "d) La Conmoción de Santafé"},
    {"a) 1738", "b) 1810", "c) 1783", "d) 1689"},
    {"a. El 6 de agosto de 1538.", "b. El 8 de octubre de 1558.", "c. El 29 de julio de 1525.", "d) El 7 de agosto de 1583"}
};
private final String[] correctAnswers = {
    "a) Jorge Eliécer Gaitán",
    "a) Cañón del Chicamocha",
    "d) Tratado Hay-Bunau Varilla",
    "b) Alberto Lleras Camargo",
    "b) Pico Cristóbal Colón",
    "d) Beatriz González",
    "d) La vorágine",
    "a) 1800",
    "d) 5",
    "a. Wayuu",
    "c) 65",
    "a) El Tiempo del Ruido",
    "a) 1738",
    "a. El 6 de agosto de 1538."
};
 public void initialize() {
        toggleGroup = new ToggleGroup();
        optionARadioButton.setToggleGroup(toggleGroup);
        optionBRadioButton.setToggleGroup(toggleGroup);
        optionCRadioButton.setToggleGroup(toggleGroup);
        optionDRadioButton.setToggleGroup(toggleGroup);

        showQuestion(currentQuestion);
    }

    @FXML
    public void submitAnswer() {
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            String answer = selectedRadioButton.getText();
            checkAnswer(answer);
            if (score >= 0 && currentQuestion < 13) {
                currentQuestion++;
                showQuestion(currentQuestion);
                toggleGroup.selectToggle(null);
            } else {
                endGame();
            }
        }
    }

    private void showQuestion(int questionNumber) {
        questionLabel.setText("Pregunta " + questionNumber + ":");
        if (score >= 0) {
            setQuestionOptions(questionNumber - 1);
        } else {
            endGame();
        }

        // Reiniciar el temporizador
        if (timeline != null) {
            timeline.stop();
        }
        iniciarTemporizador();
    }

    private void setQuestionOptions(int index) {
        questionLabel.setText(questions[index]);
        optionARadioButton.setText(options[index][0]);
        optionBRadioButton.setText(options[index][1]);
        optionCRadioButton.setText(options[index][2]);
        optionDRadioButton.setText(options[index][3]);
    }

    private void iniciarTemporizador() {
        final Integer tiempoInicial = 10;
        final Label tiempoRestanteLabel = new Label(tiempoInicial.toString());
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), event -> {
                    tiempoRestanteLabel.setText(String.valueOf(Integer.parseInt(tiempoRestanteLabel.getText()) - 1));
                    tiempoLabel.setText(tiempoRestanteLabel.getText());  // Actualizar el Label del tiempo restante
                    if (Integer.parseInt(tiempoRestanteLabel.getText()) <= 0) {
                        timeline.stop();
                        mostrarSiguientePregunta();
                    }
                })
        );
        timeline.playFromStart();
    }

    private void mostrarSiguientePregunta() {
        if (currentQuestion < 13) {
            currentQuestion++;
            showQuestion(currentQuestion);
            toggleGroup.selectToggle(null);
        } else {
            endGame();
        }
    }

    private void checkAnswer(String answer) {
        int questionIndex = currentQuestion - 1;
        if (answer.equals(correctAnswers[questionIndex])) {
            score += 15;// Sumar 10 puntos por respuesta correcta
        } else {
            score -= 15; // Restar 10 puntos por respuesta incorrecta
        }
    }

    private void endGame() {
        if (score == 130) {
            showCongratulationsMessage();
        } else {
            showRetryMessage();
        }
    }

    private void showCongratulationsMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Felicitaciones");
        alert.setHeaderText("¡Eres todo un Guerrero, Haz salvado a tu patria!");
        
        // Reiniciar el juego para permitir jugar el nivel 2 nuevamente
        resetGame();

        alert.showAndWait();
    }

    private void showRetryMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Lo Lamento");
        alert.setHeaderText("no tuviste suerte");
        alert.setContentText("No te rindas, los verdaderos valientes lo intentan hasta lograrlo.");

        ButtonType retryButton = new ButtonType("Reintentar");
        ButtonType menuButton = new ButtonType("Regresar al Menú");

        if (!retryAttempted) {
            alert.getButtonTypes().setAll(retryButton, menuButton);
        } else {
            alert.getButtonTypes().setAll(menuButton);
        }

        // Mostrar el mensaje de alerta y esperar a que se seleccione una opción
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == retryButton) {
                retryAttempted = true;
                resetGame();
            } else if (result.get() == menuButton) {
                goBackToMenu();
            }
        }
    }

    private void resetGame() {
        currentQuestion = 1;
        score = 15;
        toggleGroup.selectToggle(null);
        showQuestion(currentQuestion);
    }

    private void goBackToMenu() {
        Stage stage = (Stage) tiempoLabel.getScene().getWindow();
        stage.close(); // Cerrar la ventana actual

        // Abrir la ventana del menú principal
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Menú");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
    

