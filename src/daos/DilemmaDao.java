package daos;

import exceptions.FillPreparedStatementException;
import exceptions.ReadFromResultSetException;
import models.Dilemma;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DilemmaDao extends GenericDao<Dilemma> {

    private final String tableName = "dilemma";
    private final String[] columnNames= {
            "week_nr",
            "theme",
            "feedback"
    };

    public List<Dilemma> getByTheme(String theme) {

        String query =  "SELECT * FROM " + tableName + "\n" +
                        "WHERE " + columnNames[1] + " LIKE ?";

        PreparedStatement statement = PreparedStatementFactory.getPreparedStatement(query);
        try {
            statement.setString(1, "%" + theme + "%");
        } catch (SQLException exception){
            throw new FillPreparedStatementException();
        }

        return executeGetAll(statement);
    }

    public Dilemma getByWeekNr(int week) {
        PreparedStatement statement = PreparedStatementFactory.getSelectByColumnStatement(tableName, columnNames[0]);

        try {
            statement.setInt(1, week);
        } catch (SQLException exception){
            throw new FillPreparedStatementException();
        }

        return executeGetByAttribute(statement);
    }

    /**
     *
     * @param weekNr
     * @return
     */
    public boolean dilemmaExists(Short weekNr) {

        String query = "SELECT (COUNT(" + columnNames[0] + ") >= 1)\n" +
                "FROM " + tableName + "\n" +
                "WHERE  " + columnNames[0] + " = ?;";

        PreparedStatement statement = PreparedStatementFactory.getPreparedStatement(query);

        try {
            statement.setShort(1, weekNr);
        } catch (SQLException exception) {
            throw new FillPreparedStatementException();
        }

        return executeIsTrue(statement);
    }

    @Override
    public Dilemma createFromResultSet(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            short week_nr = resultSet.getShort(columnNames[0]);
            String theme = resultSet.getString(columnNames[1]);
            String feedback = resultSet.getString(columnNames[2]);
            return new Dilemma(id, week_nr, theme, feedback);
        } catch (SQLException exception){
            throw new ReadFromResultSetException();
        }
    }

    @Override
    public void fillPreparedStatement(PreparedStatement preparedStatement, Dilemma dilemma){
        try {
            preparedStatement.setShort(1, dilemma.getWeekNr());
            preparedStatement.setString(2, dilemma.getTheme());
            preparedStatement.setString(3, dilemma.getFeedback());
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
    public GenericDao<Dilemma> getDao() {
        return this;
    }
}

