package org.factoriaf5.project_inside_out.services;

import org.factoriaf5.project_inside_out.models.Moment;
import org.factoriaf5.project_inside_out.models.MomentDTO;
import org.factoriaf5.project_inside_out.models.Emotion;
import org.factoriaf5.project_inside_out.repositories.MomentRepository;
import org.factoriaf5.project_inside_out.mappers.MomentMapper;
import org.factoriaf5.project_inside_out.utils.MomentExporter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Moment> filterByEmotion(Emotion emotion) {
        return repository.findAll().stream()
                .filter(m -> m.getEmotion() == emotion)
                .collect(Collectors.toList());
    }
    
    public List<Moment> filterByDate(LocalDate date) {
        return repository.findAll().stream()
                .filter(m -> m.getEventDate().equals(date))
                .collect(Collectors.toList());
    }
    
    public List<Moment> filterByType(boolean isGood) {
        return repository.findAll().stream()
                .filter(m -> m.isGood() == isGood)
                .collect(Collectors.toList());
    }
    
    public void exportToCSV(String filename) {
        List<Moment> moments = getAllMoments();
        MomentExporter.exportToCSV(moments, filename);
    }
}
