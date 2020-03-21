package ua.epam.springapp.service;

import org.junit.Test;
import org.mockito.Mockito;
import ua.epam.springapp.exception.EntityNotFoundException;
import ua.epam.springapp.model.Developer;
import ua.epam.springapp.repository.DeveloperRepository;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static ua.epam.springapp.utils.DeveloperGenerator.generateDevelopers;
import static ua.epam.springapp.utils.DeveloperGenerator.generateSingleDeveloper;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class DeveloperServiceImplTest {

    private static final long DEFAULT_ID = 1L;

    private final DeveloperRepository developerRepository = Mockito.mock(DeveloperRepository.class);

    private final DeveloperServiceImpl sut = new DeveloperServiceImpl(developerRepository);

    @Test
    public void shouldGetAllDevelopers() {
        int developersSize = 10;
        List<Developer> developers = generateDevelopers(developersSize);
        doReturn(developers).when(developerRepository).getAll();

        List<Developer> foundDevelopers = sut.getAll();

        verify(developerRepository).getAll();
        assertThat(foundDevelopers, hasSize(developersSize));

        developers.forEach(
                developer -> assertThat(foundDevelopers, hasItem(allOf(
                        hasProperty("id", is(developer.getId())),
                        hasProperty("name", is(developer.getName())),
                        hasProperty("skills", is(developer.getSkills())),
                        hasProperty("account", is(developer.getAccount()))))
                )
        );
    }

    @Test
    public void shouldThrowWhenDevelopersListIsEmpty() {
        doReturn(new ArrayList<Developer>()).when(developerRepository).getAll();

        try {
            sut.getAll();
        } catch (Exception e) {
            assertThat(e, instanceOf(EntityNotFoundException.class));
        }

        verify(developerRepository).getAll();
    }

    @Test
    public void shouldGetDeveloper() {
        Developer developer = generateSingleDeveloper(DEFAULT_ID);
        doReturn(developer).when(developerRepository).get(DEFAULT_ID);

        Developer foundDeveloper = sut.get(DEFAULT_ID);

        verify(developerRepository).get(DEFAULT_ID);

        assertThat(foundDeveloper, allOf(
                hasProperty("id", is(developer.getId())),
                hasProperty("name", is(developer.getName())),
                hasProperty("skills", is(developer.getSkills())),
                hasProperty("account", is(developer.getAccount())))
        );
    }

    @Test
    public void shouldThrowWhenDeveloperNotFound() {
        doReturn(null).when(developerRepository).get(DEFAULT_ID);

        try {
            sut.get(DEFAULT_ID);
        } catch (Exception e) {
            assertThat(e, instanceOf(EntityNotFoundException.class));
        }

        verify(developerRepository).get(DEFAULT_ID);
    }

    @Test
    public void shouldAddDeveloper() {
        Developer developer = generateSingleDeveloper(DEFAULT_ID);
        sut.add(developer);

        verify(developerRepository).add(developer);
    }

    @Test
    public void shouldUpdateDeveloper() {
        Developer developer = generateSingleDeveloper(DEFAULT_ID);
        doReturn(developer).when(developerRepository).get(DEFAULT_ID);
        doReturn(true).when(developerRepository).update(developer);

        boolean updated = sut.update(DEFAULT_ID, developer);

        verify(developerRepository).update(developer);

        assertThat(updated, is(true));
    }

    @Test
    public void shouldThrowWhenUpdatedDeveloperNotFound() {
        Developer developer = generateSingleDeveloper(DEFAULT_ID);
        doReturn(null).when(developerRepository).get(DEFAULT_ID);

        try {
            sut.update(DEFAULT_ID, developer);
        } catch (Exception e) {
            assertThat(e, instanceOf(EntityNotFoundException.class));
        }

        verify(developerRepository).get(DEFAULT_ID);
    }

    @Test
    public void shouldDeleteAccount() {
        Developer developer = generateSingleDeveloper(DEFAULT_ID);
        doReturn(developer).when(developerRepository).remove(DEFAULT_ID);

        Developer deletedDeveloper = sut.remove(DEFAULT_ID);

        verify(developerRepository).remove(DEFAULT_ID);

        assertThat(deletedDeveloper, allOf(
                hasProperty("id", is(developer.getId())),
                hasProperty("name", is(developer.getName())),
                hasProperty("skills", is(developer.getSkills())),
                hasProperty("account", is(developer.getAccount())))
        );
    }

    @Test
    public void shouldThrowWhenDeletedDeveloperNotFound() {
        doReturn(null).when(developerRepository).get(DEFAULT_ID);

        try {
            sut.remove(DEFAULT_ID);
        } catch (Exception e) {
            assertThat(e, instanceOf(EntityNotFoundException.class));
        }

        verify(developerRepository).remove(DEFAULT_ID);
    }
}
