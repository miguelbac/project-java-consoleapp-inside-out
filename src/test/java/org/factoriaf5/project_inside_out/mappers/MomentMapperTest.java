package org.factoriaf5.project_inside_out.mappers;

import org.factoriaf5.project_inside_out.models.Emotion;
import org.factoriaf5.project_inside_out.models.Moment;
import org.factoriaf5.project_inside_out.models.MomentDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MomentMapperTest {

    @Test
    void testFromDTOWithAllFields() {
        // Arrange
        MomentDTO dto = new MomentDTO(
            "Test Moment",
            "This is a test description",
            Emotion.ALEGRIA,
            LocalDate.of(2024, 1, 20),
            true
        );

        // Act
        Moment moment = MomentMapper.fromDTO(dto);

        // Assert
        assertNotNull(moment);
        assertEquals("Test Moment", moment.getTitle());
        assertEquals("This is a test description", moment.getDescription());
        assertEquals(Emotion.ALEGRIA, moment.getEmotion());
        assertEquals(LocalDate.of(2024, 1, 20), moment.getEventDate());
        assertTrue(moment.isGood());
        
        // Los campos createdAt y updatedAt deben ser auto-generados
        assertNotNull(moment.getCreatedAt());
        assertNotNull(moment.getUpdatedAt());
    }

    @Test
    void testFromDTOWithNegativeEmotion() {
        // Arrange
        MomentDTO dto = new MomentDTO(
            "Sad Moment",
            "Feeling down today",
            Emotion.TRISTEZA,
            LocalDate.of(2024, 1, 21),
            false
        );

        // Act
        Moment moment = MomentMapper.fromDTO(dto);

        // Assert
        assertNotNull(moment);
        assertEquals("Sad Moment", moment.getTitle());
        assertEquals("Feeling down today", moment.getDescription());
        assertEquals(Emotion.TRISTEZA, moment.getEmotion());
        assertEquals(LocalDate.of(2024, 1, 21), moment.getEventDate());
        assertFalse(moment.isGood());
    }

    @Test
    void testFromDTOWithDifferentEmotions() {
        // Test con varias emociones diferentes
        Emotion[] emotions = {Emotion.IRA, Emotion.MIEDO, Emotion.ENVIDIA, Emotion.ANSIEDAD};
        
        for (Emotion emotion : emotions) {
            // Arrange
            MomentDTO dto = new MomentDTO(
                "Moment with " + emotion.getDisplayName(),
                "Description for " + emotion.name(),
                emotion,
                LocalDate.now(),
                true
            );

            // Act
            Moment moment = MomentMapper.fromDTO(dto);

            // Assert
            assertNotNull(moment);
            assertEquals(emotion, moment.getEmotion());
            assertEquals("Moment with " + emotion.getDisplayName(), moment.getTitle());
        }
    }

    @Test
    void testFromDTOWithEmptyStrings() {
        // Arrange
        MomentDTO dto = new MomentDTO(
            "",
            "",
            Emotion.ALEGRIA,
            LocalDate.of(2024, 1, 22),
            false
        );

        // Act
        Moment moment = MomentMapper.fromDTO(dto);

        // Assert
        assertNotNull(moment);
        assertEquals("", moment.getTitle());
        assertEquals("", moment.getDescription());
        assertEquals(Emotion.ALEGRIA, moment.getEmotion());
        assertEquals(LocalDate.of(2024, 1, 22), moment.getEventDate());
        assertFalse(moment.isGood());
    }

    @Test
    void testFromDTOVerifyTimestampsAreRecent() {
        // Arrange
        MomentDTO dto = new MomentDTO(
            "Timestamp Test",
            "Testing createdAt and updatedAt",
            Emotion.ALEGRIA,
            LocalDate.now(),
            true
        );

        // Act
        Moment moment = MomentMapper.fromDTO(dto);
        
        assertNotNull(moment.getCreatedAt());
        assertNotNull(moment.getUpdatedAt());
        
        assertTrue(moment.getCreatedAt().isAfter(java.time.LocalDateTime.now().minusSeconds(5)));
        assertTrue(moment.getUpdatedAt().isAfter(java.time.LocalDateTime.now().minusSeconds(5)));
        
        assertEquals(moment.getCreatedAt(), moment.getUpdatedAt());
    }

    @Test
void testFromDTOMultipleCallsCreateDifferentInstances() {
    // Arrange
    MomentDTO dto = new MomentDTO(
        "Same DTO",
        "Multiple calls",
        Emotion.ALEGRIA,
        LocalDate.now(),
        true
    );

    // Act
    Moment moment1 = MomentMapper.fromDTO(dto);
    Moment moment2 = MomentMapper.fromDTO(dto);

    // Assert
    assertNotSame(moment1, moment2);
    
    assertEquals(moment1.getTitle(), moment2.getTitle());
    assertEquals(moment1.getDescription(), moment2.getDescription());
    assertEquals(moment1.getEmotion(), moment2.getEmotion());
    assertEquals(moment1.getEventDate(), moment2.getEventDate());
    assertEquals(moment1.isGood(), moment2.isGood());
    
    assertNotNull(moment1.getCreatedAt());
    assertNotNull(moment1.getUpdatedAt());
    assertNotNull(moment2.getCreatedAt());
    assertNotNull(moment2.getUpdatedAt());
    
    assertTrue(moment1.getCreatedAt().isAfter(java.time.LocalDateTime.now().minusSeconds(1)));
    assertTrue(moment2.getCreatedAt().isAfter(java.time.LocalDateTime.now().minusSeconds(1)));
    
    assertEquals(moment1.getCreatedAt(), moment1.getUpdatedAt());
    assertEquals(moment2.getCreatedAt(), moment2.getUpdatedAt());
}

    @Test
    void testFromDTOWithNostalgiaEmotion() {
        // Arrange
        MomentDTO dto = new MomentDTO(
            "Nostalgic Day",
            "Remembering old times",
            Emotion.NOSTALGIA,
            LocalDate.of(2020, 5, 15),
            true
        );

        // Act
        Moment moment = MomentMapper.fromDTO(dto);

        // Assert
        assertNotNull(moment);
        assertEquals("Nostalgic Day", moment.getTitle());
        assertEquals("Remembering old times", moment.getDescription());
        assertEquals(Emotion.NOSTALGIA, moment.getEmotion());
        assertEquals(LocalDate.of(2020, 5, 15), moment.getEventDate());
        assertTrue(moment.isGood());
        assertEquals(10, moment.getEmotion().getOption()); // Verificar el option de NOSTALGIA
        assertEquals("Nostalgia", moment.getEmotion().getDisplayName());
    }

    @Test
    void testFromDTOWithBoredomEmotion() {
        // Arrange
        MomentDTO dto = new MomentDTO(
            "Boring Day",
            "Nothing to do",
            Emotion.ABURRIMIENTO,
            LocalDate.of(2024, 2, 1),
            false
        );

        // Act
        Moment moment = MomentMapper.fromDTO(dto);

        // Assert
        assertNotNull(moment);
        assertEquals("Boring Day", moment.getTitle());
        assertEquals("Nothing to do", moment.getDescription());
        assertEquals(Emotion.ABURRIMIENTO, moment.getEmotion());
        assertEquals(LocalDate.of(2024, 2, 1), moment.getEventDate());
        assertFalse(moment.isGood());
        assertEquals(9, moment.getEmotion().getOption()); // Verificar el option de ABURRIMIENTO
        assertEquals("Aburrimiento", moment.getEmotion().getDisplayName());
    }

    @Test
    void testFromDTOWithDisgustEmotion() {
        // Arrange
        MomentDTO dto = new MomentDTO(
            "Disgusting Moment",
            "Something unpleasant",
            Emotion.ASCO,
            LocalDate.of(2024, 1, 25),
            false
        );

        // Act
        Moment moment = MomentMapper.fromDTO(dto);

        // Assert
        assertNotNull(moment);
        assertEquals("Disgusting Moment", moment.getTitle());
        assertEquals("Something unpleasant", moment.getDescription());
        assertEquals(Emotion.ASCO, moment.getEmotion());
        assertEquals(LocalDate.of(2024, 1, 25), moment.getEventDate());
        assertFalse(moment.isGood());
        assertEquals(4, moment.getEmotion().getOption()); // Verificar el option de ASCO
        assertEquals("Asco", moment.getEmotion().getDisplayName());
    }
}