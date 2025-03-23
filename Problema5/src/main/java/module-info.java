module mpp.problema5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires java.sql;

    opens mpp.problema5 to javafx.fxml;
    opens mpp.problema5.Controllers to javafx.fxml;
    opens mpp.problema5.Domain to javafx.fxml;
    exports mpp.problema5;
    exports mpp.problema5.Controllers;
    exports mpp.problema5.Domain;
}