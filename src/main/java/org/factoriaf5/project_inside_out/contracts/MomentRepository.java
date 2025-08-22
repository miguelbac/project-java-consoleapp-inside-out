package org.factoriaf5.project_inside_out.contracts;

import org.factoriaf5.project_inside_out.models.Moment;
import java.util.List;

public interface MomentRepository {
    Moment save(Moment moment);
    List<Moment> findAll();
    void deleteById(int id);
    int getNextId();
}