package ua.epam.springapp.model;

import java.util.Objects;
import java.util.Set;

public class Developer {

    private long id;
    private String name;

    private Set<Skill> skills;
    private Account account;

    public Developer() {
    }

    public Developer(String name) {
        this.name = name;
    }

    public Developer(String name, Set<Skill> skills, Account account) {
        this.name = name;
        this.skills = skills;
        this.account = account;
    }

    public Developer(long id, String name, Set<Skill> skills, Account account) {
        this.id = id;
        this.name = name;
        this.skills = skills;
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return id == developer.id &&
                Objects.equals(name, developer.name) &&
                Objects.equals(skills, developer.skills) &&
                Objects.equals(account, developer.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, skills, account);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", skills=" + skills +
                ", account=" + account +
                '}';
    }
}
