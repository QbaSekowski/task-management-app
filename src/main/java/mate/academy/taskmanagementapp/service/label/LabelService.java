package mate.academy.taskmanagementapp.service.label;

import java.util.List;
import mate.academy.taskmanagementapp.dto.label.CreateLabelRequestDto;
import mate.academy.taskmanagementapp.dto.label.LabelDto;
import org.springframework.data.domain.Pageable;

public interface LabelService {
    LabelDto createLabel(CreateLabelRequestDto createLabelRequestDto);

    List<LabelDto> getAllLabels(Pageable pageable);

    LabelDto updateLabel(Long labelId, CreateLabelRequestDto createLabelRequestDto);

    void deleteLabel(Long labelId);
}
