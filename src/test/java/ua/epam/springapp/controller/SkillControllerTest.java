package ua.epam.springapp.controller;

import com.google.gson.Gson;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.epam.springapp.model.Skill;
import ua.epam.springapp.service.SkillService;

import java.util.List;

import static ua.epam.springapp.utils.SkillGenerator.generateSkills;
import static ua.epam.springapp.utils.SkillGenerator.generateSingleSkill;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SkillControllerTest {

    private static final String BASE_URL = "/api/v1/skills";
    private static final long DEFAULT_ID = 1L;

    private final SkillService skillService = Mockito.mock(SkillService.class);

    private final SkillController sut = new SkillController(skillService);

    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sut).build();

    private final Gson gson = new Gson();

    @Test
    public void shouldGetAllSkills() throws Exception {
        List<Skill> skillsList = generateSkills(2);
        given(this.skillService.getAll()).willReturn(skillsList);
        mockMvc.perform(get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is((int) skillsList.get(0).getId())))
                .andExpect(jsonPath("$[1].id", is((int) skillsList.get(1).getId())))
                .andExpect(jsonPath("$[0].name", is(skillsList.get(0).getName())))
                .andExpect(jsonPath("$[1].name", is(skillsList.get(1).getName())));
    }

    @Test
    public void shouldGetSkill() throws Exception{
        Skill skill = generateSingleSkill(DEFAULT_ID);
        given(this.skillService.get(DEFAULT_ID)).willReturn(skill);

        mockMvc.perform(get(BASE_URL + "/" + DEFAULT_ID)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) skill.getId())))
                .andExpect(jsonPath("$.name", is(skill.getName())));
    }

    @Test
    public void shouldAddSkill() throws Exception {
        Skill skill = generateSingleSkill(DEFAULT_ID);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(skill)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldUpdateSkill() throws Exception {
        Skill skill = generateSingleSkill(DEFAULT_ID);
        given(this.skillService.update(DEFAULT_ID, skill)).willReturn(true);

        mockMvc.perform(put(BASE_URL + "/" + DEFAULT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(skill)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteSkill() throws Exception {
        Skill skill = generateSingleSkill(DEFAULT_ID);
        given(this.skillService.remove(DEFAULT_ID)).willReturn(skill);

        mockMvc.perform(delete(BASE_URL + "/" + DEFAULT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(skill)))
                .andExpect(status().isOk());
    }
}
