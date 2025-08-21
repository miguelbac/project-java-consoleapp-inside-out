package org.factoriaf5.project_inside_out.mappers;

import org.factoriaf5.project_inside_out.models.MomentDTO;
import org.factoriaf5.project_inside_out.models.Moment;

public class MomentMapper {
    
    public static Moment fromDTO(MomentDTO dto) {
        return new Moment(
            dto.getTitle(),
            dto.getDescription(),
            dto.getEmotion(),
            dto.getEventDate(),
            dto.isGood()
        );
    }
}