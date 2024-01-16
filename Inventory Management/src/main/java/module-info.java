module javaproject.project {
    requires javafx.controls;
    requires javafx.fxml;


    opens javaproject.project to javafx.fxml;
    exports javaproject.project;
}