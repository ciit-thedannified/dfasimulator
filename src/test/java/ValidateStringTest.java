import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ciit.automata.dfasimulator.DFAOperations.InputType;
import static ciit.automata.dfasimulator.DFAOperations.validateString;

public class ValidateStringTest {

    /**
     * Expected to treat the string as VALID because the string contains
     */
    @Test
    void validString() {

        String input = "000110";
        Character[] alphabets = {'0', '1'};

        assertEquals(validateString(input, alphabets), InputType.VALID);

    }

    @Test
    void invalidString() {

        String input = "011a1001";
        char[] alphabets = {'0', '1', 'b'};

        assertEquals(validateString(input, alphabets), InputType.INVALID);

    }

    @Test
    void validStringExtraSymbols() {

        String input = "011ea0010";
        Character[] alphabets = {'0', '1', 'a', 'b', 'e'};

        assertEquals(validateString(input, alphabets), InputType.VALID);

    }

    @Test
    void invalidStringExtraSymbols() {

        String input = "001ac100";
        char[] alphabets = {'0', '1', 'a', 'b'};

        assertEquals(validateString(input, alphabets), InputType.INVALID);

    }

}
