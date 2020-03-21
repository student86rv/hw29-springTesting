package ua.epam.springapp.service;

import org.junit.Test;
import org.mockito.Mockito;
import ua.epam.springapp.exception.EntityNotFoundException;
import ua.epam.springapp.model.Skill;
import ua.epam.springapp.repository.SkillRepository;

import java.util.ArrayList;
import java.util.List;

import static ua.epam.springapp.utils.SkillGenerator.generateSingleSkill;
import static ua.epam.springapp.utils.SkillGenerator.generateSkills;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class SkillServiceImplTest {

    private static final long DEFAULT_ID = 1L;

    private final SkillRepository skillRepository = Mockito.mock(SkillRepository.class);

    private final SkillServiceImpl sut = new SkillServiceImpl(skillRepository);

    @Test
    public void shouldGetAllSkills() {
        int skillsSize = 10;
        List<Skill> skills = generateSkills(skillsSize);
        doReturn(skills).when(skillRepository).getAll();

        List<Skill> foundSkills = sut.getAll();

        verify(skillRepository).getAll();
        assertThat(foundSkills, hasSize(skillsSize));

        skills.forEach(
                skill -> assertThat(foundSkills, hasItem(allOf(
                        hasProperty("id", is(skill.getId())),
                        hasProperty("name", is(skill.getName()))))
                )
        );
    }

    @Test
    public void shouldThrowWhenSkillsListIsEmpty() {
        doReturn(new ArrayList<Skill>()).when(skillRepository).getAll();

        try {
            sut.getAll();
        } catch (Exception e) {
            assertThat(e, instanceOf(EntityNotFoundException.class));
        }

        verify(skillRepository).getAll();
    }

    @Test
    public void shouldGetSkill() {
        Skill skill = generateSingleSkill(DEFAULT_ID);
        doReturn(skill).when(skillRepository).get(DEFAULT_ID);

        Skill foundSkill = sut.get(DEFAULT_ID);

        verify(skillRepository).get(DEFAULT_ID);

        assertThat(foundSkill, allOf(
                hasProperty("id", is(skill.getId())),
                hasProperty("name", is(skill.getName())))
        );
    }

    @Test
    public void shouldThrowWhenSkillNotFound() {
        doReturn(null).when(skillRepository).get(DEFAULT_ID);

        try {
            sut.get(DEFAULT_ID);
        } catch (Exception e) {
            assertThat(e, instanceOf(EntityNotFoundException.class));
        }

        verify(skillRepository).get(DEFAULT_ID);
    }

    @Test
    public void shouldAddSkill() {
        Skill skill = generateSingleSkill(DEFAULT_ID);
        sut.add(skill);

        verify(skillRepository).add(skill);
    }

    @Test
    public void shouldUpdateSkill() {
        Skill skill = generateSingleSkill(DEFAULT_ID);
        doReturn(skill).when(skillRepository).get(DEFAULT_ID);
        doReturn(true).when(skillRepository).update(skill);

        boolean updated = sut.update(DEFAULT_ID, skill);

        verify(skillRepository).update(skill);

        assertThat(updated, is(true));
    }

    @Test
    public void shouldThrowWhenUpdatedSkillNotFound() {
        Skill skill = generateSingleSkill(DEFAULT_ID);
        doReturn(null).when(skillRepository).get(DEFAULT_ID);

        try {
            sut.update(DEFAULT_ID, skill);
        } catch (Exception e) {
            assertThat(e, instanceOf(EntityNotFoundException.class));
        }

        verify(skillRepository).get(DEFAULT_ID);
    }

    @Test
    public void shouldDeleteSkill() {
        Skill skill = generateSingleSkill(DEFAULT_ID);
        doReturn(skill).when(skillRepository).remove(DEFAULT_ID);

        Skill deletedAccount = sut.remove(DEFAULT_ID);

        verify(skillRepository).remove(DEFAULT_ID);

        assertThat(deletedAccount, allOf(
                hasProperty("id", is(skill.getId())),
                hasProperty("name", is(skill.getName())))
        );
    }

    @Test
    public void shouldThrowWhenDeletedSkillNotFound() {
        doReturn(null).when(skillRepository).get(DEFAULT_ID);

        try {
            sut.remove(DEFAULT_ID);
        } catch (Exception e) {
            assertThat(e, instanceOf(EntityNotFoundException.class));
        }

        verify(skillRepository).remove(DEFAULT_ID);
    }
}
