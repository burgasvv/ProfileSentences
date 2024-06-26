package com.burgas.profilesentences.dao;

import com.burgas.profilesentences.entity.Role;
import com.burgas.profilesentences.entity.User;
import com.burgas.profilesentences.entity.UserRole;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDao extends Dao<UserRole> {

    private record ResultUserRoleData(int userdataId, int roledataId, String username,
                                      String email, String password, String regDate, String roleName) {
    }

    private ResultUserRoleData getResultUserRoleData(ResultSet resultSet) throws SQLException {

        int userdataId = resultSet.getInt("user_id");
        int roledataId = resultSet.getInt("role_id");
        String username = resultSet.getString("username");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String regdate = resultSet.getString("regdate");
        String roleName = resultSet.getString("name");

        return new ResultUserRoleData(userdataId, roledataId, username, email, password, regdate, roleName);
    }

    private UserRole getUserRole(String name, String query) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            ResultUserRoleData result = getResultUserRoleData(resultSet);

            return new UserRole(
                    new User(result.userdataId(), result.username(), result.email(), result.password(), result.regDate()),
                    new Role(result.roledataId(), result.roleName())
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserRole getByName(String name) {

        String query = """
                select * from user_role
                join roles r on r.id = user_role.role_id
                join users u on u.id = user_role.user_id
                where username = ?""";

        return getUserRole(name, query);
    }

    @Override
    public UserRole getByEmail(String email) {

        String query = """
                select * from user_role
                join roles r on r.id = user_role.role_id
                join users u on u.id = user_role.user_id
                where email = ?""";

        return getUserRole(email, query);
    }

    @Override
    public List<UserRole> getAll() {

        List<UserRole>userRoles = new ArrayList<>();
        String query = """
                select * from user_role
                join roles r on r.id = user_role.role_id
                join users u on u.id = user_role.user_id""";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                ResultUserRoleData result = getResultUserRoleData(resultSet);

                userRoles.add(
                        new UserRole(
                                new User(result.userdataId, result.username, result.email, result.password),
                                new Role(result.roledataId, result.roleName)
                        )
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userRoles;
    }

    @Override
    public void insert(UserRole userRole) {

        String query = "insert into user_role(user_id, role_id) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setInt(1, userRole.getUser().getId());
            preparedStatement.setInt(2, userRole.getRole().getId());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(UserRole userRole) {

        String query = "update user_role set role_id = ? where user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setInt(1, userRole.getRole().getId());
            preparedStatement.setInt(2, userRole.getUser().getId());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {

    }
}
