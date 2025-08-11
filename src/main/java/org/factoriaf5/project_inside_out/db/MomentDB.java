package org.factoriaf5.project_inside_out.db;

import org.factoriaf5.project_inside_out.models.Moment;

import java.util.LinkedList;
import java.util.List;

public class MomentDB {
    private final List<Moment> moments = new LinkedList<>();

    public void add(Moment moment) {
        moments.add(moment);
    }

    public List<Moment> getAll() {
        return new LinkedList<>(moments);
    }

    public boolean deleteById(int id) {
        return moments.removeIf(m -> m.getId() == id);
    }

}
