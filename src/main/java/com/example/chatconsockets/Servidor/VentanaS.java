package com.example.chatconsockets.Servidor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VentanaS extends Application {
    private TextArea logTextArea;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Servidor de Chat");

        logTextArea = new TextArea();
        logTextArea.setEditable(false);
        logTextArea.setPromptText("Aquí se registrarán las conexiones y mensajes...");

        VBox layout = new VBox(10, logTextArea);
        Scene scene = new Scene(layout, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();

        // Llamar a la clase Servidor para iniciar el servidor
        new Thread(() -> {
            Servidor servidor = new Servidor(12345, logTextArea);
            servidor.iniciar();
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
