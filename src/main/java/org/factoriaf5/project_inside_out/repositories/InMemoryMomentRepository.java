package org.factoriaf5.project_inside_out.repositories;

import org.factoriaf5.project_inside_out.models.Moment;

import java.util.ArrayList;
import java.util.List;

public class InMemoryMomentRepository implements MomentRepository {
    private final List<Moment> moments = new ArrayList<>();

    @Override
    public void save(Moment moment) {
        moments.add(moment);
    }

    @Override
    public List<Moment> findAll() {
        return new ArrayList<>(moments);
    }

    @Override
    public void deleteById(int id) {
        moments.removeIf(moment -> moment.getId() == id);
    }
}