package com.example.chatconsockets.Cliente;

import javafx.scene.control.TextArea;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;

public class Cliente {
    private String ip;
    private int puerto;
    private TextArea chatArea;
    private VentanaC ventanaC;
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;

    public Cliente(String ip, int puerto, TextArea chatArea, VentanaC ventanaC) {
        this.ip = ip;
        this.puerto = puerto;
        this.chatArea = chatArea;
        this.ventanaC = ventanaC;
    }

    public void conectar() {
        try {
            socket = new Socket(ip, puerto);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);

            // Solicitar nombre de usuario
          //  salida.println(ventanaC.mostrarNombreUsuarioDialogo());
            chatArea.appendText("Conectado al servidor.\n");

            // Hilo para escuchar mensajes del servidor
            new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = entrada.readLine()) != null) {
                        String finalMensaje = mensaje;
                        Platform.runLater(() -> chatArea.appendText(finalMensaje + "\n"));
                    }
                } catch (IOException e) {
                    Platform.runLater(() -> chatArea.appendText("Conexión perdida con el servidor.\n"));
                } finally {
                    cerrarConexiones();
                }
            }).start();

        } catch (IOException e) {
            ventanaC.mostrarAlerta("Error", "No se pudo conectar al servidor.");
        }
    }

    public void enviarMensaje(String mensaje) {
        if (salida != null) {
            salida.println(mensaje);
        }
    }

    private void cerrarConexiones() {
        try {
            if (socket != null) socket.close();
            if (entrada != null) entrada.close();
            if (salida != null) salida.close();
        } catch (IOException e) {
            // Ignorar errores al cerrar
        }
    }
}
