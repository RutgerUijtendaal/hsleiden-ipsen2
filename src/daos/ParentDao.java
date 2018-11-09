package daos;

import exceptions.FillPreparedStatementException;
import exceptions.ReadFromResultSetException;
import models.Parent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Bas de Bruyn
 */
public class ParentDao extends GenericDao<Parent> {

    private final String tableName = "parent";
    private final String[] columnNames= {
            "first_name",
            "email",
            "phone_nr"
    };

    public boolean emailExists(String parent_email) {

        String query = "SELECT (COUNT(" + columnNames[1] + ") >= 1)\n" +
                "FROM " + tableName + "\n" +
                "WHERE " + columnNames[1] + " = ?;";

        PreparedStatement statement = PreparedStatementFactory.createPreparedStatement(query);

        fillParamater(statement,1, parent_email);

        return executeIsTrue(statement);
    }

    public Parent getByEmail(String email) {
        PreparedStatement preparedStatement = PreparedStatementFactory.createSelectByAttributeStatement(tableName, columnNames[1]);

        fillParamater(preparedStatement,1, email);

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

