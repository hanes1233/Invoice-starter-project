package cz.itnetwork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.itnetwork.helpers.DataMaker;
import cz.itnetwork.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = PersonController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper;
    private DataMaker dataMaker;

    @BeforeEach
    public void setUp() {
        this.dataMaker = new DataMaker();
    }

    @Test
    public void PersonController_addPerson_ReturnsPersonDTO() throws Exception {
        given(personService.addPerson(ArgumentMatchers.any())).willAnswer(invocation ->
            invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dataMaker.createPersonDTO())));
        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
