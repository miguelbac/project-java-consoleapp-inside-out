package org.factoriaf5.project_inside_out.repositories;

import org.factoriaf5.project_inside_out.db.MomentDB;
import org.factoriaf5.project_inside_out.models.Emotion;
import org.factoriaf5.project_inside_out.models.Moment;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryMomentRepositoryTest {

    private InMemoryMomentRepository repository;
    private MomentDB db;

    @BeforeEach
    void setUp() {
        db = new MomentDB();
        repository = new InMemoryMomentRepository(db);
    }

    @Test
    void save_ShouldAssignId() {
        Moment moment = new Moment("Título", "Descripción", Emotion.ALEGRIA,
                LocalDate.now(), true);

        Moment saved = repository.save(moment);

        assertEquals(1, saved.getId());
        assertEquals(1, db.getAll().size());
    }

    @Test
    void deleteById_ShouldRemoveMoment() {
        Moment saved = repository.save(new Moment("Test", "Desc", Emotion.TRISTEZA,
                LocalDate.now(), false));

        repository.deleteById(saved.getId());

        assertTrue(db.getAll().isEmpty());
    }
}
