module org.example.chatclientjava41 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.desktop;
    requires jjwt.api;
    requires okhttp3;
    requires com.fasterxml.jackson.databind;

    opens org.example.chatclientjava41 to javafx.fxml;
    exports org.example.chatclientjava41;
}