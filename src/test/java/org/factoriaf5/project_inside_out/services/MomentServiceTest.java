package org.factoriaf5.project_inside_out.services;

import org.factoriaf5.project_inside_out.models.Emotion;
import org.factoriaf5.project_inside_out.models.Moment;
import org.factoriaf5.project_inside_out.models.MomentDTO;
import org.factoriaf5.project_inside_out.repositories.MomentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


class MomentServiceTest {

    private MomentService service;

    static class InMemoryMomentRepository implements MomentRepository {
        private final List<Moment> moments = new java.util.ArrayList<>();
        private int idCounter = 1;

        @Override
        public Moment save(Moment moment) {
            Moment saved = new Moment(
                    idCounter++,
                    moment.getTitle(),
                    moment.getDescription(),
                    moment.getEmotion(),
                    moment.getEventDate(),
                    java.time.LocalDateTime.now(),
                    java.time.LocalDateTime.now()
            );
            moments.add(saved);
            return saved;
        }

        @Override
        public List<Moment> findAll() {
            return new java.util.ArrayList<>(moments);
        }

        @Override
        public void deleteById(int id) {
            moments.removeIf(m -> m.getId() == id);
        }
    }

    @BeforeEach
    void setUp() {
        service = new MomentService(new InMemoryMomentRepository());
    }

    @Test
    void testAddMoment() {
        MomentDTO dto = new MomentDTO("Cumpleaños", "Fui a la fiesta", 1, "10/08/2025");
        Moment saved = service.addMoment(dto);

        assertThat(saved.getId(), greaterThan(0));
        assertThat(saved.getTitle(), is("Cumpleaños"));
        assertThat(saved.getEmotion(), is(Emotion.ALEGRIA));
    }

    @Test
    void testGetAllMoments() {
        service.addMoment(new MomentDTO("Título1", "Desc1", 2, "01/01/2025"));
        service.addMoment(new MomentDTO("Título2", "Desc2", 3, "02/02/2025"));

        List<Moment> all = service.getAllMoments();
        assertThat(all, hasSize(2));
        assertThat(all, hasItem(hasProperty("title", is("Título1"))));
    }

    @Test
    void testDeleteMoment() {
        Moment m = service.addMoment(new MomentDTO("Prueba", "Desc", 1, "01/01/2025"));
        service.deleteMoment(m.getId());

        List<Moment> all = service.getAllMoments();
        assertThat(all, not(hasItem(hasProperty("id", is(m.getId())))));
    }

    @Test
    void testGetMomentsByEmotion() {
        service.addMoment(new MomentDTO("Feliz", "Desc", 1, "01/01/2025")); // Alegría
        service.addMoment(new MomentDTO("Triste", "Desc", 2, "02/02/2025")); // Tristeza

        List<Moment> alegrías = service.getMomentsByEmotion(1);
        assertThat(alegrías, hasSize(1));
        assertThat(alegrías.get(0).getEmotion(), is(Emotion.ALEGRIA));
    }

    @Test
    void testGetMomentsByMonthYear() {
        service.addMoment(new MomentDTO("Evento1", "Desc", 1, "10/08/2025"));
        service.addMoment(new MomentDTO("Evento2", "Desc", 2, "15/08/2025"));
        service.addMoment(new MomentDTO("Evento3", "Desc", 3, "01/09/2025"));

        List<Moment> agosto = service.getMomentsByMonthYear(8, 2025);
        assertThat(agosto, hasSize(2));
        assertThat(agosto, everyItem(hasProperty("eventDate", hasProperty("monthValue", is(8)))));
    }
}
