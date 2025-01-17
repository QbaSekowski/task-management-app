package mate.academy.taskmanagementapp.service.label;

import java.util.List;
import mate.academy.taskmanagementapp.dto.label.CreateLabelRequestDto;
import mate.academy.taskmanagementapp.dto.label.LabelDto;

public interface LabelService {
    LabelDto createLabel(CreateLabelRequestDto createLabelRequestDto);

    List<LabelDto> getAllLabels();

    LabelDto updateLabel(Long labelId, CreateLabelRequestDto createLabelRequestDto);

    void deleteLabel(Long labelId);
}
