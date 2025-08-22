package org.factoriaf5.project_inside_out.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MomentDTOTest {

    @Test
    void testConstructorAndGetters() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        MomentDTO dto = new MomentDTO("T", "D", Emotion.TRISTEZA, date, true);

        assertEquals("T", dto.getTitle());
        assertEquals("D", dto.getDescription());
        assertEquals(Emotion.TRISTEZA, dto.getEmotion());
        assertEquals(date, dto.getEventDate());
        assertTrue(dto.isGood());
    }
}
