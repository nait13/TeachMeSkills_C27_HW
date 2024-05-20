package Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/success")
public class FormHandlerServlet extends HttpServlet {
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
