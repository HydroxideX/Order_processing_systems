package controller;

import model.User_Proxy;

import java.sql.SQLException;

public class controller {
    private static controller controller;
    private static User_Proxy proxy;

    private controller() {

    }

    public static controller get_instance() throws SQLException {
        if (controller == null) {
            controller = new controller();
            proxy = new User_Proxy();
        }
        return controller;
    }

    public boolean login(String user_name, String password) throws SQLException {
        System.out.println(user_name);
        System.out.println(password);
        return proxy.login(user_name,password);
    }


}
