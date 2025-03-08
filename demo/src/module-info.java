module demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.junit.jupiter.api;
    requires org.junit.platform.commons;
    opens tests to org.junit.platform.commons;
    opens GUI to javafx.fxml;
    exports GUI;
}