package ua.epam.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.epam.springapp.model.Account;
import ua.epam.springapp.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public Account get(@PathVariable(value="id") Long id) {
        return accountService.get(id);
    }

    @GetMapping
    public List<Account> getAll() {
        return accountService.getAll();
    }

    @PostMapping
    public ResponseEntity add(@RequestBody Account account) {
        accountService.add(account);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable(value="id") Long id, @RequestBody Account account) {
        accountService.update(id, account);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity remove(@PathVariable(value="id") Long id) {
        accountService.remove(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
