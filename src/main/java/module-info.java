module com.contributor {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires static lombok;

    opens com.contributor to javafx.controls, javafx.graphics, lombok;
    opens com.contributor.view to javafx.fxml, javafx.controls, lombok;
    opens com.contributor.model to javafx.base;
    opens com.contributor.viewmodel to com.fasterxml.jackson.core, com.fasterxml.jackson.databind, javafx.base;
}
