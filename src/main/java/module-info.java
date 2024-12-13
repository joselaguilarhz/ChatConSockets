module com.example.chatconsockets {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chatconsockets to javafx.fxml;
    exports com.example.chatconsockets;
    exports com.example.chatconsockets.Servidor;
    opens com.example.chatconsockets.Servidor to javafx.fxml;
}