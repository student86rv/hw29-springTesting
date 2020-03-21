package ua.epam.springapp.service;

import org.junit.Test;
import org.mockito.Mockito;
import ua.epam.springapp.exception.EntityNotFoundException;
import ua.epam.springapp.model.Account;
import ua.epam.springapp.repository.AccountRepository;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static ua.epam.springapp.utils.AccountGenerator.generateAccounts;
import static ua.epam.springapp.utils.AccountGenerator.generateSingleAccount;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class AccountServiceImplTest {

    private static final long DEFAULT_ID = 1L;

    private final AccountRepository accountRepository = Mockito.mock(AccountRepository.class);

    private final AccountServiceImpl sut = new AccountServiceImpl(accountRepository);

    @Test
    public void shouldGetAllAccounts() {
        int accountsSize = 10;
        List<Account> accounts = generateAccounts(accountsSize);
        doReturn(accounts).when(accountRepository).getAll();

        List<Account> foundAccounts = sut.getAll();

        verify(accountRepository).getAll();
        assertThat(foundAccounts, hasSize(accountsSize));

        accounts.forEach(
                account -> assertThat(foundAccounts, hasItem(allOf(
                        hasProperty("id", is(account.getId())),
                        hasProperty("email", is(account.getEmail())),
                        hasProperty("status", is(account.getStatus()))))
                )
        );
    }

    @Test
    public void shouldThrowWhenAccountsListIsEmpty() {
        doReturn(new ArrayList<Account>()).when(accountRepository).getAll();

        try {
            sut.getAll();
        } catch (Exception e) {
            assertThat(e, instanceOf(EntityNotFoundException.class));
        }

        verify(accountRepository).getAll();
    }

    @Test
    public void shouldGetAccount() {
        Account account = generateSingleAccount(DEFAULT_ID);
        doReturn(account).when(accountRepository).get(DEFAULT_ID);

        Account foundAccount = sut.get(DEFAULT_ID);

        verify(accountRepository).get(DEFAULT_ID);

        assertThat(foundAccount, allOf(
                hasProperty("id", is(account.getId())),
                hasProperty("email", is(account.getEmail())),
                hasProperty("status", is(account.getStatus())))
        );
    }

    @Test
    public void shouldThrowWhenAccountNotFound() {
        doReturn(null).when(accountRepository).get(DEFAULT_ID);

        try {
            sut.get(DEFAULT_ID);
        } catch (Exception e) {
            assertThat(e, instanceOf(EntityNotFoundException.class));
        }

        verify(accountRepository).get(DEFAULT_ID);
    }

    @Test
    public void shouldAddAccount() {
        Account account = generateSingleAccount(DEFAULT_ID);
        sut.add(account);

        verify(accountRepository).add(account);
    }

    @Test
    public void shouldUpdateAccount() {
        Account account = generateSingleAccount(DEFAULT_ID);
        doReturn(account).when(accountRepository).get(DEFAULT_ID);
        doReturn(true).when(accountRepository).update(account);

        boolean updated = sut.update(DEFAULT_ID, account);

        verify(accountRepository).update(account);

        assertThat(updated, is(true));
    }

    @Test
    public void shouldThrowWhenUpdatedAccountNotFound() {
        Account account = generateSingleAccount(DEFAULT_ID);
        doReturn(null).when(accountRepository).get(DEFAULT_ID);

        try {
            sut.update(DEFAULT_ID, account);
        } catch (Exception e) {
            assertThat(e, instanceOf(EntityNotFoundException.class));
        }

        verify(accountRepository).get(DEFAULT_ID);
    }

    @Test
    public void shouldDeleteAccount() {
        Account account = generateSingleAccount(DEFAULT_ID);
        doReturn(account).when(accountRepository).remove(DEFAULT_ID);

        Account deletedAccount = sut.remove(DEFAULT_ID);

        verify(accountRepository).remove(DEFAULT_ID);

        assertThat(deletedAccount, allOf(
                hasProperty("id", is(account.getId())),
                hasProperty("email", is(account.getEmail())),
                hasProperty("status", is(account.getStatus())))
        );
    }

    @Test
    public void shouldThrowWhenDeletedAccountNotFound() {
        doReturn(null).when(accountRepository).get(DEFAULT_ID);

        try {
            sut.remove(DEFAULT_ID);
        } catch (Exception e) {
            assertThat(e, instanceOf(EntityNotFoundException.class));
        }

        verify(accountRepository).remove(DEFAULT_ID);
    }
}
