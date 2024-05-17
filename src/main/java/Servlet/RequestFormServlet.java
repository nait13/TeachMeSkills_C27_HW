package Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet({"/success.jsp", "/"})
public class RequestFormServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/WEB-INF/save-request.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("nameInput").trim();
        String surName = req.getParameter("surName").trim();


        if (name == null || name.isEmpty() || surName == null || surName.isEmpty()) {
            resp.sendRedirect("/");
        } else {
            req.setAttribute("name", name);
            req.setAttribute("surName", surName);

            req.getServletContext().getRequestDispatcher("/WEB-INF/success.jsp").forward(req, resp);
        }
    }
}
