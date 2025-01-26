package mate.academy.taskmanagementapp.controller.label;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.sql.DataSource;
import mate.academy.taskmanagementapp.dto.label.CreateLabelRequestDto;
import mate.academy.taskmanagementapp.dto.label.LabelDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LabelControllerTest {
    @Autowired
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext,
            @Autowired DataSource dataSource) throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Create a new label")
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Sql(scripts = "classpath:database/label/add-three-labels.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/label/remove-all-labels.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void createNewLabel_CorrectRequestDto_ReturnsNewLabelDto() throws Exception {
        LabelDto expectedLabel = new LabelDto(4L, "New label", "New color");
        CreateLabelRequestDto createLabelRequestDto = new CreateLabelRequestDto("New label",
                "New color");
        String requestJson = objectMapper.writeValueAsString(createLabelRequestDto);
        MvcResult result = mockMvc.perform(post("/api/labels")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        LabelDto actualLabel = objectMapper.readValue(result.getResponse().getContentAsString(),
                LabelDto.class);
        assertNotNull(actualLabel);
        assertEquals(expectedLabel, actualLabel);
    }

    @Test
    @DisplayName("Retrieve all labels")
    @WithMockUser(username = "user", authorities = {"USER"})
    @Sql(scripts = "classpath:database/label/add-three-labels.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/label/remove-all-labels.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllLabels_ThreeLabelsInDb_ReturnsAllLabels() throws Exception {
        List<LabelDto> expectedLabels = createThreeLabels();
        MvcResult result = mockMvc.perform(get("/api/labels")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<LabelDto> actualLabels = Arrays.stream(objectMapper.readValue(
                        result.getResponse().getContentAsString(), LabelDto[].class))
                .sorted(Comparator.comparingLong(LabelDto::id))
                .toList();
        assertNotNull(actualLabels);
        assertEquals(expectedLabels.get(0), actualLabels.get(0));
        assertEquals(expectedLabels.get(1), actualLabels.get(1));
        assertEquals(expectedLabels.get(2), actualLabels.get(2));
    }

    @Test
    @DisplayName("Update label")
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Sql(scripts = "classpath:database/label/add-three-labels.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/label/remove-all-labels.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateLabel_CorrectIdAndRequestDto_ReturnsUpdatedLabelDto() throws Exception {
        CreateLabelRequestDto createLabelRequestDto = new CreateLabelRequestDto("New label",
                "New color");
        LabelDto expectedLabel = new LabelDto(3L, "New label", "New color");
        String requestJson = objectMapper.writeValueAsString(createLabelRequestDto);
        MvcResult result = mockMvc.perform(put("/api/labels/3")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        LabelDto actualLabel = objectMapper.readValue(result.getResponse().getContentAsString(),
                LabelDto.class);
        assertNotNull(actualLabel);
        assertEquals(expectedLabel, actualLabel);
    }

    @Test
    @DisplayName("Delete label")
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Sql(scripts = "classpath:database/label/add-three-labels.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/label/remove-all-labels.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteLabel_CorrectId_ReturnsNoContentStatus() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/labels/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
        int actual = result.getResponse().getStatus();
        assertEquals(204, actual);
    }

    private List<LabelDto> createThreeLabels() {
        LabelDto label1 = new LabelDto(1L, "URGENT", "RED");
        LabelDto label2 = new LabelDto(2L, "NORMAL", "YELLOW");
        LabelDto label3 = new LabelDto(3L, "NOT URGENT", "BLUE");
        return List.of(label1, label2, label3);
    }
}
