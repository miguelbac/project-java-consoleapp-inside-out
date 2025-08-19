package org.factoriaf5.project_inside_out.mappers;

import org.factoriaf5.project_inside_out.models.MomentDTO;
import org.factoriaf5.project_inside_out.utils.InputValidator;
import org.factoriaf5.project_inside_out.models.Emotion;
import org.factoriaf5.project_inside_out.models.Moment;
import java.time.LocalDate;

public class MomentMapper {
    public static Moment fromDTO(MomentDTO dto) {
        LocalDate date = InputValidator.parseDate(dto.getEventDate());
        if (date == null) {
            throw new IllegalArgumentException("Fecha inválida: " + dto.getEventDate());
        }

        if (!InputValidator.isValidEmotionOption(dto.getEmotionOption())) {
            throw new IllegalArgumentException("Opción de emoción inválida: " + dto.getEmotionOption());
        }

        Emotion emotion = Emotion.values()[dto.getEmotionOption() - 1];

        return new Moment(
            dto.getTitle(),
            dto.getDescription(),
            emotion,
            date,
            dto.isGood()
        );
    }
}
