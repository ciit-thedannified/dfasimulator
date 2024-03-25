package ciit.automata.dfasimulator;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;


public class State {

    private static int count = 0;

    private final SimpleObjectProperty<CheckBox> initialCheck;
    private final SimpleObjectProperty<CheckBox> finalCheck;
    private final SimpleBooleanProperty initial;
    private final SimpleBooleanProperty accept;
    private final SimpleStringProperty label;

    public State(boolean initial, boolean accept) {
        String label = "q" + count;
        CheckBox ic = new CheckBox();
        CheckBox ac = new CheckBox();

        ic.setSelected(initial);
        ac.setSelected(accept);

        this.initial = new SimpleBooleanProperty(this, "initial", initial);
        this.accept = new SimpleBooleanProperty(this, "accept", accept);
        this.label = new SimpleStringProperty(this, "label", label);

        this.initialCheck = new SimpleObjectProperty<>(this, "initialCheck", ic);
        this.finalCheck = new SimpleObjectProperty<>(this, "finalCheck", ac);


        this.initial.bindBidirectional(this.initialCheck.get().selectedProperty());
        this.accept.bindBidirectional(this.finalCheck.get().selectedProperty());

        count++;
    }

    public State (boolean accept) {
        this(false, true);
    }

    public State() {
        this(false, false);
    }

    public boolean isInitial() {
        return initial.get();
    }

    public SimpleBooleanProperty initialProperty() {
        return initial;
    }

    public void setInitial(boolean initial) {
        this.initial.set(initial);
    }

    public boolean isAccept() {
        return accept.get();
    }

    public SimpleBooleanProperty acceptProperty() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept.set(accept);
    }

    public String getLabel() {
        return this.label.get();
    }

    public SimpleStringProperty labelProperty() {
        return label;
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    public CheckBox getInitialCheck() {
        return initialCheck.get();
    }

    public SimpleObjectProperty<CheckBox> initialCheckProperty() {
        return initialCheck;
    }

    public void setInitialCheck(CheckBox initialCheck) {
        this.initialCheck.set(initialCheck);
    }

    public CheckBox getFinalCheck() {
        return finalCheck.get();
    }

    public SimpleObjectProperty<CheckBox> finalCheckProperty() {
        return finalCheck;
    }

    public void setFinalCheck(CheckBox finalCheck) {
        this.finalCheck.set(finalCheck);
    }

}
