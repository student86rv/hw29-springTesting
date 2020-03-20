package ua.epam.springapp.utils;

import ua.epam.springapp.model.Account;
import ua.epam.springapp.model.Developer;
import ua.epam.springapp.model.Skill;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DeveloperGenerator {

    private static final Set<Skill> SKILLS = new HashSet<>(SkillGenerator.generateSkills(2));
    private static final Account ACCOUNT = AccountGenerator.generateSingleAccount(1);

    public static List<Developer> generateDevelopers(int count) {
        return IntStream.range(0, count)
                .mapToObj(DeveloperGenerator::generateSingleDeveloper)
                .collect(Collectors.toList());
    }

    public static Developer generateSingleDeveloper(long id) {
        String name = "testDeveloper" + id;
        return new Developer(id, name, SKILLS, ACCOUNT);
    }
}
