package ua.epam.springapp.utils;

import ua.epam.springapp.model.Skill;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SkillGenerator {

    public static List<Skill> generateSkills(int count) {
        return IntStream.range(0, count)
                .mapToObj(SkillGenerator::generateSingleSkill)
                .collect(Collectors.toList());
    }

    public static Skill generateSingleSkill(long id) {
        String name = "testSkill" + id;
        return new Skill(id, name);
    }
}
