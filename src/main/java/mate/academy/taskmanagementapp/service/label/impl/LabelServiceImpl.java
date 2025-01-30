package mate.academy.taskmanagementapp.service.label.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.taskmanagementapp.dto.label.CreateLabelRequestDto;
import mate.academy.taskmanagementapp.dto.label.LabelDto;
import mate.academy.taskmanagementapp.exception.EntityNotFoundException;
import mate.academy.taskmanagementapp.mapper.LabelMapper;
import mate.academy.taskmanagementapp.model.Label;
import mate.academy.taskmanagementapp.repository.label.LabelRepository;
import mate.academy.taskmanagementapp.service.label.LabelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService {
    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    @Transactional
    @Override
    public LabelDto createLabel(CreateLabelRequestDto createLabelRequestDto) {
        Label label = labelRepository.save(labelMapper.toModel(createLabelRequestDto));
        return labelMapper.toDto(label);
    }

    @Override
    public List<LabelDto> getAllLabels() {
        return labelRepository.findAll().stream()
                .map(labelMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public LabelDto updateLabel(Long labelId, CreateLabelRequestDto createLabelRequestDto) {
        Label label = labelRepository.findById(labelId).orElseThrow(
                () -> new EntityNotFoundException("Label with id " + labelId + " not found"));
        Label updatedLabel = labelMapper.updateLabel(createLabelRequestDto, label);
        return labelMapper.toDto(labelRepository.save(updatedLabel));
    }

    @Transactional
    @Override
    public void deleteLabel(Long labelId) {
        labelRepository.deleteById(labelId);
    }
}
