module mpp.problema5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires java.sql;

    opens mpp.problema5 to javafx.fxml;
    exports mpp.problema5;
}