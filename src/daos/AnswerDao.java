package daos;

import exceptions.FailedToFillPreparedStatementException;
import exceptions.FailedToReadFromResultSetException;
import models.Answer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnswerDao extends GenericDao<Answer> {
    private final String tableName = "answer";
    private final String[] columnNames= {
            "dilemma_id",
            "url_pic",
            "text"
    };

    public Answer[] getByDilemmaId(int dilemmaId){
        Answer[] answers = new Answer[2];

        String query = "SELECT *\n" +
                "FROM " + tableName + "\n" +
                "WHERE " + columnNames[0] + " = ?;";

        PreparedStatement statement = PreparedStatementFactory.getPreparedStatement(query);

        try {
            statement.setInt(1, dilemmaId);
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new FailedToFillPreparedStatementException();
        }

        ResultSet resultSet = executeQuery(statement);

        try {
            resultSet.next();
            answers[0] = createFromResultSet(resultSet);
            resultSet.next();
            answers[1] = createFromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new FailedToReadFromResultSetException();
        } finally {
            closeTransaction(statement);
        }

        return answers;
    }

    @Override
    public Answer createFromResultSet(ResultSet resultSet){
        try {
            int id = resultSet.getInt("id");
            int dilemma_id = resultSet.getInt(columnNames[0]);
            String url_pic = resultSet.getString(columnNames[1]);
            String text = resultSet.getString(columnNames[2]);

            return new Answer(id,dilemma_id,url_pic,text);
        } catch (SQLException exception){
            exception.printStackTrace();
            throw new FailedToReadFromResultSetException();
        }
    }

    @Override
    public void fillPreparedStatement(PreparedStatement preparedStatement, Answer answer){
        try {
            preparedStatement.setInt(1, answer.getDilemma_id());
            preparedStatement.setString(2, answer.getUrl());
            preparedStatement.setString(3, answer.getText());
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
    public GenericDao<Answer> getDao() {
        return this;
    }
}

