package ua.epam.springapp.jdbc;

import org.junit.Before;
import org.junit.Test;
import ua.epam.springapp.model.Account;
import ua.epam.springapp.model.AccountStatus;
import ua.epam.springapp.repository.jdbc.JdbcAccountRepo;

import static org.junit.Assert.*;

public class JdbcAccountRepoTest {

    private JdbcAccountRepo repo;

    @Before
    public void init() {
        repo = new JdbcAccountRepo();
    }

    @Test
    public void addAndGetTest() {
        Account account = new Account("vasya.pupkin@gmail.com", AccountStatus.ACTIVE);
        repo.add(account);
        long id = account.getId();
        Account account1 = repo.get(id);

        assertEquals(account, account1);
        repo.remove(id);
    }

    @Test
    public void removeTest() {
        Account account = new Account("ivan.ivanov@i.ua", AccountStatus.DELETED);
        repo.add(account);
        long id = account.getId();
        Account account1 = repo.remove(id);

        assertEquals(account, account1);
        assertNull(repo.get(id));
    }

    @Test
    public void updateTest(){
        Account account = new Account("petr.petrov@ukr.net", AccountStatus.BANNED);
        repo.add(account);
        long id = account.getId();

        Account newAccount = new Account(id,"sidor.sidorov@gmail.com", AccountStatus.ACTIVE);
        repo.update(newAccount);

        assertEquals(repo.get(id), newAccount);
        repo.remove(id);
    }
}
