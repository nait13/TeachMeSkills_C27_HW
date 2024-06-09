package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pack.PostgresDriverManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/get")
public class GetUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");


        if (userId == null || userId.isEmpty()) {
            getServletContext().getRequestDispatcher("/WEB-INF/getUserForm.jsp").forward(req, resp);
            return;
        }

        try (Connection connection = PostgresDriverManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users where id = ?")) {

            preparedStatement.setInt(1, Integer.parseInt(userId));

            ResultSet prepareResultSet = preparedStatement.executeQuery();

            String name = null;
            String login = null;
            while (prepareResultSet.next()) {
                name = prepareResultSet.getString("name");
                login = prepareResultSet.getString("login");
                System.out.print(" " + prepareResultSet.getInt("id"));
                System.out.print(" " + prepareResultSet.getString("name"));
                System.out.println(" " + prepareResultSet.getString("login"));
            }

            if (name != null && login != null) {
                req.setAttribute("body", "Name: " + name + ", Login: " + login);
            } else {
                req.setAttribute("body", "Not found user!");
            }
            getServletContext().getRequestDispatcher("/WEB-INF/information.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
