package ciit.automata.dfasimulator;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public final class Transition {
    private final State currentState;
    private final HashMap<Character, State> movements;

    public Transition(@NotNull State currentState, @NotNull HashMap<Character, State> movements) {
        this.currentState = currentState;
        this.movements = new HashMap<>();
    }

    public Transition(@NotNull State currentState) {
        this(currentState, new HashMap<>());
    }

    public State getCurrentState() {
        return currentState;
    }

    public HashMap<Character, State> getMovements() {
        return movements;
    }
}