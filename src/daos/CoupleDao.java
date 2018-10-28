package daos;

import exceptions.FailedToFillPreparedStatementException;
import exceptions.FailedToReadFromResultSetException;
import models.Couple;
import models.Parent;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoupleDao extends GenericDao<Couple> {

    private final String tableName = "couple";
    private final String[] columnNames = {
            "parent1_id",
            "parent2_id",
            "signup_date"
    };

    public Couple getByParent(Parent parent) {
        String query = "SELECT * FROM " + tableName + " WHERE " + columnNames[0] + " = ? OR " + columnNames[1] + " = ?;";
        PreparedStatement preparedStatement = PreparedStatementFactory.getPreparedStatement(query);

        try {
            preparedStatement.setInt(1, parent.getId());
            preparedStatement.setInt(2, parent.getId());
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new FailedToFillPreparedStatementException();
        }

        return executeGetByAttribute(preparedStatement);
    }

    @Override
    public Couple createFromResultSet(ResultSet resultSet){
        try {
            int id = resultSet.getInt("id");
            Date signup_date = resultSet.getDate("signup_date");
            int parent1_id = resultSet.getInt("parent1_id");
            int parent2_id = resultSet.getInt("parent2_id");
            return new Couple(id, signup_date, parent1_id, parent2_id);
        } catch (SQLException exception){
            exception.printStackTrace();
            throw new FailedToReadFromResultSetException();
        }
    }

    @Override
    public void fillPreparedStatement(PreparedStatement preparedStatement, Couple couple){
        try {
            preparedStatement.setInt(1, couple.getParent1_id());
            preparedStatement.setInt(2, couple.getParent2_id());
            preparedStatement.setDate(3, couple.getSignupDate());
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
    public GenericDao<Couple> getDao() {
        return this;
    }
}

