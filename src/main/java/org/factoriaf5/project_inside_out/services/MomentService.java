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

    public void deleteMoment(int id) {
        repository.deleteById(id);
    }

    public List<Moment> getMomentsByEmotion(int emotionOption) {
        return repository.findAll().stream()
                .filter(moment -> moment.getEmotion().ordinal() + 1 == emotionOption)
                .toList();
    }

    public List<Moment> getMomentsByMonthYear(int month, int year) {
        return repository.findAll().stream()
                .filter(moment -> moment.getEventDate().getMonthValue() == month
                        && moment.getEventDate().getYear() == year)
                .toList();
    }

    public List<Moment> getMomentsByType(boolean isGood) {
        return repository.findAll().stream()
                .filter(moment -> moment.isGood() == isGood)
                .toList();
    }

}