module ciit.automata.dfasimulator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.jetbrains.annotations;

    opens ciit.automata.dfasimulator to javafx.fxml;
    opens ciit.automata.dfasimulator.components to javafx.fxml;
    exports ciit.automata.dfasimulator;
}