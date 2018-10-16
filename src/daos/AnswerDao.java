package daos;

import models.Answer;
import models.Dilemma;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswerDao implements GenericDao<Answer>{
    private final String tableName = "answer";
    private final String[] columnNames= {
            "dilemma_id",
            "url_pic",
            "text"
    };

    @Override
    public List<Answer> getAll() {
        List<Answer> answers = new ArrayList<>();

        PreparedStatement preparedStatement = DaoManager.getSelectAllStatement(tableName);

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                answers.add(createAnswerFromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(preparedStatement);

        return answers;
    }

    @Override
    public Answer getById(int id) {
        Answer answer = null;

        PreparedStatement statement = DaoManager.getSelectByIdStatement(tableName, id);

        try {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            answer = createAnswerFromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);

        return answer;
    }

    public Answer[] getByDilemma(Dilemma dilemma) {
        return null;
    }

    @Override
    public void save(Answer savedAnswer) {
        PreparedStatement statement = DaoManager.getInsertStatement(tableName, columnNames);

        try{
            fillPreparedStatement(statement, savedAnswer);
            statement.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);
    }

    @Override
    public void update(Answer updatedAnswer) {
        PreparedStatement statement = DaoManager.getUpdateStatement(columnNames, tableName, updatedAnswer.getId());

        try{
            fillPreparedStatement(statement, updatedAnswer);
            statement.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);
    }

    @Override
    public void delete(Answer deletedAnswer) {
        PreparedStatement statement = DaoManager.getDeleteStatement(tableName, deletedAnswer.getId());

        try{
            statement.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);
    }

    private Answer createAnswerFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int dilemma_id = resultSet.getInt(columnNames[0]);
        String url_pic = resultSet.getString(columnNames[1]);
        String text = resultSet.getString(columnNames[2]);

        return new Answer(id,dilemma_id,url_pic,text);
    }

    private void fillPreparedStatement(PreparedStatement preparedStatement, Answer answer) throws SQLException {
        preparedStatement.setInt(1, answer.getDilemma_id());
        preparedStatement.setString(2, answer.getUrl());
        preparedStatement.setString(3, answer.getText());
    }
}

