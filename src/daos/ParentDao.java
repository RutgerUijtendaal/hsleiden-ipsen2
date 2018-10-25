package daos;

import exceptions.FailedToFillPreparedStatementException;
import exceptions.FailedToReadFromResultSetException;
import models.Parent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ParentDao extends GenericDao<Parent> {

    private final String tableName = "parent";
    private final String[] columnNames= {
            "first_name",
            "email",
            "phone_nr"
    };

    /**
     * Check if the email already exists in the database.
     *
     * @param parent_email email to check.
     * @return true if email exists, false otherwise.
     */
    public boolean emailExists(String parent_email) {

        String query = "SELECT (COUNT(" + columnNames[1] + ") >= 1)\n" +
                "FROM " + tableName + "\n" +
                "WHERE " + columnNames[1] + " = ?;";

        PreparedStatement statement = PreparedStatementFactory.getPreparedStatement(query);

        try {
            statement.setString(1, parent_email);
        } catch (SQLException exception){
            exception.printStackTrace();
            throw new FailedToFillPreparedStatementException();
        }

        return executeIsTrue(statement);
    }

    @Override
    public Parent createFromResultSet(ResultSet resultSet){
        try {
            int id = resultSet.getInt("id");
            String first_name = resultSet.getString(columnNames[0]);
            String email = resultSet.getString(columnNames[1]);
            String phone_number = resultSet.getString(columnNames[2]);

            return new Parent(id, phone_number, first_name, email);
        } catch (SQLException exception){
            exception.printStackTrace();
            throw new FailedToReadFromResultSetException();
        }
    }

    @Override
    public void fillPreparedStatement(PreparedStatement preparedStatement, Parent parent){
        try {
            preparedStatement.setString(1, parent.getFirstName());
            preparedStatement.setString(2, parent.getEmail());
            preparedStatement.setString(3, parent.getPhoneNr());
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

    @Override
    public GenericDao<Parent> getDao() {
        return this;
    }
}

