package daos;

import exceptions.FailedToFillPreparedStatementException;
import exceptions.FailedToReadFromResultSetException;
import models.Result;

import java.sql.*;

public class ResultDao extends GenericDao<Result> {

    private final String tableName = "result";
    private final String[] columnNames= {
            "parent_id",
            "answer_id",
            "date_dilemma_sent",
            "date_dilemma_answered"
    };

    public boolean isDilemmaAnswered(int parentId){

        String query = "SELECT (COUNT(" + columnNames[0] + ") >= 1)\n" +
                "FROM " + tableName + "\n" +
                "WHERE " + columnNames[0] + " = ?\n" +
                "AND " + columnNames[3] + " IS NOT NULL;";

        PreparedStatement statement = PreparedStatementFactory.getPreparedStatement(query);

        try {
            statement.setInt(1, parentId);
        } catch (SQLException exception){
            exception.printStackTrace();
            throw new FailedToFillPreparedStatementException();
        }

        return executeIsTrue(statement);
    }

    public Result getByParentId(int id) {
        PreparedStatement preparedStatement = PreparedStatementFactory.getSelectByColumnStatement(tableName, columnNames[0], id);
        return executeGetByAttribute(preparedStatement);
    }

    @Override
    public Result createFromResultSet(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            int parent_id = resultSet.getInt(columnNames[0]);
            int answer_id = resultSet.getInt(columnNames[1]);
            Timestamp date_dilemma_sent = resultSet.getTimestamp(columnNames[2]);
            Timestamp date_dilemma_answered = resultSet.getTimestamp(columnNames[3]);
            return new Result(id, parent_id, answer_id, date_dilemma_sent, date_dilemma_answered);
        } catch (SQLException exception){
            exception.printStackTrace();
            throw new FailedToReadFromResultSetException();
        }
    }

    @Override
    public void fillPreparedStatement(PreparedStatement preparedStatement, Result result) {
        try {
            preparedStatement.setInt(1, result.getParent_id());
            if(result.getAnswer_id() == null) {
                preparedStatement.setNull(2, Types.INTEGER);
            } else {
                preparedStatement.setInt(2, result.getAnswer_id());
            }
            preparedStatement.setTimestamp(3, result.getSentTime());
            preparedStatement.setTimestamp(4, result.getAnsweredTime());
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
    public GenericDao<Result> getDao() {
        return this;
    }
}
