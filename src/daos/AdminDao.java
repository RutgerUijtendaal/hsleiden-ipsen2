package daos;

import exceptions.FillPreparedStatementException;
import exceptions.ReadFromResultSetException;
import models.Admin;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao extends GenericDao<Admin> {

    private final String tableName = "admin";
    private final String[] columnNames= {
            "email",
            "password",
            "rights_id"
    };

    public boolean updateWithoutPassword(Admin admin) {
        String [] columnsWithoutPassword = {
            columnNames[0],
            columnNames[2],
        };

        PreparedStatement statement = PreparedStatementFactory.getUpdateStatement(columnsWithoutPassword, tableName, admin.getId());

        fillPreparedStatement(statement, admin);

        boolean successfull = executeUpdate(statement);

        closeTransaction(statement);

        return successfull;
    }

    public Admin getByEmail(String email) {

        String query = "SELECT * FROM " + tableName + "\n" +
                "WHERE " + columnNames[0] + " LIKE ?;";

        PreparedStatement statement = PreparedStatementFactory.getPreparedStatement(query);

        try {
            statement.setString(1, "%" + email + "%");
        } catch (SQLException exception){
            throw new FillPreparedStatementException();
        }

        return executeGetByAttribute(statement);
    }

    /**
     * Check if the email already exists in the database.
     *
     * @param admin_email email to check.
     * @return true if email exists, false otherwise.
     */
    public boolean emailExists(String admin_email) {

        String query = "SELECT (COUNT(" + columnNames[0] + ") >= 1)\n" +
                "FROM " + tableName + "\n" +
                "WHERE " + columnNames[0] + " = ?;";

        PreparedStatement statement = PreparedStatementFactory.getPreparedStatement(query);

        try {
            statement.setString(1, admin_email);
        } catch (SQLException exception){
            throw new FillPreparedStatementException();
        }

        return executeIsTrue(statement);
    }

    @Override
    public Admin createFromResultSet(ResultSet resultSet){
        try {
            int id = resultSet.getInt("id");
            String email = resultSet.getString(columnNames[0]);
            String password = resultSet.getString(columnNames[1]);
            int rights_id = resultSet.getInt(columnNames[2]);
            return new Admin(id, email, password, rights_id);
        } catch (SQLException exception){
            throw new ReadFromResultSetException();
        }
    }

    @Override
    public void fillPreparedStatement(PreparedStatement preparedStatement, Admin admin){

        try {
            preparedStatement.setString(1, admin.getEmail());
            preparedStatement.setString(2, admin.getPassword());
            preparedStatement.setInt(3, admin.getRights_id());
        } catch (SQLException exception){
            throw new FillPreparedStatementException();
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

    @Override
    public GenericDao<Admin> getDao() {
        return this;
    }
}

