package ua.epam.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.springapp.exception.EntityNotFoundException;
import ua.epam.springapp.model.Developer;
import ua.epam.springapp.repository.DeveloperRepository;

import java.util.List;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    private final DeveloperRepository developerRepo;

    @Autowired
    public DeveloperServiceImpl(DeveloperRepository developerRepo) {
        this.developerRepo = developerRepo;
    }

    @Override
    public void add(Developer entity) {
        developerRepo.add(entity);
    }

    @Override
    public Developer get(Long id) {
        Developer developer = developerRepo.get(id);
        if (developer == null) {
            throw new EntityNotFoundException();
        }
        return developer;
    }

    @Override
    public List<Developer> getAll() {
        List<Developer> list = developerRepo.getAll();
        if (list.size() == 0) {
            throw new EntityNotFoundException();
        }
        return list;
    }

    @Override
    public boolean update(Long id, Developer entity) {
        Developer developer = developerRepo.get(id);
        if (developer == null) {
            throw new EntityNotFoundException();
        }

        developer.setName(entity.getName());
        developer.setSkills(entity.getSkills());
        developer.setAccount(entity.getAccount());

        return developerRepo.update(developer);
    }

    @Override
    public Developer remove(Long id) {
        Developer developer = developerRepo.remove(id);
        if (developer == null) {
            throw new EntityNotFoundException();
        }
        return developer;
    }
}
