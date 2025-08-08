package org.factoriaf5.project_inside_out.repositories;

import org.factoriaf5.project_inside_out.models.Moment;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class InMemoryMomentRepository implements MomentRepository {
    private final List<Moment> moments = new LinkedList<>();
    private int counter = 1;

    @Override
    public Moment save(Moment moment) {
        Moment newMoment = new Moment(
            counter++,
            moment.getTitle(),
            moment.getDescription(),
            moment.getEmotion(),
            moment.getEventDate(),
            LocalDateTime.now(),
            LocalDateTime.now()
        );

        moments.add(newMoment);
        return newMoment;
    }

    @Override
    public List<Moment> findAll() {
        return new LinkedList<>(moments);
    }

    @Override
    public void deleteById(int id) {
        moments.removeIf(moment -> moment.getId() == id);
    }
}