package ua.epam.springapp.repository.jdbc;

import org.springframework.stereotype.Repository;
import ua.epam.springapp.model.Account;
import ua.epam.springapp.model.AccountStatus;
import ua.epam.springapp.model.Developer;
import ua.epam.springapp.model.Skill;
import ua.epam.springapp.repository.DeveloperRepository;
import ua.epam.springapp.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class JdbcDeveloperRepo implements DeveloperRepository {

    private static Logger LOGGER = Logger.getLogger(JdbcDeveloperRepo.class.getName());

    private final String GET_SKILL_QUERY = "SELECT s.id id, s.name name FROM skills s INNER JOIN\n" +
            "developer_skills d_s ON s.id = d_s.skill_id WHERE d_s.developer_id = %d ORDER BY id;";
    private final String INSERT_DEV_SKILL_QUERY = "INSERT INTO developer_skills (developer_id, skill_id)\n" +
            "VALUES (%d, %d);";
    private final String DELETE_DEV_SKILL_QUERY = "DELETE FROM developer_skills WHERE developer_id = %d;";

    private Connection connection;

    public JdbcDeveloperRepo() {
        try {
            this.connection = ConnectionUtil.getConnection();
            LOGGER.log(Level.INFO, "GenericRepository connected to database");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Connection to database failed");
        }
        createTables();
    }

    private void createTables() {
        String createDevQuery = "CREATE TABLE IF NOT EXISTS developers (\n" +
                "id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "name VARCHAR(255) NOT NULL,\n" +
                "account_id INT,\n" +
                "FOREIGN KEY (account_id) REFERENCES accounts (id) ON DELETE SET NULL\n" +
                ");";
        String createDSQuery = "CREATE TABLE IF NOT EXISTS developer_skills (\n" +
                "developer_id INT NOT NULL,\n" +
                "skill_id INT NOT NULL,\n" +
                "UNIQUE (developer_id, skill_id),\n" +
                "FOREIGN KEY (developer_id) REFERENCES developers (id) ON DELETE CASCADE,\n" +
                "FOREIGN KEY (skill_id) REFERENCES skills (id) ON DELETE CASCADE\n" +
                ");";
        try (Statement statement = connection.createStatement()) {
            statement.addBatch(createDevQuery);
            statement.addBatch(createDSQuery);
            statement.executeBatch();
            LOGGER.log(Level.INFO, "New tables were created");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Tables creating failed");
        }
    }

    @Override
    public void add(Developer entity) {
        String insertDevQuery = String.format("INSERT INTO developers (name, account_id)\n" +
                "VALUES ('%s', %d);", entity.getName(), entity.getAccount().getId());
        String getIdQuery = "SELECT MAX(id) id FROM developers;";
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.execute(insertDevQuery);
            ResultSet rs = statement.executeQuery(getIdQuery);
            int id = 0;
            while (rs.next()) {
                id = rs.getInt("id");
            }
            entity.setId(id);
            for (Skill skill : entity.getSkills()) {
                statement.addBatch(String.format(INSERT_DEV_SKILL_QUERY, id, skill.getId()));
            }
            statement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Writing failed. Trying to cancel transaction...");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                LOGGER.log(Level.SEVERE, "Transaction rollback failed");
            }
        }
    }

    @Override
    public Developer get(Long id) {
        String getDevQuery = String.format("SELECT d.id id, d.name name, d.account_id account_id,\n" +
                "a.email email, a.status status FROM developers d INNER JOIN accounts a\n" +
                "ON d.account_id = a.id WHERE d.id = %d", id);
        Set<Skill> skills = new HashSet<>();
        Developer developer = null;
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            ResultSet skillRs = statement.executeQuery(String.format(GET_SKILL_QUERY, id));
            while (skillRs.next()) {
                int skillId = skillRs.getInt("id");
                String skillName = skillRs.getString("name");
                skills.add(new Skill(skillId, skillName));
            }
            ResultSet devRs = statement.executeQuery(getDevQuery);
            while (devRs.next()) {
                int devId = devRs.getInt("id");
                String devName = devRs.getString("name");
                Account account = new Account(
                        devRs.getInt("account_id"),
                        devRs.getString("email"),
                        AccountStatus.valueOf(devRs.getString("status"))
                );
                developer = new Developer(devId, devName, skills, account);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Reading failed. Trying to cancel transaction...");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                LOGGER.log(Level.SEVERE, "Transaction rollback failed");
            }
        }
        return developer;
    }

    @Override
    public List<Developer> getAll() {
        String getAllQuery = "SELECT d.id id, d.name name, d.account_id account_id,\n" +
                "a.email email, a.status status FROM developers d INNER JOIN accounts a\n" +
                "ON account_id = a.id ORDER BY id";
        List<Developer> developers = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);

            ResultSet devRs = statement.executeQuery(getAllQuery);
            while (devRs.next()) {
                int devId = devRs.getInt("id");
                String devName = devRs.getString("name");
                Account account = new Account(
                        devRs.getInt("account_id"),
                        devRs.getString("email"),
                        AccountStatus.valueOf(devRs.getString("status"))
                );
                developers.add(new Developer(devId, devName, null, account));
            }
            for (Developer developer : developers) {
                Set<Skill> skills = new HashSet<>();
                ResultSet skillRs = statement.executeQuery(String.format(GET_SKILL_QUERY, developer.getId()));

                while (skillRs.next()) {
                    int skillId = skillRs.getInt("id");
                    String skillName = skillRs.getString("name");
                    skills.add(new Skill(skillId, skillName));
                }
                developer.setSkills(skills);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Reading failed. Trying to cancel transaction...");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                LOGGER.log(Level.SEVERE, "Transaction rollback failed");
            }
        }
        return developers;
    }

    @Override
    public boolean update(Developer entity) {
        String updateDevQuery = String.format("UPDATE developers SET name = '%s'," +
                        "account_id = %d WHERE id = %d;",
                entity.getName(), entity.getAccount().getId(), entity.getId());
        int updatedRows = 0;
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            updatedRows = statement.executeUpdate(updateDevQuery);
            statement.execute(String.format(DELETE_DEV_SKILL_QUERY, entity.getId()));
            for (Skill skill : entity.getSkills()) {
                statement.addBatch(String.format(INSERT_DEV_SKILL_QUERY, entity.getId(), skill.getId()));
            }
            statement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Updating failed. Trying to cancel transaction...");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                LOGGER.log(Level.SEVERE, "Transaction rollback failed");
            }
        }
        return updatedRows > 0;
    }

    @Override
    public Developer remove(Long id) {
        Developer developer = get(id);
        String deleteDevQuery = String.format("DELETE FROM developers WHERE id = %d;", id);
        try (Statement statement = connection.createStatement()) {
            statement.addBatch(deleteDevQuery);
            statement.addBatch(String.format(DELETE_DEV_SKILL_QUERY, id));
            statement.executeBatch();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Deleting failed" + e);
        }
        return developer;
    }
}
