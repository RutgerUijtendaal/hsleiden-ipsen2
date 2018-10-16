package daos;

import models.Admin;

import java.sql.*;
import java.util.ArrayList;
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
        List<Admin> admins = new ArrayList<>();

        PreparedStatement preparedStatement = DaoManager.getSelectAllStatement(tableName);

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                admins.add(createAdminFromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(preparedStatement);

        return admins;
    }

    @Override
    public Admin getById(int id) {
        Admin admin = null;

        PreparedStatement statement = DaoManager.getSelectByIdStatement(tableName, id);

        try {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            admin = createAdminFromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);

        return admin;
    }

    @Override
    public int save(Admin savedAdmin) {
        int generatedKey = -1;

        PreparedStatement statement = DaoManager.getInsertStatement(tableName, columnNames);

        try{
            fillPreparedStatement(statement, savedAdmin);
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            generatedKey = resultSet.getInt(1);
            resultSet.close();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);

        return generatedKey;
    }

    @Override
    public void update(Admin updatedAdmin) {
        PreparedStatement statement = DaoManager.getUpdateStatement(columnNames, tableName, updatedAdmin.getId());

        try{
            fillPreparedStatement(statement, updatedAdmin);
            statement.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);
    }

    @Override
    public void delete(Admin deletedAdmin) {
        PreparedStatement statement = DaoManager.getDeleteStatement(tableName, deletedAdmin.getId());

        try{
            statement.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);
    }

    private Admin createAdminFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        int rights_id = resultSet.getInt("rights_id");
        Date signup_date = resultSet.getDate("signup_date");
        return new Admin(id, email, password, rights_id, signup_date);
    }

    private void fillPreparedStatement(PreparedStatement preparedStatement, Admin admin) throws SQLException {
        preparedStatement.setString(1, admin.getEmail());
        preparedStatement.setString(2, admin.getPassword());
        preparedStatement.setInt(3, admin.getRights_id());
        preparedStatement.setDate(4, admin.getSignup_date());
    }
}

