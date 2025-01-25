package mate.academy.taskmanagementapp.controller.user;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.sql.DataSource;
import mate.academy.taskmanagementapp.dto.user.UserRegistrationRequestDto;
import mate.academy.taskmanagementapp.dto.user.UserResponseDto;
import mate.academy.taskmanagementapp.dto.user.UserRoleUpdateDto;
import mate.academy.taskmanagementapp.model.Role;
import mate.academy.taskmanagementapp.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
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
    @DisplayName("Retrieve user's info")
    @WithUserDetails("user3")
    @Sql(scripts = "classpath:database/user/add-three-users.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/user/remove-all-users.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getUserInfo_Ok() throws Exception {
        User user = new User();
        user.setId(3L);
        user.setUsername("user3");
        user.setEmail("user3@gmail.com");
        user.setFirstName("User3");
        user.setLastName("User3lastname");
        UserResponseDto expected = new UserResponseDto(user.getId(), user.getUsername(),
                user.getEmail(), user.getFirstName(), user.getLastName());
        MvcResult result = mockMvc.perform(get("/users/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        UserResponseDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), UserResponseDto.class);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update user's info")
    @WithUserDetails("user3")
    @Sql(scripts = "classpath:database/user/add-three-users.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/user/remove-all-users.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateUserInfo_Ok() throws Exception {
        UserRegistrationRequestDto userRegistrationRequestDto = new UserRegistrationRequestDto(
                "user3", "user3password", "user3password",
                "user3@gmail.com", "user3firstname", "Updated last name");
        UserResponseDto expected = new UserResponseDto(3L, userRegistrationRequestDto.username(),
                userRegistrationRequestDto.email(), userRegistrationRequestDto.firstName(),
                userRegistrationRequestDto.lastName());
        String jsonRequest = objectMapper.writeValueAsString(userRegistrationRequestDto);
        MvcResult result = mockMvc.perform(
                        put("/users/me")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        UserResponseDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), UserResponseDto.class);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update User's Role")
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Sql(scripts = "classpath:database/user/add-three-users.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/user/remove-all-users.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateUserRole_Ok() throws Exception {
        UserRoleUpdateDto userRoleUpdateDto = new UserRoleUpdateDto(Role.RoleName.ADMIN);
        String jsonRequest = objectMapper.writeValueAsString(userRoleUpdateDto);
        mockMvc.perform(put("/users/1/roles")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}
