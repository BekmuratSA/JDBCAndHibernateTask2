package peaksoft;

import peaksoft.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
       UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("bek", "bek", (byte) 2);
        userService.getAllUsers();
        userService.removeUserById(1);
        userService.dropUsersTable();
        userService.cleanUsersTable();
    }
}
