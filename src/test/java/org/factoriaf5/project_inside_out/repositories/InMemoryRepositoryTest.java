package org.factoriaf5.project_inside_out.repositories;

import org.factoriaf5.project_inside_out.models.Emotion;
import org.factoriaf5.project_inside_out.models.Moment;
import org.factoriaf5.project_inside_out.db.MomentDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryMomentRepositoryTest {

    private InMemoryMomentRepository repository;
    private FakeMomentDB db;

    // Fake DB para test
    static class FakeMomentDB extends MomentDB {
        private final List<Moment> moments = new LinkedList<>();
        private int lastId = 0;

        @Override
        public void add(Moment moment) {
            lastId++;
            // Creamos un nuevo Moment con ID asignado
            Moment momentWithId = new Moment(
                lastId,
                moment.getTitle(),
                moment.getDescription(),
                moment.getEmotion(),
                moment.getEventDate(),
                LocalDateTime.now(), // createdAt
                LocalDateTime.now()  // updatedAt
            );
            moments.add(momentWithId);
        }

        @Override
        public List<Moment> getAll() {
            return new LinkedList<>(moments);
        }

        @Override
        public boolean deleteById(int id) {
            return moments.removeIf(m -> m.getId() == id);
        }
    }

    @BeforeEach
    void setUp() {
        db = new FakeMomentDB();
        repository = new InMemoryMomentRepository(db);
    }

    @Test
    void testSaveAddsMoment() {
        Moment moment = new Moment("Mi título", "Mi descripción", Emotion.ALEGRIA, LocalDate.now());
        Moment saved = repository.save(moment);

        assertNotNull(saved);
        assertEquals(1, saved.getId());
        assertEquals("Mi título", saved.getTitle());
        assertEquals(1, db.getAll().size());
    }

    @Test
    void testFindAllReturnsAllMoments() {
        repository.save(new Moment("Title1", "Desc1", Emotion.ALEGRIA, LocalDate.now()));
        repository.save(new Moment("Title2", "Desc2", Emotion.TRISTEZA, LocalDate.now()));

        List<Moment> moments = repository.findAll();
        assertEquals(2, moments.size());
        assertEquals("Title1", moments.get(0).getTitle());
        assertEquals("Title2", moments.get(1).getTitle());
    }

    @Test
    void testDeleteByIdRemovesMoment() {
        Moment saved = repository.save(new Moment("Title", "Desc", Emotion.ALEGRIA, LocalDate.now()));
        assertEquals(1, db.getAll().size());

        repository.deleteById(saved.getId());
        assertEquals(0, db.getAll().size());
    }
}
