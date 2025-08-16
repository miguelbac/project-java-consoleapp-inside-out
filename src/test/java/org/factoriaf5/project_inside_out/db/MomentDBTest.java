package org.factoriaf5.project_inside_out.db;

import org.factoriaf5.project_inside_out.models.Moment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.factoriaf5.project_inside_out.models.Emotion;




class MomentDBTest {

    private MomentDB momentDB;

    @BeforeEach
    void setUp() {
        momentDB = new MomentDB();
    }

    private Moment createTestMoment(int id) {
        return new Moment(
                id,
                "Title " + id,
                "Description " + id,
                Emotion.ALEGRIA,
                LocalDate.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    void testAddMoment() {
        Moment moment = createTestMoment(1);
        momentDB.add(moment);
        List<Moment> allMoments = momentDB.getAll();
        assertEquals(1, allMoments.size());
        assertEquals(moment.getId(), allMoments.get(0).getId());
    }

    @Test
    void testGetAllReturnsCopy() {
        Moment moment = createTestMoment(2);
        momentDB.add(moment);
        List<Moment> copy = momentDB.getAll();
        assertEquals(1, copy.size());
        copy.clear();
        assertEquals(1, momentDB.getAll().size());
    }

    @Test
    void testDeleteByIdExisting() {
        Moment moment = createTestMoment(3);
        momentDB.add(moment);
        boolean deleted = momentDB.deleteById(3);
        assertTrue(deleted);
        assertTrue(momentDB.getAll().isEmpty());
    }

    @Test
    void testDeleteByIdNonExisting() {
        Moment moment = createTestMoment(4);
        momentDB.add(moment);
        boolean deleted = momentDB.deleteById(999);
        assertFalse(deleted);
        assertEquals(1, momentDB.getAll().size());
    }
}
