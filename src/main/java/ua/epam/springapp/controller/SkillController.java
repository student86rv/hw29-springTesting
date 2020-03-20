package ua.epam.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.epam.springapp.model.Skill;
import ua.epam.springapp.service.SkillService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skills")
public class SkillController {

    private final SkillService skillService;

    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping("/{id}")
    public Skill get(@PathVariable(value="id") Long id) {
        return skillService.get(id);
    }

    @GetMapping
    public List<Skill> getAll() {
        return skillService.getAll();
    }

    @PostMapping
    public ResponseEntity add(@RequestBody Skill skill) {
        skillService.add(skill);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable(value="id") Long id, @RequestBody Skill skill) {
        skillService.update(id, skill);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity remove(@PathVariable(value="id") Long id) {
        skillService.remove(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
