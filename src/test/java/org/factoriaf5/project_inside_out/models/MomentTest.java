package org.factoriaf5.project_inside_out.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MomentTest {

    @Test
    void testConstructorFullAndGetters() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        LocalDateTime now = LocalDateTime.now();

        Moment m = new Moment(1, "Title", "Desc", Emotion.ALEGRIA, date, now, now, true);

        assertEquals(1, m.getId());
        assertEquals("Title", m.getTitle());
        assertEquals("Desc", m.getDescription());
        assertEquals(Emotion.ALEGRIA, m.getEmotion());
        assertEquals(date, m.getEventDate());
        assertEquals(now, m.getCreatedAt());
        assertEquals(now, m.getUpdatedAt());
        assertTrue(m.isGood());
    }

    @Test
    void testConstructorWithoutIdAndGetters() {
        LocalDate date = LocalDate.of(2024, 2, 1);

        Moment m = new Moment("T", "D", Emotion.MIEDO, date, false);

        assertEquals("T", m.getTitle());
        assertEquals("D", m.getDescription());
        assertEquals(Emotion.MIEDO, m.getEmotion());
        assertEquals(date, m.getEventDate());
        assertFalse(m.isGood());
        assertNotNull(m.getCreatedAt());
        assertNotNull(m.getUpdatedAt());
    }

    @Test
    void testSetId() {
        Moment m = new Moment("A", "B", Emotion.IRA, LocalDate.now(), true);
        m.setId(42);
        assertEquals(42, m.getId());
    }
}
