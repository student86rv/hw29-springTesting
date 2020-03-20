package ua.epam.springapp.controller;

import com.google.gson.Gson;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.epam.springapp.model.Developer;
import ua.epam.springapp.service.DeveloperService;

import java.util.List;

import static ua.epam.springapp.utils.DeveloperGenerator.generateDevelopers;
import static ua.epam.springapp.utils.DeveloperGenerator.generateSingleDeveloper;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeveloperControllerTest {

    private static final String BASE_URL = "/api/v1/developers";
    private static final long DEFAULT_ID = 1L;

    private final DeveloperService developerService = Mockito.mock(DeveloperService.class);

    private final DeveloperController sut = new DeveloperController(developerService);

    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sut).build();

    private final Gson gson = new Gson();

    @Test
    public void shouldGetAllDevelopers() throws Exception {
        List<Developer> developersList = generateDevelopers(2);
        given(this.developerService.getAll()).willReturn(developersList);
        mockMvc.perform(get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is((int) developersList.get(0).getId())))
                .andExpect(jsonPath("$[1].id", is((int) developersList.get(1).getId())))
                .andExpect(jsonPath("$[0].name", is(developersList.get(0).getName())))
                .andExpect(jsonPath("$[1].name", is(developersList.get(1).getName())));
    }

    @Test
    public void shouldGetDeveloper() throws Exception {
        Developer developer = generateSingleDeveloper(DEFAULT_ID);
        given(this.developerService.get(DEFAULT_ID)).willReturn(developer);

        mockMvc.perform(get(BASE_URL + "/" + DEFAULT_ID)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) developer.getId())))
                .andExpect(jsonPath("$.name", is(developer.getName())));
    }

    @Test
    public void shouldAddDeveloper() throws Exception {
        Developer developer = generateSingleDeveloper(DEFAULT_ID);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(developer)))
                .andExpect(status().isCreated());
        }

    @Test
    public void shouldUpdateDeveloper() throws Exception {
        Developer developer = generateSingleDeveloper(DEFAULT_ID);
        given(this.developerService.update(DEFAULT_ID, developer)).willReturn(true);

        mockMvc.perform(put(BASE_URL + "/" + DEFAULT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(developer)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteDeveloper() throws Exception {
        Developer developer = generateSingleDeveloper(DEFAULT_ID);
        given(this.developerService.remove(DEFAULT_ID)).willReturn(developer);

        mockMvc.perform(delete(BASE_URL + "/" + DEFAULT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(developer)))
                .andExpect(status().isOk());
    }
}
