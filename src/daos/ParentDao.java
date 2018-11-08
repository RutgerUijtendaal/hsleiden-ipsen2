package daos;

import exceptions.FillPreparedStatementException;
import exceptions.ReadFromResultSetException;
import models.Parent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

        PreparedStatement statement = PreparedStatementFactory.createPreparedStatement(query);

        try {
            statement.setString(1, parent_email);
        } catch (SQLException exception){
            throw new FillPreparedStatementException();
        }

        return executeIsTrue(statement);
    }

    /**
     * Get the parent by attribute
     * @param email
     * @return
     */
    public Parent getByEmail(String email) {
        PreparedStatement preparedStatement = PreparedStatementFactory.createSelectByColumnStatement(tableName, columnNames[1]);

        try {
            preparedStatement.setString(1, email);
        } catch (SQLException exception){
            throw new FillPreparedStatementException();
        }

        return executeGetByAttribute(preparedStatement);
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
            throw new ReadFromResultSetException();
        }
    }

    @Override
    public void fillPreparedStatement(PreparedStatement preparedStatement, Parent parent){
        try {
            preparedStatement.setString(1, parent.getFirstName());
            preparedStatement.setString(2, parent.getEmail());
            preparedStatement.setString(3, parent.getPhoneNr());
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
    public GenericDao<Parent> getDao() {
        return this;
    }
}

