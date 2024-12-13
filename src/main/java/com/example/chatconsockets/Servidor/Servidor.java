package com.example.chatconsockets.Servidor;

import javafx.scene.control.TextArea;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    private int puerto;
    private TextArea logTextArea;
    private List<HiloCliente> clientesConectados;

    public Servidor(int puerto, TextArea logTextArea) {
        this.puerto = puerto;
        this.logTextArea = logTextArea;
        this.clientesConectados = new ArrayList<>();
    }

    public void iniciar() {
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            log("Servidor iniciado en el puerto " + puerto);

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                log("Nuevo cliente conectado: " + clienteSocket.getInetAddress());
                HiloCliente nuevoCliente = new HiloCliente(clienteSocket, this);
                clientesConectados.add(nuevoCliente);
                new Thread(nuevoCliente).start();
            }
        } catch (IOException e) {
            log("Error en el servidor: " + e.getMessage());
        }
    }

    public void log(String mensaje) {
        logTextArea.appendText(mensaje + "\n");
    }

    public void difundirMensaje(String mensaje, HiloCliente remitente) {
        for (HiloCliente cliente : clientesConectados) {
            if (cliente != remitente) {
                cliente.enviarMensaje(mensaje);
            }
        }
    }

    public void eliminarCliente(HiloCliente cliente) {
        clientesConectados.remove(cliente);
        log("Cliente desconectado: " + cliente.getNombreUsuario());
    }
}
