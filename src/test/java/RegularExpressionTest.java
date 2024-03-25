import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class RegularExpressionTest {

    @Test
    void separateStates() {
        String s = "q0,   q1, q2";
        Pattern p = Pattern.compile("[, ]+[^,\\w]*?");
        String[] split = s.split(p.pattern());

        assertArrayEquals(split, new String[]{"q0", "q1", "q2"});
    }

    @Test
    void separateStatesSpecial() {
        String s = "  ? q0, ,,,, , q1, $%$q2, q3";
        Pattern p = Pattern.compile("[\\s,!?]*[\\s,!?]+\\W*");
        String g = s.trim().replaceAll(p.pattern(), "|");



        String[] split = g.replaceAll("^\\|+|\\|+$", "").split("\\|+");


        assertArrayEquals(split, new String[]{"q0", "q1", "q2", "q3"});
    }

}
