package ciit.automata.dfasimulator;

import javafx.fxml.Initializable;

import java.util.HashMap;

public final class BaseController {

    static {
        controllers = new HashMap<>();
    }

    private final static HashMap<String, Initializable> controllers;

    public static void registerController(String name, Initializable controller) {
        controllers.put(name, controller);
    }

    public static Initializable getController(String name) {
        return controllers.get(name);
    }


}
