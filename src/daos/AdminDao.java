package daos;

import models.Admin;

import java.sql.*;
import java.util.List;

public class AdminDao implements GenericDao<Admin>{

    private final String tableName = "admin";
    private final String[] columnNames= {
            "email",
            "password",
            "rights_id",
            "signup_date"
    };

    @Override
    public List<Admin> getAll() {
        return GenericDaoImplementation.getAll(this);
    }

    @Override
    public Admin getById(int id) {
        return GenericDaoImplementation.getById(this, id);
    }

    @Override
    public int save(Admin savedAdmin) {
        return GenericDaoImplementation.save(this, savedAdmin);
    }

    @Override
    public boolean update(Admin updatedAdmin) {
        return GenericDaoImplementation.update(this, updatedAdmin, updatedAdmin.getId());
    }

    @Override
    public boolean delete(Admin deletedAdmin) {
        return GenericDaoImplementation.delete(this, deletedAdmin.getId());
    }

    @Override
    public boolean deleteById(int adminId) {
        return GenericDaoImplementation.delete(this, adminId);
    }

    @Override
    public Admin createFromResultSet(ResultSet resultSet){
        try {
            int id = resultSet.getInt("id");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            int rights_id = resultSet.getInt("rights_id");
            Date signup_date = resultSet.getDate("signup_date");
            return new Admin(id, email, password, rights_id, signup_date);
        } catch (SQLException exception){
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void fillPreparedStatement(PreparedStatement preparedStatement, Admin admin){

        try {
            preparedStatement.setString(1, admin.getEmail());
            preparedStatement.setString(2, admin.getPassword());
            preparedStatement.setInt(3, admin.getRights_id());
            preparedStatement.setDate(4, admin.getSignup_date());
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String[] getColumnNames() {
        return columnNames;
    }
}

