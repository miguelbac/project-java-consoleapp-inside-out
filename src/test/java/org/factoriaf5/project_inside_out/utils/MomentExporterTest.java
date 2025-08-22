package org.factoriaf5.project_inside_out.utils;

import org.factoriaf5.project_inside_out.models.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MomentExporterTest {

    @Test
    void exportToCSV_ShouldGenerateFile() throws Exception {
        Moment moment = new Moment(1, "Título", "Descripción", Emotion.ALEGRIA,
                LocalDate.of(2023, 5, 20), null, null, true);

        MomentExporter.exportToCSV(List.of(moment), "test_moments.csv");

        File file = new File("exports/test_moments.csv");
        assertTrue(file.exists());

        String content = Files.readString(file.toPath());
        assertTrue(content.contains("Título"));
        assertTrue(content.contains("Alegría"));
    }
}
