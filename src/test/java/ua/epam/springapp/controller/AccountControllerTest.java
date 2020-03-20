package ua.epam.springapp.controller;

import com.google.gson.Gson;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.epam.springapp.model.Account;
import ua.epam.springapp.service.AccountService;

import static ua.epam.springapp.utils.AccountGenerator.generateAccounts;
import static ua.epam.springapp.utils.AccountGenerator.generateSingleAccount;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.List;

public class AccountControllerTest {

    private static final String BASE_URL = "/api/v1/accounts";
    private static final long DEFAULT_ID = 1L;

    private final AccountService accountService = Mockito.mock(AccountService.class);

    private final AccountController sut = new AccountController(accountService);

    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sut).build();

    private final Gson gson = new Gson();

    @Test
    public void shouldGetAllAccounts() throws Exception {
        List<Account> accountList = generateAccounts(2);
        given(this.accountService.getAll()).willReturn(accountList);
        mockMvc.perform(get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is((int) accountList.get(0).getId())))
                .andExpect(jsonPath("$[1].id", is((int) accountList.get(1).getId())))
                .andExpect(jsonPath("$[0].email", is(accountList.get(0).getEmail())))
                .andExpect(jsonPath("$[1].email", is(accountList.get(1).getEmail())))
                .andExpect(jsonPath("$[0].status", is(accountList.get(0).getStatus().toString())))
                .andExpect(jsonPath("$[1].status", is(accountList.get(1).getStatus().toString())));
    }

    @Test
    public void shouldGetAccount() throws Exception{
        Account account = generateSingleAccount(DEFAULT_ID);
        given(this.accountService.get(DEFAULT_ID)).willReturn(account);

        mockMvc.perform(get(BASE_URL + "/" + DEFAULT_ID)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) account.getId())))
                .andExpect(jsonPath("$.email", is(account.getEmail())))
                .andExpect(jsonPath("$.status", is(account.getStatus().toString())));
    }

    @Test
    public void shouldAddAccount() throws Exception {
        Account account = generateSingleAccount(DEFAULT_ID);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(account)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldUpdateAccount() throws Exception {
        Account account = generateSingleAccount(DEFAULT_ID);
        given(this.accountService.update(DEFAULT_ID, account)).willReturn(true);

        mockMvc.perform(put(BASE_URL + "/" + DEFAULT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(account)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteAccount() throws Exception {
        Account account = generateSingleAccount(DEFAULT_ID);
        given(this.accountService.remove(DEFAULT_ID)).willReturn(account);

        mockMvc.perform(delete(BASE_URL + "/" + DEFAULT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(account)))
                .andExpect(status().isOk());
    }
}
