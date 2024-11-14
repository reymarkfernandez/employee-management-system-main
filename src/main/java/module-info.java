module reymark.employeeJBC {

    requires transitive javafx.controls;

    requires javafx.fxml;
    requires core.fx;
    requires javafx.graphics;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign2;
    requires org.kordamp.ikonli.materialdesign;
    requires atlantafx.base;
    requires core.db;

    opens dev.reymark to javafx.fxml;
    opens dev.reymark.app to java.fxml;

    exports dev.reymark;
    exports dev.reymark.app;

}
