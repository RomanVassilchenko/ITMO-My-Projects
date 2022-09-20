package servlets;

import data.Result;
import data.ResultList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@WebServlet("/checkArea")
public class AreaCheckServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long startTime = System.nanoTime();
            ResultList results;
            if(req.getSession().getAttribute("results") == null) results = new ResultList();
            else results = (ResultList) req.getSession().getAttribute("results");

            if(isValid(req.getParameter("x"),req.getParameter("y"),req.getParameter("r"))) {
                Result newResult = getResult(
                        req.getParameter("x"),
                        req.getParameter("y"),
                        req.getParameter("r"),
                        startTime);
                results.addResult(newResult);
            } else {
                throw new IllegalArgumentException("Illegal Arguments!");
            }

            req.getSession().setAttribute("results",results);

        } catch (IllegalArgumentException e){
            resp.setStatus(400);
            req.setAttribute("errorMessage", "Error: " + e.getMessage());
        } finally {
            req.getRequestDispatcher("/index.jsp").forward(req, resp); //TODO go back to lab2/
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    private boolean validateX (String xVal) {
        try {
            double x = Double.parseDouble(xVal);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validateY (String yVal) {
        try {
            double y = Double.parseDouble(yVal);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validateR (String rVal) {
        try {
            double r = Double.parseDouble(rVal);
            List<Double> rRange = Arrays.asList(1.0,2.0,3.0,4.0,5.0);
            return rRange.contains(r);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValid(String xVal, String yVal, String rVal){
        return validateX(xVal) && validateY(yVal) && validateR(rVal);
    }

    private boolean checkRectangle(double x, double y, double r){
        return (x >= 0 && y >= 0) && (x <= r/2) && (y <= r);
    }

    private boolean checkTriangle(double x, double y, double r) {
        return (x >= 0 && y <= 0) && (y >= x-r/2);
    }

    private boolean checkSector(double x, double y, double r) {
        return(x <= 0 && y <= 0) && (Math.pow(x,2) + Math.pow(y,2) <= Math.pow(r,2));
    }

    private boolean checkHit(double x, double y, double r){
        return checkRectangle(x,y,r) || checkTriangle(x,y,r) || checkSector(x,y,r);
    }

    private Result getResult(String xVal, String yVal, String rVal, long startTime) {
        double x = Double.parseDouble(xVal);
        double y = Double.parseDouble(yVal);
        double r = Double.parseDouble(rVal);

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String currTime = dateFormat.format(calendar.getTime());
        String execTime = String.valueOf(System.nanoTime() - startTime);
        return new Result(x,y,r,currTime,execTime,checkHit(x,y,r));
    }

}
