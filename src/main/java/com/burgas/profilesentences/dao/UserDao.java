package com.burgas.profilesentences.dao;

import com.burgas.profilesentences.entity.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends Dao<User>{

    public static final String COLUMN_LABEL_USERNAME = "username";
    public static final String COLUMN_LABEL_EMAIL = "email";
    public static final String COLUMN_LABEL_PASSWORD = "password";
    public static final String COLUMN_LABEL_REGDATE = "regdate";
    public static final String COLUMN_LABEL_ID = "id";

    private static final class ResultUserData {
        private int id;
        private final String userName;
        private final String email;
        private final String password;
        private final String registrDate;

        public ResultUserData(int id, String userName, String email, String password, String registrDate) {
            this.id = id;
            this.userName = userName;
            this.email = email;
            this.password = password;
            this.registrDate = registrDate;
        }

        private ResultUserData(String userName, String email, String password, String registrDate) {
            this.userName = userName;
            this.email = email;
            this.password = password;
            this.registrDate = registrDate;
        }

        public int getId() {
            return id;
        }
    }

    private User getUserData(PreparedStatement preparedStatement) throws SQLException {

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        ResultUserData result = getResultUserData(resultSet);

        return new User(result.userName, result.email, result.password, result.registrDate);
    }

    private ResultUserData getResultUserData(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt(COLUMN_LABEL_ID);
        String userName = resultSet.getString(COLUMN_LABEL_USERNAME);
        String email = resultSet.getString(COLUMN_LABEL_EMAIL);
        String password = resultSet.getString(COLUMN_LABEL_PASSWORD);
        String loginDate = resultSet.getString(COLUMN_LABEL_REGDATE);

        return new ResultUserData(id, userName, email, password, loginDate);
    }

    private List<User> getUserDataList(PreparedStatement preparedStatement, List<User> userList)
            throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            ResultUserData result = getResultUserData(resultSet);
            userList.add(
                    new User(result.getId(), result.userName, result.email, result.password, result.registrDate)
            );
        }

        return userList;
    }

    private void setStatementParameters(User user, PreparedStatement preparedStatement)
            throws SQLException {

        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getEmail());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
    }

    @Override
    public User getByName(String name) {

        String query = "select id, username, email, password, regdate from users where username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setString(1, name);

            return getUserData(preparedStatement);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getByEmail(String email) {

        String query = "select id, username, email, password, regdate from users where email = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setString(1, email);

            return getUserData(preparedStatement);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {

        List<User> userList = new ArrayList<>();
        String query = "select id, userName, email, password, regdate from users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){

            return getUserDataList(preparedStatement, userList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void insert(User user) {

        String query = "insert into users(username, email, password, regdate) values(?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){

            setStatementParameters(user, preparedStatement);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {

        String query = "update users set username = ? and email = ? and password = ? where id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getId());

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {

        String query = "delete from users where id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setInt(1, id);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
