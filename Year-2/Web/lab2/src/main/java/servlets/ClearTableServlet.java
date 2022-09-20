package servlets;

import data.ResultList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/clear")
public class ClearTableServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResultList results;
        if(req.getSession().getAttribute("results") == null) {
            results = new ResultList();
        } else {
            results = (ResultList) req.getSession().getAttribute("results");
        }

        results.clear();
        req.getSession().setAttribute("results", results);
        req.getRequestDispatcher("/index.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req,resp);
    }
}
