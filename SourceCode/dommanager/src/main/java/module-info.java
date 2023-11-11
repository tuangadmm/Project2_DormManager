module Main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.sql;
    requires commons.validator;
    opens Main to javafx.fxml;
    opens data to javafx.fxml;
    exports Main;
    exports data;
}
