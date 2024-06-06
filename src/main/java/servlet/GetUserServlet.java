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


        if (userId == null || userId.isEmpty() ){
            getServletContext().getRequestDispatcher("/WEB-INF/getUserForm.jsp").forward(req,resp);
            return;
        }

        PreparedStatement preparedStatement = null;
        ResultSet prepareResultSet = null;

        try {
            PostgresDriverManager driverManager = PostgresDriverManager.getInstance();
            Connection connection = driverManager.getConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            preparedStatement.setInt(1,Integer.parseInt(userId));

            prepareResultSet = preparedStatement.executeQuery();


            while (prepareResultSet.next()){
                System.out.print(" " + prepareResultSet.getInt("id"));
                System.out.print(" " + prepareResultSet.getString("name"));
                System.out.println(" " + prepareResultSet.getString("login"));
            }
            if(prepareResultSet != null) {
                req.setAttribute("body","Success");
                getServletContext().getRequestDispatcher("/WEB-INF/information.jsp").forward(req,resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
