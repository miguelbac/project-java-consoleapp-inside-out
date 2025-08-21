package org.factoriaf5.project_inside_out.repositories;

import org.factoriaf5.project_inside_out.db.MomentDB;
import org.factoriaf5.project_inside_out.models.Moment;
import java.time.LocalDateTime;
import java.util.List;

public class InMemoryMomentRepository implements MomentRepository {
    private final MomentDB db;
    private int counter = 1;

    public InMemoryMomentRepository(MomentDB db) {
        this.db = db;
    }

    @Override
    public Moment save(Moment moment) {
        // Asignar ID si no lo tiene
        if (moment.getId() == 0) {
            moment.setId(counter++);
        }
        
        Moment newMoment = new Moment(
            moment.getId(),
            moment.getTitle(),
            moment.getDescription(),
            moment.getEmotion(),
            moment.getEventDate(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            moment.isGood()
        );
        db.add(newMoment);
        return newMoment;
    }

    @Override
    public List<Moment> findAll() {
        return db.getAll();
    }

    @Override
    public void deleteById(int id) {
        db.deleteById(id);
    }
    
    @Override
    public int getNextId() {
        return counter;
    }
}
