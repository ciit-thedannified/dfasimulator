package ciit.automata.dfasimulator;

import ciit.automata.dfasimulator.components.OutputViewController;
import javafx.scene.control.TextArea;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class TransitionMap {

    private final HashMap<State, Transition> transitions;

    private State initialState;

    private Transition currentTransition;

    public TransitionMap(@NotNull HashMap<State, Transition> transitionMap, State initialState) {
        this.transitions = new HashMap<>(transitionMap);
        this.initialState = initialState;
        this.currentTransition = initialState == null ? null : transitions.get(initialState);
    }

    public TransitionMap(@NotNull HashMap<State, Transition> transitionMap) {
        this(transitionMap,
                transitionMap.keySet().stream()
                        .filter(State::isInitial).findFirst()
                        .orElse(new State(true, false)));
    }

    public TransitionMap(State initialState) {
        this(new HashMap<>(), initialState);
    }

    public TransitionMap() {
        this(new HashMap<>(), null);
    }

    public void setInitialState(@NotNull State state) {
        this.initialState = state;
    }

    private Transition acceptSymbol(char symbol) {
        return this.currentTransition = this.currentTransition == null ?
                transitions.get(initialState) : transitions.get(currentTransition.getMovements().get(symbol));
    }

    public State getCurrentState() {
        return currentTransition.getCurrentState();
    }

    public void resetTransition() {
        this.currentTransition = transitions.get(initialState);
    }

    public HashMap<State, Transition> getTransitions() {
        return transitions;
    }

    public DFAOperations.Result readInput(String input) {
        char[] i = input.toCharArray();
        DFAOperations.Result result;
        OutputViewController ovc = ((OutputViewController) BaseController.getController("outputView"));
        TextArea outArea = ovc.getOutputArea();

        StringBuilder out = new StringBuilder("Simulating ").append(input).append("...\n");

        outArea.clear();

        for (char c : i) {
            out.append(String.format("%1$s received symbol '%2$c' (%1$s, %2$c) -> ",
                    this.currentTransition == null ? initialState.getLabel() : getCurrentState().getLabel(), c));

            acceptSymbol(c);
            out.append(String.format("%s\n", getCurrentState().getLabel()));
        }

        result = getCurrentState().isAccept() ? DFAOperations.Result.ACCEPTED : DFAOperations.Result.REJECTED;

        out.append("Result: ").append(result).append("\n");
        ovc.getOutputArea().setText(out.toString());

        resetTransition();
        return result;
    }
}
