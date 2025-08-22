package org.factoriaf5.project_inside_out.services;

import org.factoriaf5.project_inside_out.models.*;
import org.factoriaf5.project_inside_out.repositories.InMemoryMomentRepository;
import org.factoriaf5.project_inside_out.db.MomentDB;
import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MomentServiceTest {

    private MomentService service;

    @BeforeEach
    void setUp() {
        service = new MomentService(new InMemoryMomentRepository(new MomentDB()));
    }

    @Test
    void addMoment_ShouldSaveFromDTO() {
        MomentDTO dto = new MomentDTO("Viaje", "A la playa", Emotion.ALEGRIA, LocalDate.now(), true);

        Moment moment = service.addMoment(dto);

        assertNotNull(moment);
        assertEquals("Viaje", moment.getTitle());
    }

    @Test
    void getAllMoments_ShouldReturnAllSaved() {
        service.addMoment(new MomentDTO("A", "Desc", Emotion.ALEGRIA, LocalDate.now(), true));
        service.addMoment(new MomentDTO("B", "Desc", Emotion.MIEDO, LocalDate.now(), false));

        List<Moment> moments = service.getAllMoments();

        assertEquals(2, moments.size());
    }

    @Test
    void deleteMoment_ShouldRemoveMomentById() {
        Moment saved = service.addMoment(new MomentDTO("C", "Desc", Emotion.ANSIEDAD, LocalDate.now(), false));

        service.deleteMoment(saved.getId());

        assertTrue(service.getAllMoments().isEmpty());
    }

    @Test
    void filterByEmotion_ShouldReturnCorrectMoments() {
        service.addMoment(new MomentDTO("Triste", "Llorar", Emotion.TRISTEZA, LocalDate.now(), false));
        service.addMoment(new MomentDTO("Feliz", "Reír", Emotion.ALEGRIA, LocalDate.now(), true));

        List<Moment> sad = service.filterByEmotion(Emotion.TRISTEZA);

        assertEquals(1, sad.size());
        assertEquals("Triste", sad.get(0).getTitle());
    }

    @Test
    void filterByDate_ShouldReturnMatchingMoments() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        service.addMoment(new MomentDTO("Hoy", "Evento", Emotion.ALEGRIA, today, true));
        service.addMoment(new MomentDTO("Ayer", "Evento", Emotion.ALEGRIA, yesterday, true));

        List<Moment> result = service.filterByDate(today);

        assertEquals(1, result.size());
        assertEquals("Hoy", result.get(0).getTitle());
    }

    @Test
    void filterByType_ShouldReturnGoodMoments() {
        service.addMoment(new MomentDTO("Bueno", "Evento", Emotion.ALEGRIA, LocalDate.now(), true));
        service.addMoment(new MomentDTO("Malo", "Evento", Emotion.IRA, LocalDate.now(), false));

        List<Moment> good = service.filterByType(true);

        assertEquals(1, good.size());
        assertTrue(good.get(0).isGood());
    }

    @Test
    void exportToCSV_ShouldGenerateFileWithContent() throws Exception {
        service.addMoment(new MomentDTO("Exportable", "Evento", Emotion.NOSTALGIA, LocalDate.now(), true));

        service.exportToCSV("moments_test.csv");

        File file = new File("exports/moments_test.csv");
        assertTrue(file.exists());

        String content = Files.readString(file.toPath());
        assertTrue(content.contains("Exportable"));
        assertTrue(content.contains("Nostalgia"));
    }
}
