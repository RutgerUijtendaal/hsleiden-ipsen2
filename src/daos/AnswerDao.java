package daos;

import exceptions.FillPreparedStatementException;
import exceptions.NoFurtherResultsException;
import exceptions.ReadFromResultSetException;
import models.Answer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Bas de Bruyn
 */
public class AnswerDao extends GenericDao<Answer> {
    private final String tableName = "answer";
    private final String[] columnNames= {
            "dilemma_id",
            "extension",
            "text"
    };

    public Answer[] getByDilemmaId(int dilemmaId){
        Answer[] answers;

        String query = "SELECT * FROM " + tableName + " WHERE " + columnNames[0] + " = ?;";
        PreparedStatement statement = PreparedStatementFactory.createPreparedStatement(query);

        fillParamater(statement, 1, dilemmaId);

        ResultSet resultSet = executeQuery(statement);

        try {
            answers = extractAnswersfromResultset(resultSet);
        } catch (SQLException exception) {
            throw new ReadFromResultSetException();
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
            throw new ReadFromResultSetException();
        }
    }

    @Override
    public void fillPreparedStatement(PreparedStatement preparedStatement, Answer answer){
        try {
            preparedStatement.setInt(1, answer.getDilemma_id());
            preparedStatement.setString(2, answer.getUrl());
            preparedStatement.setString(3, answer.getText());
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
    public GenericDao<Answer> getDao() {
        return this;
    }

    private Answer[] extractAnswersfromResultset(ResultSet resultSet) throws SQLException {
        Answer[] answers = new Answer[2];

        if(resultSet.next()) {
            answers[0] = createFromResultSet(resultSet);
        } else {
            throw new NoFurtherResultsException();
        }

        if(resultSet.next()) {
            answers[1] = createFromResultSet(resultSet);
        } else {
            throw new NoFurtherResultsException();
        }

        resultSet.close();

        return answers;
    }
}

