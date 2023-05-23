module com.example.nbaanalytics {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.nbaanalytics to javafx.fxml;
    exports com.example.nbaanalytics;
}