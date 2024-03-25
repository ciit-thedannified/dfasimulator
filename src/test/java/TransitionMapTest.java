import ciit.automata.dfasimulator.TransitionMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TransitionMapTest {

    @Test
    void createNoArgTransitionMap() {
        TransitionMap transitionMap = new TransitionMap();

        assertNotNull(transitionMap.getCurrentState());
    }

}
