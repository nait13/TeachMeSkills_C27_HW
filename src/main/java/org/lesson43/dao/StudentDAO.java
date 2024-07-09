package org.lesson43.dao;

import org.lesson43.models.Student;
import org.lesson43.postgres.driver.PostgresDriverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentDAO {
    private PostgresDriverManager postgresDriverManager;

    @Autowired
    public void setPostgresDriverManager(PostgresDriverManager postgresDriverManager) {
        this.postgresDriverManager = postgresDriverManager;
    }

    public List<Student> index() {
        try (Connection connection = postgresDriverManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM students")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Student> studentList = new ArrayList<>();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setSurname(resultSet.getString("surName"));
                student.setGithub(resultSet.getString("github"));
                studentList.add(student);
                System.out.println(student);
            }
            return studentList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Student show(int id) {
        try (Connection connection = postgresDriverManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM students WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setSurname(resultSet.getString("surName"));
                student.setGithub(resultSet.getString("github"));
                return student;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean save(Student student) {
        try (Connection connection = postgresDriverManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO students(name,surname,github) VALUES(?,?,?)")) {

            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getSurname());
            preparedStatement.setString(3, student.getGithub());
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Student student, int id) {
        try (Connection connection = postgresDriverManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE students SET name = ? , surname = ? , github = ? WHERE id = ?")) {

            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getSurname());
            preparedStatement.setString(3, student.getGithub());
            preparedStatement.setInt(4, id);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        try (Connection connection = postgresDriverManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM students WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
