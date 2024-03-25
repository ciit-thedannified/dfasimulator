package ciit.automata.dfasimulator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class DFAOperations {

    private final static ObservableList<Character> alphabet;
    private static TransitionMap transitionMap;


    static {
        alphabet = FXCollections.observableArrayList();
    }

    public enum Result {
        ACCEPTED,
        REJECTED
    }

    public enum InputType {
        VALID,
        INVALID
    }

    public synchronized static InputType validateString(String input, char[] symbols) {
        String regex = buildRegularExpression(symbols);

        if (regex == null) return InputType.INVALID;

        return input.matches(regex) ? InputType.VALID : InputType.INVALID;
    }

    public synchronized static InputType validateString(String input, Character[] symbols) {
        String regex = buildRegularExpression(symbols);

        if (regex == null) return InputType.INVALID;

        return input.matches(regex) ? InputType.VALID : InputType.INVALID;
    }


    private static String buildRegularExpression(char[] symbols) {

        if (symbols.length == 0) return null;

        StringBuilder regex = new StringBuilder("[");
        for (char symbol : symbols)
            regex.append(symbol);
        regex.append("]+");

        return regex.toString();

    }

    private static String buildRegularExpression(Character[] symbols) {

        if (symbols.length == 0) return null;

        StringBuilder regex = new StringBuilder("[");
        for (char symbol : symbols)
            regex.append(symbol);
        regex.append("]+");

        return regex.toString();
    }

    public static ObservableList<Character> getAlphabet() {
        return alphabet;
    }

    public static void setTransitionMap(TransitionMap tm) {
        transitionMap = tm;
    }

    public static TransitionMap getTransitionMap() {
        return transitionMap;
    }

}
