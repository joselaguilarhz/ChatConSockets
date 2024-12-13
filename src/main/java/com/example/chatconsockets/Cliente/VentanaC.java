package com.example.chatconsockets.Cliente;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VentanaC extends Application {
    private TextArea chatArea;
    private TextField messageField;
    private Button sendButton;
    private Cliente cliente;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cliente de Chat");

        // Configuración de los componentes
        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setPromptText("Mensajes del chat aparecerán aquí...");

        messageField = new TextField();
        messageField.setPromptText("Escribe tu mensaje aquí");

        sendButton = new Button("Enviar");
        sendButton.setDisable(true);

        sendButton.setOnAction(event -> enviarMensaje());

        VBox layout = new VBox(10, chatArea, messageField, sendButton);
        layout.setPadding(new Insets(10));
        Scene scene = new Scene(layout, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();

        conectarConServidor();
    }

    private void conectarConServidor() {
        TextInputDialog dialog = new TextInputDialog("localhost");
        dialog.setTitle("Conectar al Servidor");
        dialog.setHeaderText("Introduce la dirección IP del servidor y puerto:");
        dialog.setContentText("IP:Puerto");

        String resultado = dialog.showAndWait().orElse("");
        if (!resultado.isEmpty() && resultado.contains(":")) {
            try {
                String[] partes = resultado.split(":");
                String ip = partes[0];
                int puerto = Integer.parseInt(partes[1]);

                cliente = new Cliente(ip, puerto, chatArea, this);
                cliente.conectar();
                sendButton.setDisable(false);
            } catch (Exception e) {
                mostrarAlerta("Error de conexión", "No se pudo conectar al servidor. Verifica la IP y el puerto.");
            }
        }
    }

    private void enviarMensaje() {
        String mensaje = messageField.getText();
        if (!mensaje.trim().isEmpty()) {
            cliente.enviarMensaje(mensaje);
            messageField.clear();
        }
    }

    public void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
