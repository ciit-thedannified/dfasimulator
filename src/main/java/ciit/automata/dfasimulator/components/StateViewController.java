package ciit.automata.dfasimulator.components;

import ciit.automata.dfasimulator.BaseController;
import ciit.automata.dfasimulator.DFAOperations;
import ciit.automata.dfasimulator.State;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class StateViewController implements Initializable {

    @FXML
    private Button addState;
    @FXML
    private Button deleteState;
    @FXML
    private TableView<State> transition_table;
    @FXML
    private TableColumn<State, CheckBox> col_initialState;
    @FXML
    private TableColumn<State, CheckBox> col_finalStates;
    @FXML
    private TableColumn<State, String> col_states;
    @FXML
    private ComboBox<State> selected_initial;

    @FXML
    private TextArea transitionInput;
    protected ObservableList<Character> alphabet;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BaseController.registerController("stateView", this);
        this.alphabet = DFAOperations.getAlphabet();

        // Instantiate first state.
        transition_table.getItems().add(new State(true, false));
        selected_initial.setItems(transition_table.getItems());

        selected_initial.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(State item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                }
                else {
                    setText(item.getLabel());
                }
            }
        });

        selected_initial.setConverter(new StringConverter<>() {
            @Override
            public String toString(State state) {
                return state == null ? null : state.getLabel();
            }

            @Override
            public State fromString(String s) {
                return null;
            }
        });

        transition_table.getItems().addListener((ListChangeListener<? super State>) change -> {
            change.next();
            if (change.wasUpdated()) {
                System.out.println(change);
            }
        });

        addState.setOnAction(this::createState);
        deleteState.setOnAction(this::deleteState);

        col_initialState.setCellValueFactory(new PropertyValueFactory<>("initialCheck"));
        col_finalStates.setCellValueFactory(new PropertyValueFactory<>("finalCheck"));
        col_states.setCellValueFactory(new PropertyValueFactory<>("label"));

        selected_initial.getSelectionModel().select(0);

    }

    private void createState(ActionEvent event) {
        State newState = new State();

        transition_table.getItems().add(newState);
    }

    private void deleteState(ActionEvent event) {
        State targetState = transition_table.getSelectionModel().getSelectedItem();

        if (targetState == null) {
            Alert noSelectedState = new Alert(Alert.AlertType.ERROR);

            noSelectedState.setTitle("No selected state");
            noSelectedState.setContentText("Select a state to delete first.");
            noSelectedState.showAndWait();

            return;
        }

        transition_table.getItems().remove(targetState);
    }

    protected ObservableList<State> getStates() {
        return transition_table.getItems();
    }

    protected TextArea getTransitionInput() {
        return transitionInput;
    }

}
