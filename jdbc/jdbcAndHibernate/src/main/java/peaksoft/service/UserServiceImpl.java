package peaksoft.service;

import peaksoft.dao.UserDaoJdbcImpl;
import peaksoft.model.User;
import peaksoft.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    public UserDaoJdbcImpl userDaoJdbc;

    public UserServiceImpl(UserDaoJdbcImpl userDaoJdbc) {
        this.userDaoJdbc = userDaoJdbc;
    }

    public UserServiceImpl() {
    }

    public void createUsersTable() {
        String sql = "Create Table if not exists Users" +
                "(id Serial primary key not null," +
                "name varchar(250)," +
                "lastName varchar(25)," +
                "age integer not null)";

        try (Connection connection = Util.connection();
             Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
            System.out.println("created table user!");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        String sql = "drop table Users";
        try (Connection connection = Util.connection();
             Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
            System.out.println("deleted table users!");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "Insert into Users(name, lastName, age) Values(?,?,?)";
        try( Connection connection = Util.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,name);
            statement.setString(2,lastName);
            statement.setByte(3,age);
            statement.executeUpdate();
            System.out.println("saved "+ name +"!");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String sql = "delete from Users Where id = ?";
        try (Connection connection = Util.connection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1,id);
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "Select * from Users";
        try {
            Connection connection = Util.connection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                User user = new User();

                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);
                System.out.println(users);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return users;
    }

    public void cleanUsersTable() {
        String sql = "Truncate table Users";
        try {
            Connection connection = Util.connection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
