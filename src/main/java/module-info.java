module User_Interface {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;
    requires javafx.media;
    requires java.sql;
    requires com.zaxxer.hikari;
    requires com.gluonhq.attach.audio;
    requires org.controlsfx.controls;
    opens User_Interface.PopUps to javafx.fxml;
    opens User_Interface to javafx.fxml;
    exports User_Interface;
    exports Accounts;
}
