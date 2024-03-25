package ciit.automata.dfasimulator.components;

import ciit.automata.dfasimulator.*;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static ciit.automata.dfasimulator.BaseController.getController;
import static java.util.Arrays.stream;

public class ConfigurationPanelController implements Initializable {

    @FXML
    private TextField input_string;
    @FXML
    private CheckBox enable_auto_simulation;
    @FXML
    private TextField test_result;
    @FXML
    private Button validateString;
    @FXML
    private Button simulateString;
    @FXML
    private TextField input_symbol;
    @FXML
    private Button add_symbol;
    @FXML
    private Button del_symbol;
    @FXML
    private ListView<Character> symbol_list;
    @FXML
    private Accordion accordion_input;

    // Listener instances
    private final ChangeListener<? super String> inputSymbolListener = (observable, oldValue, newValue) -> {
        if (newValue.length() >= 2)
            ((StringProperty) observable).setValue(oldValue);
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        BaseController.registerController("configurationPanel", this);
        symbol_list.itemsProperty().set(DFAOperations.getAlphabet());

        // Input section configuration
        validateString.setOnAction(this::validateInput);
        simulateString.setOnAction(this::simulateInput);

        // Alphabet section configuration
        add_symbol.setOnAction(this::addSymbol);
        del_symbol.setOnAction(this::deleteSymbol);

        input_symbol.addEventFilter(KeyEvent.KEY_TYPED, this::readSymbol);
        input_symbol.textProperty().addListener(this.inputSymbolListener);

    }

    private void validateInput(ActionEvent actionEvent) {
        ObservableList<Character> alphabets = symbol_list.getItems();
        String input = input_string.getText();
        Character[] symbols = alphabets.toArray(Character[]::new);

        DFAOperations.InputType result = DFAOperations.validateString(input, symbols);

        switch (result) {
            case VALID -> test_result.setText("Valid String");
            case INVALID -> test_result.setText("Invalid String");
        }
    }

    private void simulateInput(ActionEvent actionEvent) {
        processTransitionMap();

        TransitionMap tm = DFAOperations.getTransitionMap();

        test_result.setText(tm.readInput(input_string.getText()).toString());
    }

    private void addSymbol(ActionEvent actionEvent) {
        ObservableList<Character> items = symbol_list.getItems();
        char symbol;

        if (input_symbol.getText().isEmpty()) {
            Alert noEmptySymbol = new Alert(Alert.AlertType.ERROR);

            noEmptySymbol.setTitle("Empty symbol not allowed");
            noEmptySymbol.setContentText("Please input a symbol first.");
            noEmptySymbol.showAndWait();
            return;
        }

        symbol = (char) input_symbol.getText().codePointAt(0);

        if (items.contains(symbol)) {
            Alert noDuplicateSymbol = new Alert(Alert.AlertType.ERROR);

            noDuplicateSymbol.setTitle("Duplicate symbol not allowed");
            noDuplicateSymbol.setContentText(String.format("Symbol '%c' has already been added in the alphabets.", symbol));
            noDuplicateSymbol.showAndWait();
            return;
        };

        items.add(symbol);
        input_symbol.clear();
    }

    private void deleteSymbol(ActionEvent actionEvent) {
        ObservableList<Character> items = symbol_list.getItems();
        int idx = symbol_list.getFocusModel().getFocusedIndex();

        if (idx <= -1) {
            Alert emptyAlphabets = new Alert(Alert.AlertType.ERROR);

            emptyAlphabets.setTitle("No selected alphabets");
            emptyAlphabets.setContentText("Please register at least 1 symbol to the alphabet set.");
            emptyAlphabets.showAndWait();
            return;
        }

        items.remove(idx);
    }

    private void readSymbol(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.BACK_SPACE) keyEvent.consume();

        if (input_symbol.getText().length() >= 2
                || !keyEvent.getCharacter().matches("[a-zA-Z0-9]+")) {
            keyEvent.consume();
        }
    }

    private void processTransitionMap() {
        StateViewController stateViewController = (StateViewController) getController("stateView");

        String transitionInput = stateViewController.getTransitionInput().getText();
        ObservableList<State> states = stateViewController.getStates();
        Pattern p = Pattern.compile("[\\s,!?|]*[\\s,!?|]+\\W*");

        String[] stateTransitions = transitionInput.split("\n");

        Iterator<State> st = states.iterator();

        Set<String> ss = states.stream().map(State::getLabel)
                .collect(Collectors.toSet());


        List<State> initialStates = states.stream().filter(State::isInitial).toList();
        if (initialStates.size() != 1) {
            Alert oneInitialStateOnly = new Alert(Alert.AlertType.ERROR);
            oneInitialStateOnly.setTitle("Initial state error");
            oneInitialStateOnly.setContentText("Please set only one initial state in the configuration.");
            oneInitialStateOnly.showAndWait();
            return;
        }

        if (DFAOperations.getTransitionMap() == null) {
            DFAOperations.setTransitionMap(new TransitionMap(initialStates.get(0)));
        }

        DFAOperations.getTransitionMap().setInitialState(initialStates.get(0));

        for (String transition : stateTransitions) {
            if (!st.hasNext()) break;

            State c = st.next();
            String s = transition.trim().replaceAll(p.pattern(), "|");
            String[] q = s.replaceAll("^\\|+|\\|+$", "").split("\\|+");

            if (q.length > DFAOperations.getAlphabet().size()) {
                Alert insufficientAlphabet = new Alert(Alert.AlertType.ERROR);
                insufficientAlphabet.setTitle("Insufficient alphabets");
                insufficientAlphabet.setContentText("Please add more alphabets in the configuration.");
                insufficientAlphabet.showAndWait();
                return;
            }
            else if (q.length < DFAOperations.getAlphabet().size()) {
                Alert tooMuchInput = new Alert(Alert.AlertType.ERROR);
                tooMuchInput.setTitle("Too much input");
                tooMuchInput.setContentText("Please remove excessive inputs in the configuration.");
                tooMuchInput.showAndWait();
                return;
            }
            else if (!ss.containsAll(List.of(q))) {
                Alert invalidStateInput = new Alert(Alert.AlertType.ERROR);
                invalidStateInput.setTitle("Invalid state");
                invalidStateInput.setContentText(String.format("Invalid state input found in %s", c.getLabel()));
                invalidStateInput.showAndWait();
                return;
            }
            else {
                Transition t = new Transition(c);
                ObservableList<Character> symbols = DFAOperations.getAlphabet();

                for (AtomicInteger i = new AtomicInteger(0); i.get() < q.length; i.getAndIncrement()) {
                    t.getMovements().put(symbols.get(i.get()), states.stream().filter(z -> z.getLabel().equals(q[i.get()])).findFirst().get());
                }

                DFAOperations.getTransitionMap().getTransitions().put(c, t);
            }
        }
    }

}
