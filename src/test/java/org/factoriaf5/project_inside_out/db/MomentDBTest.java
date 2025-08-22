package org.factoriaf5.project_inside_out.db;

import org.factoriaf5.project_inside_out.models.Emotion;
import org.factoriaf5.project_inside_out.models.Moment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MomentDBTest {

    private MomentDB momentDB;
    private Moment moment1;
    private Moment moment2;
    private Moment moment3;

    @BeforeEach
    void setUp() {
        momentDB = new MomentDB();
        
        moment1 = new Moment("Moment 1", "Description 1", Emotion.ALEGRIA, 
                           LocalDate.of(2024, 1, 15), true);
        moment1.setId(1);
        
        moment2 = new Moment("Moment 2", "Description 2", Emotion.TRISTEZA, 
                           LocalDate.of(2024, 1, 16), false);
        moment2.setId(2);
        
        moment3 = new Moment("Moment 3", "Description 3", Emotion.IRA, 
                           LocalDate.of(2024, 1, 17), true);
        moment3.setId(3);
    }

    @Test
    void testAddMoment() {
        // Act
        momentDB.add(moment1);
        
        // Assert
        List<Moment> moments = momentDB.getAll();
        assertEquals(1, moments.size());
        assertEquals(moment1, moments.get(0));
        assertEquals(1, moments.get(0).getId());
        assertEquals("Moment 1", moments.get(0).getTitle());
        assertEquals(Emotion.ALEGRIA, moments.get(0).getEmotion());
    }

    @Test
    void testAddMultipleMoments() {
        // Act
        momentDB.add(moment1);
        momentDB.add(moment2);
        momentDB.add(moment3);
        
        // Assert
        List<Moment> moments = momentDB.getAll();
        assertEquals(3, moments.size());
        assertTrue(moments.contains(moment1));
        assertTrue(moments.contains(moment2));
        assertTrue(moments.contains(moment3));
        
        assertEquals(Emotion.ALEGRIA, moments.get(0).getEmotion());
        assertEquals(Emotion.TRISTEZA, moments.get(1).getEmotion());
        assertEquals(Emotion.IRA, moments.get(2).getEmotion());
    }

    @Test
    void testGetAllReturnsNewList() {
        // Arrange
        momentDB.add(moment1);
        momentDB.add(moment2);
        
        // Act
        List<Moment> firstCall = momentDB.getAll();
        List<Moment> secondCall = momentDB.getAll();
        
        // Assert
        assertNotSame(firstCall, secondCall);
        assertEquals(firstCall, secondCall);
    }

    @Test
    void testGetAllWhenEmpty() {
        // Act
        List<Moment> moments = momentDB.getAll();
        
        // Assert
        assertNotNull(moments);
        assertTrue(moments.isEmpty());
    }

    @Test
    void testDeleteByIdExistingMoment() {
        // Arrange
        momentDB.add(moment1);
        momentDB.add(moment2);
        momentDB.add(moment3);
        
        // Act
        boolean result = momentDB.deleteById(2);
        
        // Assert
        assertTrue(result);
        List<Moment> moments = momentDB.getAll();
        assertEquals(2, moments.size());
        assertFalse(moments.contains(moment2));
        assertTrue(moments.contains(moment1));
        assertTrue(moments.contains(moment3));
    }

    @Test
    void testDeleteByIdNonExistingMoment() {
        // Arrange
        momentDB.add(moment1);
        momentDB.add(moment3);
        
        // Act
        boolean result = momentDB.deleteById(999);
        
        // Assert
        assertFalse(result);
        List<Moment> moments = momentDB.getAll();
        assertEquals(2, moments.size());
        assertTrue(moments.contains(moment1));
        assertTrue(moments.contains(moment3));
    }

    @Test
    void testDeleteByIdFromEmptyList() {
        // Act
        boolean result = momentDB.deleteById(1);
        
        // Assert
        assertFalse(result);
        List<Moment> moments = momentDB.getAll();
        assertTrue(moments.isEmpty());
    }

    @Test
    void testDeleteByIdMultipleMomentsSameId() {
        // Arrange
        Moment moment4 = new Moment("Duplicate Moment", "Duplicate description", 
                                  Emotion.ENVIDIA, LocalDate.of(2024, 1, 18), true);
        moment4.setId(1);
        
        momentDB.add(moment1);
        momentDB.add(moment4);
        
        // Act
        boolean result = momentDB.deleteById(1);
        
        // Assert
        assertTrue(result);
        List<Moment> moments = momentDB.getAll();
        assertTrue(moments.isEmpty());
    }

    @Test
    void testIntegrationAddGetAllDelete() {
        assertTrue(momentDB.getAll().isEmpty());
        
        momentDB.add(moment1);
        momentDB.add(moment2);
        
        List<Moment> momentsAfterAdd = momentDB.getAll();
        assertEquals(2, momentsAfterAdd.size());
        assertEquals(1, momentsAfterAdd.get(0).getId());
        assertEquals(2, momentsAfterAdd.get(1).getId());
        assertEquals(Emotion.ALEGRIA, momentsAfterAdd.get(0).getEmotion());
        assertEquals(Emotion.TRISTEZA, momentsAfterAdd.get(1).getEmotion());
        
        boolean deleteResult = momentDB.deleteById(1);
        
        assertTrue(deleteResult);
        List<Moment> momentsAfterDelete = momentDB.getAll();
        assertEquals(1, momentsAfterDelete.size());
        assertEquals(moment2, momentsAfterDelete.get(0));
        assertEquals(2, momentsAfterDelete.get(0).getId());
        assertEquals(Emotion.TRISTEZA, momentsAfterDelete.get(0).getEmotion());
    }

    @Test
    void testMomentPropertiesArePreserved() {
        // Arrange
        Moment complexMoment = new Moment("Complex Moment", "Detailed description", 
                                        Emotion.MIEDO, LocalDate.of(2024, 2, 1), true);
        complexMoment.setId(10);
        
        // Act
        momentDB.add(complexMoment);
        Moment retrieved = momentDB.getAll().get(0);
        
        // Assert
        assertEquals(10, retrieved.getId());
        assertEquals("Complex Moment", retrieved.getTitle());
        assertEquals("Detailed description", retrieved.getDescription());
        assertEquals(Emotion.MIEDO, retrieved.getEmotion());
        assertEquals(LocalDate.of(2024, 2, 1), retrieved.getEventDate());
        assertTrue(retrieved.isGood());
    }

    @Test
    void testDifferentEmotions() {
        Moment momentWithAnxiety = new Moment("Anxious moment", "Feeling anxious", 
                                            Emotion.ANSIEDAD, LocalDate.now(), false);
        momentWithAnxiety.setId(4);
        
        Moment momentWithShame = new Moment("Shameful moment", "Feeling ashamed", 
                                          Emotion.VERGUENZA, LocalDate.now(), false);
        momentWithShame.setId(5);
        
        momentDB.add(momentWithAnxiety);
        momentDB.add(momentWithShame);
        
        List<Moment> moments = momentDB.getAll();
        assertEquals(2, moments.size());
        assertEquals(Emotion.ANSIEDAD, moments.get(0).getEmotion());
        assertEquals(Emotion.VERGUENZA, moments.get(1).getEmotion());
        assertEquals("Ansiedad", moments.get(0).getEmotion().getDisplayName());
        assertEquals("Vergüenza", moments.get(1).getEmotion().getDisplayName());
    }

    @Test
    void testDeleteLastMoment() {
        // Arrange
        momentDB.add(moment1);
        
        // Act
        boolean result = momentDB.deleteById(1);
        
        // Assert
        assertTrue(result);
        assertTrue(momentDB.getAll().isEmpty());
    }

    @Test
    void testAddAfterDelete() {
        // Arrange
        momentDB.add(moment1);
        momentDB.deleteById(1);
        
        // Act
        momentDB.add(moment2);
        
        // Assert
        List<Moment> moments = momentDB.getAll();
        assertEquals(1, moments.size());
        assertEquals(moment2, moments.get(0));
        assertEquals(Emotion.TRISTEZA, moments.get(0).getEmotion());
    }
}