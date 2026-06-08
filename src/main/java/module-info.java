module com.contributor {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    opens com.contributor to javafx.controls, javafx.graphics;
    opens com.contributor.view to javafx.fxml, javafx.controls;
}
