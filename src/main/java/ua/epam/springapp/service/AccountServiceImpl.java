package ua.epam.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.springapp.exception.EntityNotFoundException;
import ua.epam.springapp.model.Account;
import ua.epam.springapp.repository.AccountRepository;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepo;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public void add(Account entity) {
        accountRepo.add(entity);
    }

    @Override
    public Account get(Long id) {
        Account account = accountRepo.get(id);
        if (account == null) {
            throw new EntityNotFoundException();
        }
        return account;
    }

    @Override
    public List<Account> getAll() {
        List<Account> list = accountRepo.getAll();
        if (list.size() == 0) {
            throw new EntityNotFoundException();
        }
        return list;
    }

    @Override
    public boolean update(Long id, Account entity) {
        Account account = accountRepo.get(id);
        if (account == null) {
            throw new EntityNotFoundException();
        }

        account.setEmail(entity.getEmail());
        account.setStatus(entity.getStatus());

        return accountRepo.update(account);
    }

    @Override
    public Account remove(Long id) {
        Account account = accountRepo.remove(id);
        if (account == null) {
            throw new EntityNotFoundException();
        }
        return account;
    }
}
