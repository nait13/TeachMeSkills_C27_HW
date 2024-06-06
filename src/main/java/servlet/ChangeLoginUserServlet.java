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

@WebServlet("/change-login")
public class ChangeLoginUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/updateLogin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String newLogin = req.getParameter("login");

        if (userId == null || userId.isEmpty() || newLogin == null || newLogin.isEmpty()) {
            return;
        }

        int prepareResultSet;

        try (Connection connection = PostgresDriverManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET login = ? WHERE id = ?"))
        {
            preparedStatement.setString(1, newLogin);
            preparedStatement.setInt(2, Integer.parseInt(userId));

            prepareResultSet = preparedStatement.executeUpdate();
            if (prepareResultSet > 0) {
                req.setAttribute("body","Success change login");
                getServletContext().getRequestDispatcher("/WEB-INF/information.jsp").forward(req,resp);
            }else {
                req.setAttribute("body","Error change login ");
                getServletContext().getRequestDispatcher("/WEB-INF/information.jsp").forward(req,resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
