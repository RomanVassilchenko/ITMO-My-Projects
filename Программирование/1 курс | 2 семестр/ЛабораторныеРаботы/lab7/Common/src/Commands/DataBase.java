package Commands;

import Data.*;
import Exceptions.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.Stack;

public class DataBase {
    private Connection connection;
    private Statement statement;

    public DataBase(String login, String password) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://pg:5432/studs", login, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int insert(User user) throws SQLException, NotDatabaseUpdateException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("insert into users (login, password) values (?, ?) returning id");
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());

        try {
            if (preparedStatement.execute()) {
                ResultSet rs = preparedStatement.getResultSet();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
            throw new NotDatabaseUpdateException("The user object was not added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new NotDatabaseUpdateException("The user object was not added");
        }
    }

    private int insert(Coordinates coords) throws SQLException, NotDatabaseUpdateException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("insert into coordinates (x, y) values (?, ?) returning id");
        preparedStatement.setDouble(1, coords.getX());
        preparedStatement.setFloat(2, coords.getY());

        try {
            if (preparedStatement.execute()) {
                ResultSet rs = preparedStatement.getResultSet();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
            throw new NotDatabaseUpdateException("The coordinates object was not added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new NotDatabaseUpdateException("The coordinates object was not added");
        }
    }

    private int insert(Address address) throws SQLException, NotDatabaseUpdateException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("insert into address (street, zipCode) values (?, ?) returning id");
        preparedStatement.setString(1, address.getStreet());
        preparedStatement.setString(2, address.getZipCode());

        try {
            if (preparedStatement.execute()) {
                ResultSet rs = preparedStatement.getResultSet();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
            throw new NotDatabaseUpdateException("The address object was not added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new NotDatabaseUpdateException("The address object was not added");
        }
    }

    public int insert(Organization organization, int userID) throws SQLException, NotDatabaseUpdateException {
        int coordsID = insert(organization.getCoordinates());
        int addressID = insert(organization.getAddress());
        PreparedStatement preparedStatement =
                connection.prepareStatement("insert into organizations (name, coordinates_id, creationdate,annualturnover, employeescount, type, address_id, user_id) values (?, ?, ?, ?, ?, ?, ?, ?) returning id");
        preparedStatement.setString(1, organization.getName());
        preparedStatement.setInt(2, coordsID);
        preparedStatement.setDate(3, Date.valueOf(organization.getCreationDate()));
        preparedStatement.setFloat(4, organization.getAnnualTurnOver());
        preparedStatement.setLong(5, organization.getEmployeesCount());
        preparedStatement.setString(6, organization.getType().toString());
        preparedStatement.setInt(7, addressID);
        preparedStatement.setInt(8, userID);

        try {
            if (preparedStatement.execute()) {
                ResultSet rs = preparedStatement.getResultSet();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
            throw new NotDatabaseUpdateException("The Organization object was not added");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new NotDatabaseUpdateException("The Organization object was not added");
        }

    }

    public int selectUserID(String login, String password) throws SQLException, UserNotFoundException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from users where login=? and password=?");
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        if (preparedStatement.execute()) {
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }

        throw new UserNotFoundException("The user with this username or password was not found");
    }

    public Address selectAddress(int id) throws SQLException, AddressNotFoundException, FiledIncorrect {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from address where id=?");
        preparedStatement.setInt(1, id);
        if (preparedStatement.execute()) {
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()) {
                String street = rs.getString("street");
                String zipCode = rs.getString("zipCode");
                return new Address(street, zipCode);
            }
        }

        throw new AddressNotFoundException("No address with id = " + id);
    }

    public User selectUser(int id) throws SQLException, UserNotFoundException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id=?");
        preparedStatement.setInt(1, id);
        if (preparedStatement.execute()) {
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()) {
                String login = rs.getString("login");
                String password = rs.getString("password");
                return new User(login, password);
            }
        }

        throw new UserNotFoundException("No пользователя with id = " + id);
    }

    public Coordinates selectCoordinates(int id) throws SQLException, CoordinatesNotFound {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from coordinates where id=?");
        preparedStatement.setInt(1, id);
        if (preparedStatement.execute()) {
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()) {
                int x = rs.getInt("x");
                float y = rs.getFloat("y");
                return new Coordinates(x, y);
            }
        }

        throw new CoordinatesNotFound("No Coordinates with id = " + id);
    }

    public Organization selectOrganization(int id) throws SQLException, OrganizationNotFound {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from organizations where id=?");
        preparedStatement.setLong(1, id);
        if (preparedStatement.execute()) {
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()) {
                String name = rs.getString("name");
                int coords_id = rs.getInt("coordinates_id");
                LocalDate creationDate = LocalDate.from(rs.getDate("creationdate").toLocalDate());
                Float annualTurnover = rs.getFloat("annualturnover");
                Long employeesCount = rs.getLong("employeescount");
                OrganizationType type = OrganizationType.valueOf(rs.getString("type"));
                int address_id = rs.getInt("address_id");
                int user_id = rs.getInt("user_id");

                try {
                    Coordinates coordinates = selectCoordinates(coords_id);
                    Address address = selectAddress(address_id);
                    User user = selectUser(user_id);
                    return new Organization(id, name, coordinates, creationDate, annualTurnover, employeesCount, type, address, user);
                } catch (AddressNotFoundException | CoordinatesNotFound | UserNotFoundException | FiledIncorrect e) {
                    throw new OrganizationNotFound("Error reading subfields of the organization class");
                }
            }
        }

        throw new OrganizationNotFound("No organization with id = " + id);
    }

    public Stack<Organization> selectAllNotes() throws SQLException, NotDatabaseUpdateException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from organizations ");

        if (preparedStatement.execute()) {

            ResultSet rs = preparedStatement.getResultSet();
            Stack<Organization> organizations = new Stack<>();
            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                int coords_id = rs.getInt("coordinates_id");
                LocalDate creationDate = (rs.getDate("creationdate").toLocalDate());
                Float annualTurnover = rs.getFloat("annualturnover");
                Long employeesCount = rs.getLong("employeescount");
                OrganizationType type = OrganizationType.valueOf(rs.getString("type"));
                int address_id = rs.getInt("address_id");
                int user_id = rs.getInt("user_id");

                try {

                    Coordinates coordinates = selectCoordinates(coords_id);

                    Address address = selectAddress(address_id);
                    User user = selectUser(user_id);

                    Organization organization = new Organization(id, name, coordinates, creationDate, annualTurnover, employeesCount, type, address, user);
                    organizations.add(organization);

                } catch (AddressNotFoundException | CoordinatesNotFound | UserNotFoundException e) {
                    throw new NotDatabaseUpdateException("Error in updating the collection");
                } catch (FiledIncorrect filedIncorrect) {
                    filedIncorrect.printStackTrace();
                }
            }
            return organizations;
        }
        throw new NotDatabaseUpdateException("Error in updating the collection");
    }

    public void update(int id, Organization organization) throws SQLException, NotDatabaseUpdateException {
        PreparedStatement preparedStatement = connection.prepareStatement("update organizations set " +
                "(name, coordinates_id, creationdate, annualturnover, employeescount, type, address_id) " +
                "= (?, ?, ?, ?, ?, ?, ?) where id=?");
        preparedStatement.setString(1, organization.getName());
        preparedStatement.setInt(2, insert(organization.getCoordinates()));
        preparedStatement.setDate(3, Date.valueOf(organization.getCreationDate()));
        preparedStatement.setFloat(4, organization.getAnnualTurnOver());
        preparedStatement.setLong(5, organization.getEmployeesCount());
        preparedStatement.setString(6, organization.getType().toString());
        preparedStatement.setInt(7, insert(organization.getAddress()));
        preparedStatement.setInt(8, id);
        preparedStatement.execute();
    }

    public void deleteUserNotes(int userID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete from organizations where user_id=?");
        preparedStatement.setInt(1, userID);
        preparedStatement.execute();
    }

    public void deleteNote(int ID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete from organizations where id=?");
        preparedStatement.setInt(1, ID);
        preparedStatement.execute();
    }

}
