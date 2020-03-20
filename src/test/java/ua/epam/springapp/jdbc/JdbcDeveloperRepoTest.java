package ua.epam.springapp.jdbc;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.epam.springapp.model.Account;
import ua.epam.springapp.model.AccountStatus;
import ua.epam.springapp.model.Developer;
import ua.epam.springapp.model.Skill;
import ua.epam.springapp.repository.jdbc.JdbcAccountRepo;
import ua.epam.springapp.repository.jdbc.JdbcDeveloperRepo;
import ua.epam.springapp.repository.jdbc.JdbcSkillRepo;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class JdbcDeveloperRepoTest {

    private static JdbcAccountRepo accountRepo;
    private static JdbcSkillRepo skillRepo;
    private static JdbcDeveloperRepo developerRepo;

    private static Account account;
    private static Set<Skill> skills;

    @BeforeClass
    public static void initRepo() {
        accountRepo = new JdbcAccountRepo();
        skillRepo = new JdbcSkillRepo();
        developerRepo = new JdbcDeveloperRepo();
    }

    @BeforeClass
    public static void initEntities() {
        account = new Account("vasya.pupkin@gmail.com", AccountStatus.ACTIVE);
        accountRepo.add(account);

        skills = new HashSet<>();
        skills.add(new Skill("Java"));
        skills.add(new Skill("C++"));
        skills.add(new Skill("PHP"));
        skills.add(new Skill("Front-end"));
        skills.add(new Skill("Some new skill"));

        for (Skill skill : skills) {
            skillRepo.add(skill);
        }
    }

    @AfterClass
    public static void removeFromDb() {
        accountRepo.remove(account.getId());

        for (Skill skill : skills) {
            skillRepo.remove(skill.getId());
        }
    }

    @Test
    public void addAndGetTest() {

        Developer developer = new Developer("Ivan Ivanov", skills, account);
        developerRepo.add(developer);

        long id = developer.getId();
        Developer developer1 = developerRepo.get(id);

        assertEquals(developer, developer1);
        assertEquals(account, developer1.getAccount());
        assertEquals(skills, developer1.getSkills());
        developerRepo.remove(id);
    }

    @Test
    public void removeTest() {
        Developer developer = new Developer("Petr Petrov", skills, account);
        developerRepo.add(developer);
        long id = developer.getId();

        Developer developer1 = developerRepo.remove(id);

        assertEquals(developer, developer1);
        assertNull(developerRepo.get(id));
    }

    @Test
    public void updateTest() {
        Developer developer = new Developer("Sidor Sidorov", skills, account);
        developerRepo.add(developer);
        long id = developer.getId();

        Developer developer1 = new Developer(id, "Vasili Pupkin", skills, account);
        developerRepo.update(developer1);

        assertEquals(developerRepo.get(id), developer1);
        developerRepo.remove(id);
    }
}
