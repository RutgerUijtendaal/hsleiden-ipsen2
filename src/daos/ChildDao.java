package daos;

import exceptions.FillPreparedStatementException;
import exceptions.ReadFromResultSetException;
import models.Child;
import models.Couple;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChildDao extends GenericDao<Child> {
    private final String tableName = "child";
    private final String[] columnNames = {
            "couple_id",
            "is_born",
            "date"
    };

    public Child getByCouple(Couple couple) {
        PreparedStatement preparedStatement = PreparedStatementFactory.createSelectByColumnStatement(tableName, columnNames[0]);

        try {
            preparedStatement.setInt(1, couple.getId());
        } catch (SQLException exception){
            throw new FillPreparedStatementException();
        }

        return executeGetByAttribute(preparedStatement);
    }

    @Override
    public Child createFromResultSet(ResultSet resultSet){
        try {
            int id = resultSet.getInt("id");
            int couple_id = resultSet.getInt(columnNames[0]);
            boolean is_born = resultSet.getBoolean(columnNames[1]);
            Date date = resultSet.getDate(columnNames[2]);

            return new Child(id, couple_id, date, is_born);
        } catch (SQLException exception){
            throw new ReadFromResultSetException();
        }
    }

    @Override
    public void fillPreparedStatement(PreparedStatement preparedStatement, Child child){
        try {
            preparedStatement.setInt(1, child.getCouple_id());
            preparedStatement.setBoolean(2, child.getIsBorn());
            preparedStatement.setDate(3, child.getDate());
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
    public GenericDao<Child> getDao() {
        return this;
    }
}

