package org.factoriaf5.project_inside_out.utils;

import org.factoriaf5.project_inside_out.models.Moment;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MomentExporter {

    public static void exportToCSV(List<Moment> moments, String filename) {
        try {
            File folder = new File("exports");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            if (!filename.toLowerCase().endsWith(".csv")) {
                filename += ".csv";
            }

            File file = new File(folder, filename);

            try (FileWriter writer = new FileWriter(file)) {
                writer.write("ID,Título,Descripción,Emoción,Fecha,Tipo\n");
                for (Moment moment : moments) {
                    writer.write(String.format("%d,%s,%s,%s,%s,%s\n",
                            moment.getId(),
                            escapeCsv(moment.getTitle()),
                            escapeCsv(moment.getDescription()),
                            moment.getEmotion(),
                            moment.getEventDate(),
                            moment.isGood() ? "BUENO" : "MALO"
                    ));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error al exportar CSV: " + e.getMessage());
        }
    }

    private static String escapeCsv(String text) {
        if (text == null) return "";
        String escaped = text.replace("\"", "\"\"");
        return "\"" + escaped + "\"";
    }
}
