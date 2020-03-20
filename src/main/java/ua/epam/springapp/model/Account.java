package ua.epam.springapp.model;

import java.util.Objects;

public class Account {

    private long id;
    private String email;
    private AccountStatus status;

    public Account() {
    }

    public Account(long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Account(String email, AccountStatus status) {
        this.email = email;
        this.status = status;
    }

    public Account(long id, String email, AccountStatus status) {
        this.id = id;
        this.email = email;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id &&
                Objects.equals(email, account.email) &&
                status == account.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, status);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", status=" + status +
                '}';
    }
}
