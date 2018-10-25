package daos;

import exceptions.FailedToFillPreparedStatementException;
import exceptions.FailedToReadFromResultSetException;
import models.Dilemma;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            exception.printStackTrace();
            throw new FailedToFillPreparedStatementException();
        }

        return executeGetAll(statement);
    }

    @Override
    public List<Dilemma> getAll() {
        return GenericDaoImplementation.getAll(this);
    }

    @Override
    public Dilemma getById(int id) {
        return GenericDaoImplementation.getById(this, id);
    }

    public Dilemma getByWeekNr(int week) {
        return GenericDaoImplementation.getByColumn(this, columnNames[0], week);
    }

    @Override
    public int save(Dilemma savedDilemma) {
        return GenericDaoImplementation.save(this, savedDilemma);
    }

    @Override
    public boolean deleteById(int dilemmaId) {
        return GenericDaoImplementation.delete(this, dilemmaId);
    }

    /**
     *
     * @param weekNr
     * @return
     */
    public boolean dilemmaExists(Short weekNr) {

        String query = "SELECT (COUNT(" + columnNames[0] + ") >= 1)\n" +
                "FROM " + tableName + "\n" +
                "WHERE  " + columnNames[1] + " = ?;";

        PreparedStatement statement = PreparedStatementFactory.getPreparedStatement(query);

        try {
            statement.setShort(1, weekNr);
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new FailedToFillPreparedStatementException();
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
            exception.printStackTrace();
            throw new FailedToReadFromResultSetException();
        }
    }

    @Override
    public void fillPreparedStatement(PreparedStatement preparedStatement, Dilemma dilemma){
        try {
            preparedStatement.setShort(1, dilemma.getWeekNr());
            preparedStatement.setString(2, dilemma.getTheme());
            preparedStatement.setString(3, dilemma.getFeedback());
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
    public GenericDao<Dilemma> getDao() {
        return this;
    }
}

