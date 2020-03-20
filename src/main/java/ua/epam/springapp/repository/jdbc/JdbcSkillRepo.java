package ua.epam.springapp.repository.jdbc;

import org.springframework.stereotype.Repository;
import ua.epam.springapp.model.Skill;
import ua.epam.springapp.repository.SkillRepository;
import ua.epam.springapp.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class JdbcSkillRepo implements SkillRepository {

    private static Logger LOGGER = Logger.getLogger(JdbcSkillRepo.class.getName());

    private Connection connection;

    public JdbcSkillRepo() {
        try {
            this.connection = ConnectionUtil.getConnection();
            LOGGER.log(Level.INFO, "GenericRepository connected to database");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Connection to database failed");
        }
        createSkillsTable();
    }

    private void createSkillsTable() {
        String createQuery = "CREATE TABLE IF NOT EXISTS skills (\n" +
                "id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "name VARCHAR(255) NOT NULL\n" +
                ");";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createQuery);
            LOGGER.log(Level.INFO, "New table was created");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Table creating failed");
        }
    }

    @Override
    public void add(Skill entity) {
        String insertQuery = String.format("INSERT INTO skills (name) VALUES ('%s');",
                entity.getName());
        String getIdQuery = "SELECT MAX(id) id FROM skills;";
        try (Statement statement = connection.createStatement()) {
            statement.execute(insertQuery);
            ResultSet rs = statement.executeQuery(getIdQuery);
            int id = 0;
            while (rs.next()) {
                id = rs.getInt("id");
            }
            entity.setId(id);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Writing failed");
        }
    }

    @Override
    public Skill get(Long id) {
        String getSkillQuery = String.format("SELECT * FROM skills WHERE id = %d;", id);
        Skill skill = null;
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(getSkillQuery);
            while (rs.next()) {
                skill = new Skill(
                        rs.getInt("id"),
                        rs.getString("name")
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Reading failed");
        }
        return skill;
    }

    @Override
    public List<Skill> getAll() {
        String getAllQuery = "SELECT * FROM skills ORDER BY id;";
        List<Skill> skills = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(getAllQuery);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                skills.add(new Skill(id, name));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Reading failed");
        }
        return skills;
    }

    @Override
    public boolean update(Skill entity) {
        String updateQuery = String.format("UPDATE skills SET name = ('%s') WHERE id = %d;",
                entity.getName(), entity.getId());
        int updatedRows = 0;
        try (Statement statement = connection.createStatement()) {
            updatedRows = statement.executeUpdate(updateQuery);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Updating failed");
        }
        return updatedRows > 0;
    }

    @Override
    public Skill remove(Long id) {
        Skill skill = get(id);
        String removeQuery = String.format("DELETE FROM skills WHERE id = %d;", id);
        try (Statement statement = connection.createStatement()) {
            statement.execute(removeQuery);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Deleting failed");
        }
        return skill;
    }
}
