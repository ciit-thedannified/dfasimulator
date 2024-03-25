package ciit.automata.dfasimulator.components;

import ciit.automata.dfasimulator.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class OutputViewController implements Initializable {

    @FXML
    private TextArea output_area;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Nothing to initialize here, really...
        BaseController.registerController("outputView", this);
    }

    public TextArea getOutputArea() {
        return output_area;
    }


}
