package com.example.chatconsockets.Servidor;

import java.io.*;
import java.net.Socket;

public class HiloCliente implements Runnable {
    private Socket socket;
    private Servidor servidor;
    private BufferedReader entrada;
    private PrintWriter salida;
    private String nombreUsuario;

    public HiloCliente(Socket socket, Servidor servidor) {
        this.socket = socket;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try {
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);

            salida.println("Introduce tu nombre de usuario:");
            nombreUsuario = entrada.readLine();
            servidor.log(nombreUsuario + " se ha conectado.");

            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                servidor.log(nombreUsuario + ": " + mensaje);
                servidor.difundirMensaje(nombreUsuario + ": " + mensaje, this);
            }
        } catch (IOException e) {
            servidor.log("Error con el cliente: " + e.getMessage());
        } finally {
            servidor.eliminarCliente(this);
            cerrarConexiones();
        }
    }

    public void enviarMensaje(String mensaje) {
        salida.println(mensaje);
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    private void cerrarConexiones() {
        try {
            if (socket != null) socket.close();
            if (entrada != null) entrada.close();
            if (salida != null) salida.close();
        } catch (IOException e) {
            servidor.log("Error cerrando conexiones: " + e.getMessage());
        }
    }
}
