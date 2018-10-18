package daos;

import exceptions.FailedToFillPreparedStatementException;
import exceptions.FailedToReadFromResultSetException;
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

    public Admin getByEmail(String email) {
        Admin admin;

        String query = "SELECT * FROM " + tableName + "\n" +
                "WHERE " + columnNames[0] + " LIKE ?;";

        PreparedStatement statement = PreparedStatementFactory.getPreparedStatement(query);

        try {
            statement.setString(1, "%" + email + "%");
        } catch (SQLException exception){
            exception.printStackTrace();
            throw new FailedToFillPreparedStatementException();
        }

        ResultSet resultSet = GenericDaoImplementation.executeQuery(statement);

        try {
            resultSet.next();
            admin = createFromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException exception){
            exception.printStackTrace();
            throw new FailedToReadFromResultSetException();
        } finally {
            GenericDaoImplementation.closeTransaction(statement);
        }

        return admin;
    }

    /**
     * Check if the email already exists in the database.
     *
     * @param admin_email email to check.
     * @return true if email exists, false otherwise.
     */
    public boolean emailExists(String admin_email) {
        boolean exists = false;

        String query = "SELECT (COUNT(" + columnNames[0] + ") >= 1)\n" +
                "FROM " + tableName + "\n" +
                "WHERE " + columnNames[0] + " = ?;";

        PreparedStatement statement = PreparedStatementFactory.getPreparedStatement(query);

        try {
            statement.setString(1, admin_email);
        } catch (SQLException exception){
            exception.printStackTrace();
            throw new FailedToFillPreparedStatementException();
        }

        ResultSet resultSet = GenericDaoImplementation.executeQuery(statement);

        try {
            resultSet.next();
            exists = resultSet.getBoolean(1);
            resultSet.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new FailedToReadFromResultSetException();
        } finally {
            GenericDaoImplementation.closeTransaction(statement);
        }

        return exists;
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
            throw new FailedToReadFromResultSetException();
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
            throw new FailedToFillPreparedStatementException();
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

