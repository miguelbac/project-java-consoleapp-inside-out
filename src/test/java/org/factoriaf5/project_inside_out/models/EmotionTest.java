package org.factoriaf5.project_inside_out.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmotionTest {

    @Test
    void testFromOptionValid() {
        assertEquals(Emotion.ALEGRIA, Emotion.fromOption(1));
        assertEquals(Emotion.NOSTALGIA, Emotion.fromOption(10));
    }

    @Test
    void testFromOptionInvalid() {
        assertThrows(IllegalArgumentException.class, () -> Emotion.fromOption(0));
        assertThrows(IllegalArgumentException.class, () -> Emotion.fromOption(11));
    }
}
