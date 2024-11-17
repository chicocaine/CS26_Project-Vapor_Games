module User_Interface {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;
    requires javafx.media;
    requires java.sql;
    requires com.zaxxer.hikari;

    opens User_Interface to javafx.fxml;
    exports User_Interface;
}
