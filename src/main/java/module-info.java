module reymark.employeeJBC {

    requires transitive javafx.controls;
    requires javafx.fxml;
    requires transitive core.fx;
    requires transitive core.db;
    requires javafx.base;
    requires core.util;

    opens dev.reymark to javafx.fxml;
    opens dev.reymark.app to javafx.fxml;
    

    exports dev.reymark;
    exports dev.reymark.app;

}
