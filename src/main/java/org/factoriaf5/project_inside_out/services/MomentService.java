package org.factoriaf5.project_inside_out.services;

import org.factoriaf5.project_inside_out.mappers.MomentMapper;
import org.factoriaf5.project_inside_out.models.Moment;
import org.factoriaf5.project_inside_out.models.MomentDTO;
import org.factoriaf5.project_inside_out.repositories.MomentRepository;

import java.util.List;

public class MomentService {
    private final MomentRepository repository;

    public MomentService(MomentRepository repository) {
        this.repository = repository;
    }

    public Moment addMoment(MomentDTO dto) {
        Moment moment = MomentMapper.fromDTO(dto);
        return repository.save(moment);
    }

    public List<Moment> getAllMoments() {
        return repository.findAll();
    }

}