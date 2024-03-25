package ciit.automata.dfasimulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("DFA Simulator");
        stage.setScene(scene);
        stage.show();

        stage.setResizable(false);

    }

    public static void main(String[] args) {
        launch();
    }
}