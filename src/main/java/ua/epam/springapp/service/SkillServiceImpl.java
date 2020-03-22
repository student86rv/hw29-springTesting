package ua.epam.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.springapp.exception.EntityNotFoundException;
import ua.epam.springapp.model.Skill;
import ua.epam.springapp.repository.SkillRepository;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepo;

    @Autowired
    public SkillServiceImpl(SkillRepository skillRepo) {
        this.skillRepo = skillRepo;
    }

    @Override
    public void add(Skill entity) {
        skillRepo.add(entity);
    }

    @Override
    public Skill get(Long id) {
        Skill skill = skillRepo.get(id);
        if (skill == null) {
            throw new EntityNotFoundException();
        }
        return skill;
    }

    @Override
    public List<Skill> getAll() {
        List<Skill> list = skillRepo.getAll();
        if (list.size() == 0) {
            throw new EntityNotFoundException();
        }
        return list;
    }

    @Override
    public boolean update(Long id, Skill entity) {
        Skill skill = skillRepo.get(id);
        if (skill == null) {
            throw new EntityNotFoundException();
        }

        skill.setName(entity.getName());

        return skillRepo.update(entity);
    }

    @Override
    public Skill remove(Long id) {
        Skill skill = skillRepo.remove(id);
        if (skill == null) {
            throw new EntityNotFoundException();
        }
        return skill;
    }
}
