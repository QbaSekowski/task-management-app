package mate.academy.taskmanagementapp.service.label;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import mate.academy.taskmanagementapp.dto.label.CreateLabelRequestDto;
import mate.academy.taskmanagementapp.dto.label.LabelDto;
import mate.academy.taskmanagementapp.mapper.LabelMapper;
import mate.academy.taskmanagementapp.model.Label;
import mate.academy.taskmanagementapp.repository.label.LabelRepository;
import mate.academy.taskmanagementapp.service.label.impl.LabelServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
public class LabelServiceTest {
    @Mock
    private LabelRepository labelRepository;
    @Mock
    private LabelMapper labelMapper;
    @InjectMocks
    private LabelServiceImpl labelService;

    @Test
    @DisplayName("Create label")
    public void createLabel_CorrectLabelRequest_ReturnsLabelDto() {
        Label newLabel = createLabel();
        CreateLabelRequestDto createLabelRequestDto = new CreateLabelRequestDto("test",
                "Test color");
        LabelDto expected = new LabelDto(newLabel.getId(), newLabel.getName(), newLabel.getColor());
        when(labelMapper.toDto(newLabel)).thenReturn(expected);
        when(labelMapper.toModel(createLabelRequestDto)).thenReturn(newLabel);
        when(labelRepository.save(newLabel)).thenReturn(newLabel);
        LabelDto actual = labelService.createLabel(createLabelRequestDto);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Retrieve all labels")
    public void getAllLabels_CorrectLabelRequest_ReturnsAllLabelDtos() {
        List<Label> labels = createThreeLabels();
        List<LabelDto> expected = createThreeLabelDtos();
        PageImpl<Label> labelsPage
                = new PageImpl<>(labels, PageRequest.of(0, 10), labels.size());
        when(labelRepository.findAll(PageRequest.of(0, 10))).thenReturn(labelsPage);
        when(labelMapper.toDto(labels.get(0))).thenReturn(expected.get(0));
        when(labelMapper.toDto(labels.get(1))).thenReturn(expected.get(1));
        when(labelMapper.toDto(labels.get(2))).thenReturn(expected.get(2));
        List<LabelDto> actual = labelService.getAllLabels(PageRequest.of(0, 10));
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update label")
    public void updateLabel_CorrectLabelRequest_ReturnsLabelDto() {
        Label label = createLabel();
        CreateLabelRequestDto createLabelRequestDto
                = new CreateLabelRequestDto("Updated name", "Updated color");
        Label updatedLabel = new Label();
        updatedLabel.setId(label.getId());
        updatedLabel.setName(createLabelRequestDto.name());
        updatedLabel.setColor(createLabelRequestDto.color());
        LabelDto expected = new LabelDto(updatedLabel.getId(), updatedLabel.getName(),
                updatedLabel.getColor());
        when(labelMapper.updateLabel(createLabelRequestDto, label)).thenReturn(updatedLabel);
        when(labelRepository.save(updatedLabel)).thenReturn(updatedLabel);
        when(labelMapper.toDto(updatedLabel)).thenReturn(expected);
        when(labelRepository.findById(label.getId())).thenReturn(Optional.of(label));
        LabelDto actual = labelService.updateLabel(label.getId(), createLabelRequestDto);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Delete label")
    public void deleteLabel_CorrectId_LabelDeleted() {
        Label label = createLabel();
        labelRepository.save(label);
        Long labelId = label.getId();
        labelService.deleteLabel(label.getId());
        assertFalse(labelRepository.findById(labelId).isPresent());
    }

    private Label createLabel() {
        Label label = new Label();
        label.setId(1L);
        label.setName("test");
        label.setColor("Test color");
        return label;
    }

    private List<Label> createThreeLabels() {
        Label label1 = new Label();
        label1.setId(1L);
        label1.setName("test1");
        label1.setColor("Test color1");
        Label label2 = new Label();
        label2.setId(2L);
        label2.setName("test2");
        label2.setColor("Test color2");
        Label label3 = new Label();
        label3.setId(3L);
        label3.setName("test3");
        label3.setColor("Test color3");
        return List.of(label1, label2, label3);
    }

    private List<LabelDto> createThreeLabelDtos() {
        LabelDto labelDto1 = new LabelDto(1L, "test1", "Test color1");
        LabelDto labelDto2 = new LabelDto(2L, "test2", "Test color2");
        LabelDto labelDto3 = new LabelDto(3L, "test3", "Test color3");
        return List.of(labelDto1, labelDto2, labelDto3);
    }
}
