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
import java.sql.SQLException;

@WebServlet("/delete")
public class DeleteUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/deleteUserForm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId").trim();


        if (userId == null || userId.isEmpty()) {
            getServletContext().getRequestDispatcher("/WEB-INF/deleteUserForm.jsp").forward(req, resp);
        }

        try (Connection connection = PostgresDriverManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?"))
        {

            preparedStatement.setInt(1, Integer.parseInt(userId));

            int prepareResultSet = preparedStatement.executeUpdate();

            if (prepareResultSet > 0) {
                req.setAttribute("body", "User id :" + userId + " Success delete");
            } else {
                req.setAttribute("body", "Error delete");
            }
            getServletContext().getRequestDispatcher("/WEB-INF/information.jsp").forward(req, resp);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
