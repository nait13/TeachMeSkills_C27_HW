package org.lesson41.service;


import org.lesson41.DTO.User;
import org.lesson41.postgres.driver.PostgresDriverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@Repository
public class UserService {
    @Autowired
    private PostgresDriverManager postgresDriverManager;

    public User getUserById(int id) {
        try (Connection connection = postgresDriverManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setLogin(resultSet.getString("login"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createUser(String name, String login) {
        try (Connection connection = postgresDriverManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(name, login) VALUES(?, ?)")) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, login);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(int id) {
        try (Connection connection = postgresDriverManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {

            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean updateUserLogin(int id, String login) {
        try (Connection connection = postgresDriverManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET login = ? WHERE id = ?")) {

            preparedStatement.setString(1, login);
            preparedStatement.setInt(2, id);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
