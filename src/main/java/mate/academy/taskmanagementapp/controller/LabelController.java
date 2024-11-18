package mate.academy.taskmanagementapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.taskmanagementapp.dto.label.CreateLabelRequestDto;
import mate.academy.taskmanagementapp.dto.label.LabelDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/labels")
@Tag(name = "Label management", description = "Endpoints for managing labels")
public class LabelController {
    //private final LabelService labelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new label", description = "Create a new label "
            + "providing name and color")
    public LabelDto createLabel(@RequestBody @Valid CreateLabelRequestDto createLabelRequestDto) {
        //return labelService.createLabel(createLabelRequestDto);
    }

    @GetMapping
    @Operation(summary = "Get all labels", description = "Get all labels")
    public List<LabelDto> getAllLabels() {
        //return labelService.getAllLabels();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update label", description = "Update label with given Id")
    public LabelDto updateLabel(@PathVariable Long id,
                                @RequestBody CreateLabelRequestDto createLabelRequestDto) {
        //return labelService.updateLabel(id, createLabelRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete label", description = "Delete label with given Id")
    public void deleteLabel(@PathVariable Long id) {
        //labelService.deleteLabel(id);
    }
}
