package org.factoriaf5.project_inside_out.repositories;

import org.factoriaf5.project_inside_out.models.Moment;

import java.util.List;

public interface MomentRepository {
    void save(Moment moment);
    List<Moment> findAll();
    void deleteById(int id);
}