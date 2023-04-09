package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/process")
public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(checkClear(req,resp)) return;
        if(checkArea(req,resp)) return;
        req.setAttribute("errorMessage", "Error: miss some args");
        req.getRequestDispatcher("/index.jsp").forward(req,resp);

    }

    private boolean checkClear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("clear") != null &&
                req.getParameter("clear").equals("true")) {
            req.getRequestDispatcher("/clear").forward(req, resp);
            return true;
        } return false;
    }

    private boolean checkArea(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("x") != null &&
                req.getParameter("y") != null &&
                req.getParameter("r") != null) {
            req.getRequestDispatcher("/checkArea").forward(req,resp);
            return true;
        } return false;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
