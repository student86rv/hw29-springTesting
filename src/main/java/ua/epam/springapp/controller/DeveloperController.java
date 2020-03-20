package ua.epam.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.epam.springapp.model.Developer;
import ua.epam.springapp.service.DeveloperService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperController {

    private final DeveloperService developerService;

    @Autowired
    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping("/{id}")
    public Developer get(@PathVariable(value="id") Long id) {
        return developerService.get(id);
    }

    @GetMapping
    public List<Developer> getAll() {
        return developerService.getAll();
    }

    @PostMapping
    public ResponseEntity add(@RequestBody Developer developer) {
        developerService.add(developer);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable(value="id") Long id, @RequestBody Developer developer) {
        developerService.update(id, developer);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity remove(@PathVariable(value="id") Long id) {
        developerService.remove(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
