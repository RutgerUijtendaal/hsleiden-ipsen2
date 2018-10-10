package daos;

import models.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDao implements GenericDao<Admin>{

    @Override
    public List<Admin> getAll() throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        String statement = "SELECT * FROM admin";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Admin> admins = new ArrayList<>();
        while(resultSet.next()){
            int id = resultSet.getInt("id");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            int rights_id = resultSet.getInt("rights_id");
            Date signup_date = resultSet.getDate("signup_date");
            Admin admin = new Admin(id,email,password,rights_id,signup_date);
        }
        return null;
    }

    @Override
    public Admin getByPK(int pk) {
        return null;
    }

    @Override
    public void save(Admin newAdmin) {

    }

    @Override
    public void update(Admin updatedAdmin, String[] params) {

    }

    @Override
    public void delete(Admin deletedAdmin) {

    }
}

