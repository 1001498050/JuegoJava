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

public class VentanaJuegoController {

    
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
    private int puntos;
    private Timeline timeline;
    private boolean retryAttempted = false;

    private final String[] questions = {
            "¿Cómo se conoce el nuevo tipo de organización social que surgió en las comunidades indígenas de Colombia durante la época precolombina?",
            "Algunas de las culturas indígenas precolombinas conformadas por varios grupos son:",
            "¿Cuál fue la batalla decisiva que aseguró la independencia de Colombia?",
            "¿Cuál es el nombre del famoso archipiélago colombiano ubicado en el mar Caribe, conocido por sus playas de arena blanca y su biodiversidad marina?",
            "¿Cuál fue el nombre del territorio que se creó después de la independencia de Colombia?",
            "¿Cuántas divisiones político-administrativas tiene la República de Colombia?",
            "¿Cuál fue el primer presidente de la Gran Colombia?",
            "¿Cuál es la región más poblada de Colombia?",
            "¿Cuál es el río más importante de Colombia?",
            "¿Quién es considerado el escritor más famoso de Colombia?",
            "¿Cuál es el famoso festival de música que se celebra en Cartagena, Colombia?",
            "¿Cuál es el volcán más alto de Colombia?",
            "¿Cuál es la bebida tradicional de Colombia hecha a base de aguardiente de caña y azúcar?"
    };

    private final String[][] options = {
            {"a) Tribalismo", "b) Cacicazgo", "c) Feudalismo", "d) Monarquía"},
            {"a) Cultura de Nariño, Cultura de Tierradentro y los Pijaos", "b) Cultura Maya, Cultura Azteca y Cultura Inca", "c) Cultura Griega, Cultura Egipcia y Cultura Romana", "d) Cultura China, Cultura Japonesa y Cultura India"},
            {"a) Batalla de Carabobo", "b) Batalla de Boyacá", "c) Batalla de Ayacucho", "d) Batalla de Pichincha"},
            {"a) Islas Malpelo", "b) Islas del Rosario", "c) Islas de San Andrés, Providencia y Santa Catalina", "d) Islas Gorgona"},
            {"a) Gran Colombia", "b) República de Bolívar", "c) Confederación de los Andes", "d) Estado Libre de Colombia"},
            {"a. 23 estados y un distrito federal.", "b. 32 departamentos y un distrito capital.", "c. 50 provincias y una capital autónoma.", "d) 32 provincias y un distrito capital"},
            {"a) Simón Bolívar", "b) Francisco de Paula Santander", "c) Antonio Nariño", "d) Rafael Urdaneta"},
            {"a) Región Andina", "b) Región Caribe", "c) Región Pacífica", "d) Región Orinoquía"},
            {"a) Río Magdalena", "b) Río Cauca", "c) Río Amazonas", "d) Río Orinoco"},
            {"a) Gabriel García Márquez", "b) Fernando Vallejo", "c) Laura Restrepo", "d) Juan Gabriel Vásquez"},
            {"a) Festival de la Leyenda Vallenata", "b) Festival Internacional de Jazz de Mompox", "c) Festival de la Independencia de Cartagena", "d) Festival Internacional de Música de Cartagena"},
            {"a) Nevado del Huila", "b) Nevado del Tolima", "c) Nevado del Ruiz", "d) Nevado del Cocuy"},
            {"a) Chicha", "b) Avena colombiana", "c) Canelazo", "d) Aguardiente"}
    };

    private final String[] correctAnswers = {
            "b) Cacicazgo",
            "a) Cultura de Nariño, Cultura de Tierradentro y los Pijaos",
            "b) Batalla de Boyacá",
            "c) Islas de San Andrés, Providencia y Santa Catalina",
            "a) Gran Colombia",
            "b) 32 departamentos y un distrito capital.",
            "a) Simón Bolívar",
            "a) Región Andina",
            "a) Río Magdalena",
            "a) Gabriel García Márquez",
            "d) Festival Internacional de Música de Cartagena",
            "a) Nevado del Huila",
            "d) Aguardiente"
    };

    public void initialize() {
    toggleGroup = new ToggleGroup();
    optionARadioButton.setToggleGroup(toggleGroup);
    optionBRadioButton.setToggleGroup(toggleGroup);
    optionCRadioButton.setToggleGroup(toggleGroup);
    optionDRadioButton.setToggleGroup(toggleGroup);

    showQuestion(currentQuestion);
    
    
    puntos = score; 
    }// Agregar esta línea para inicializar correctamente la variable puntos
     public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    @FXML
   public void submitAnswer() {
    RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
    if (selectedRadioButton != null) {
        String answer = selectedRadioButton.getText();
        checkAnswer(answer);
        if (score >= 0 && currentQuestion < 13) {
            currentQuestion++;
            toggleGroup.selectToggle(null);
            showQuestion(currentQuestion);
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
        final Integer tiempoInicial = 15;
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
            score += 10;  // Sumar 10 puntos por respuesta correcta
        } else {
            score -= 10;  // Restar 10 puntos por respuesta incorrecta
            puntos = score;

        }
    }

    private void endGame() {
    if (score >= 130) {
        showCongratulationsMessage();
    } else {
        showRetryMessage();
    }

    }

    private void showCongratulationsMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Felicitaciones");
        alert.setHeaderText("¡Haz demostrado tu compromiso con la Misión!");
        alert.setContentText("Ahora eres una ficha clave en lograr la independencia junto con Simón Bolívar.\n\n" +
                "Bienvenido al nivel dos, donde disfrutarás de una aventura mágica.");

        // Reiniciar el juego para permitir jugar el nivel 2 nuevamente
        resetGame();

        alert.showAndWait();
    }

    private void showRetryMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Lo siento");
        alert.setHeaderText("Por Hoy no fue");
        alert.setContentText("¡No te rindas!, los verdaderos Guerreros lo intentan hasta lograrlo.");

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
            primaryStage.setTitle("Menu");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
    
   
















