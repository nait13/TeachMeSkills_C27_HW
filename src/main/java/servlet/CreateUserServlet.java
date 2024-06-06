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

@WebServlet("/create")
public class CreateUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/createUserForm.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("nameInput");
        String userLogin = req.getParameter("login");

        if (userLogin == null || userLogin.isEmpty() || userName == null || userName.isEmpty()) {
            return;
        }
        PreparedStatement preparedStatement = null;
        int prepareResultSet;

        try {
            PostgresDriverManager driverManager = PostgresDriverManager.getInstance();
            Connection connection = driverManager.getConnection();

            preparedStatement = connection.prepareStatement("INSERT INTO users(name,login) VALUES(?,?)");
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, userLogin);

            prepareResultSet = preparedStatement.executeUpdate();

            if (prepareResultSet > 0) {
                req.setAttribute("body","Success insert");
                getServletContext().getRequestDispatcher("/WEB-INF/information.jsp").forward(req,resp);
            }else {
                req.setAttribute("body","Error insert");
                getServletContext().getRequestDispatcher("/WEB-INF/information.jsp").forward(req,resp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
