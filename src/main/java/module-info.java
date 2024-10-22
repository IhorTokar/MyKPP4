module com.example.mykpp4 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;

    opens com.example.mykpp4 to javafx.fxml;
    exports com.example.mykpp4;
}