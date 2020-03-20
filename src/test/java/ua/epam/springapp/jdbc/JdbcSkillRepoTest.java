package ua.epam.springapp.jdbc;

import org.junit.Before;
import org.junit.Test;
import ua.epam.springapp.model.Skill;
import ua.epam.springapp.repository.jdbc.JdbcSkillRepo;

import static org.junit.Assert.*;

public class JdbcSkillRepoTest {

    private JdbcSkillRepo repo;

    @Before
    public void initRepo() {
        repo = new JdbcSkillRepo();
    }

    @Test
    public void addAndGetTest() {
        Skill skill = new Skill("Java");
        repo.add(skill);
        long id = skill.getId();
        Skill skill2 = repo.get(id);

        assertEquals(skill, skill2);
        repo.remove(id);
    }

    @Test
    public void removeTest() {
        Skill skill = new Skill("C++");
        repo.add(skill);
        long id = skill.getId();
        Skill skill2 = repo.remove(id);

        assertEquals(skill, skill2);
        assertNull(repo.get(id));
    }
}
