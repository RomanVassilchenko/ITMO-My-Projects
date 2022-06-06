package NetInteraction;

//STEP 1. Import required packages

import Application.Commander;
import Dependency.Worker;
import Dependency.Command;

import java.io.IOException;
import java.sql.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class DatabaseManager {

    //  Database credentials
    static final String DB_URL = "jdbc:postgresql://localhost:55000/studs";
    static final String USER = "postgres";
    static final String PASS = "postgrespw";
    Connection conn;
    public static final Logger logger = Logger.getLogger(Commander.class.getName());

    public DatabaseManager() throws IOException {
        conn = connect();
        Handler handler = new FileHandler("Server/logs/log5.txt");
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
    }

    public static Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.info("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
        }

        Connection connection = null;

        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }

        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }
        return connection;
    }

    public void insertWorker(Worker worker, Command user) {
        String SQL = "INSERT INTO collection(name,salary,coordinates_x,coordinates_y,position,status,birthday,height,pasportID,creationDate,author) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, worker.getName());
            pstmt.setInt(2, worker.getSalary());
            pstmt.setInt(3, worker.getCoordinates().getX());
            pstmt.setDouble(4, worker.getCoordinates().getY());
            pstmt.setString(5, String.valueOf(worker.getPosition()));
            pstmt.setString(6, String.valueOf(worker.getStatus()));
            pstmt.setString(7, String.valueOf(worker.getPerson().getBirthday()));
            pstmt.setDouble(8, worker.getPerson().getHeight());
            pstmt.setString(9, worker.getPerson().getPassportID());
            pstmt.setString(10, String.valueOf(worker.getCreationDate()));
            pstmt.setString(11, user.getLogin());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            logger.info(ex.getMessage());
        }
    }

    public void insertWorkerLowerMaxId(Worker worker, Command user) {
        String SQL = "INSERT INTO collection(name,salary,coordinates_x,coordinates_y,position,status,birthday,height,pasportID,creationDate,id,author) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, worker.getName());
            pstmt.setInt(2, worker.getSalary());
            pstmt.setInt(3, worker.getCoordinates().getX());
            pstmt.setDouble(4, worker.getCoordinates().getY());
            pstmt.setString(5, String.valueOf(worker.getPosition()));
            pstmt.setString(6, String.valueOf(worker.getStatus()));
            pstmt.setString(7, String.valueOf(worker.getPerson().getBirthday()));
            pstmt.setDouble(8, worker.getPerson().getHeight());
            pstmt.setString(9, worker.getPerson().getPassportID());
            pstmt.setString(10, String.valueOf(worker.getCreationDate()));
            pstmt.setInt(11, Math.toIntExact(worker.getId()));
            pstmt.setString(12, user.getLogin());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            logger.info(ex.getMessage());
        }
    }

    public boolean replaceIfGreater(int id, int value,Command user) {
        String request = "UPDATE collection SET salary = ? WHERE (id = ?) AND (salary < ?) AND (author = ?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(request);
            preparedStatement.setInt(1, value);
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3, value);
            preparedStatement.setString(4, user.getLogin());
            if (preparedStatement.executeUpdate() != 0) return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.info(ex.getMessage());
        }
        return false;
    }

    public void update(int id, String request, String value,Command user) {
        String SQL = "UPDATE collection "
                + "SET";
        String author = user.getLogin();
        PreparedStatement pstmt = null;
        try {
            switch (request) {
                case "name":
                    SQL += " name = ? " + "WHERE id = ? AND author = ?";
                    pstmt = conn.prepareStatement(SQL);
                    pstmt.setString(1, value);
                    break;
                case "salary":
                    SQL += " salary = ? " + "WHERE id = ? AND author = ?";
                    pstmt = conn.prepareStatement(SQL);
                    pstmt.setInt(1, Integer.parseInt(value));
                    break;
                case "coordinates_x":
                    SQL += " coordinates_x = ? " + "WHERE id = ? AND author = ?";
                    pstmt = conn.prepareStatement(SQL);
                    pstmt.setDouble(1, Double.parseDouble(value));
                    break;
                case "coordinates_y":
                    SQL += " coordinates_y = ? " + "WHERE id = ? AND author = ?";
                    pstmt = conn.prepareStatement(SQL);
                    pstmt.setDouble(1, Double.parseDouble(value));
                    break;
                case "position":
                    SQL += " position = ? " + "WHERE id = ? AND author = ?";
                    pstmt = conn.prepareStatement(SQL);
                    pstmt.setString(1, value);
                    break;
                case "status":
                    SQL += " status = ? " + "WHERE id = ? AND author = ?";
                    pstmt = conn.prepareStatement(SQL);
                    pstmt.setString(1, value);
                    break;
                case "birthday":
                    SQL += " birthday = ? " + "WHERE id = ? AND author = ?";
                    pstmt = conn.prepareStatement(SQL);
                    pstmt.setString(1, value);
                    break;
                case "height":
                    SQL += " height = ? " + "WHERE id = ? AND author = ?";
                    pstmt = conn.prepareStatement(SQL);
                    pstmt.setDouble(1, Double.parseDouble(value));
                    break;
                case "pasportid":
                    SQL += " pasportid = ? " + "WHERE id = ? AND author = ?";
                    pstmt = conn.prepareStatement(SQL);
                    pstmt.setString(1, value);
                    break;
                case "creationdate":
                    SQL += " creationdate = ? " + "WHERE id = ? AND author = ?";
                    pstmt = conn.prepareStatement(SQL);
                    pstmt.setString(1, value);
                    break;
            }
            pstmt.setInt(2, id);
            pstmt.setString(3, author);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.info(ex.getMessage());
        }
    }
    public boolean checkRule(Command user, int id){
        String SQL = "SELECT author FROM collection WHERE id=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            if (rs.getString("author").equals(user.getLogin())){
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            logger.info(ex.getMessage());
        }
        return false;
    }
    public ResultSet getWorkers() {
        String SQL = "SELECT id, name, salary, coordinates_x, coordinates_y, position, status, birthday, height, pasportid, creationdate, author FROM collection";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            return rs;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            logger.info(ex.getMessage());
            return null;
        }

    }

    public boolean clearByAuthor(Command user) {
        String SQL = "DELETE FROM collection WHERE author = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL);
            preparedStatement.setString(1, user.getLogin());
            if(preparedStatement.executeUpdate() != 0) return true;
        } catch (SQLException ex) {
            logger.info(ex.getMessage());
        }
        return false;
    }

    public boolean deleteWorker(int id,Command user) {
        String SQL = "DELETE FROM collection WHERE id = ? AND author = ?";
        String author = user.getLogin();
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, id);
            pstmt.setString(2, author);
            if(pstmt.executeUpdate() != 0) return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            logger.info(ex.getMessage());
        }
        return false;
    }

    public boolean removeGreater(int value, String type, Command user) {
        String request = "DELETE FROM collection WHERE";
        try {
            switch (type) {
                case "id":
                    request += " (id > ?) AND author = ?";
                    break;
                case "salary":
                    request += " (salary > ?) AND author = ?";
                    break;
            }
            PreparedStatement preparedStatement = conn.prepareStatement(request);
            preparedStatement.setInt(1, value);
            preparedStatement.setString(2, user.getLogin());
            if (preparedStatement.executeUpdate() != 0) return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.info(ex.getMessage());
        }
        return false;
    }

    public ResultSet getUser(String login, String password) {
        String SQL = "SELECT login, password FROM users WHERE login=? AND password = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, login);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            logger.info(ex.getMessage());
            return null;
        }
    }
    public boolean checkUserAuthorize(Command user) throws SQLException {
        ResultSet rs = getUser(user.getLogin(),user.getPassword());
        int count= 0;
        while(rs.next()){
            count++;
        }
        if(count>0){
            return true;
        }
        return false;
    }
    public boolean checkLogin(Command user) {
        String SQL = "SELECT login FROM users WHERE login=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, user.getLogin());
            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count > 0) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            logger.info(ex.getMessage());
        }
        return false;
    }

    public void insertUser(Command user) {
        String SQL = "INSERT INTO users(login,password) VALUES(?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, user.getLogin());
            pstmt.setString(2, user.getPassword());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            logger.info(ex.getMessage());
        }
    }
}
