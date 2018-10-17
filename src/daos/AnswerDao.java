package daos;

import models.Answer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return GenericDaoImplementation.getAll(this);
    }

    @Override
    public Answer getById(int id) {
        return GenericDaoImplementation.getById(this, id);
    }

    public Answer[] getByDilemmaId(int dilemmaId){
        Answer[] answers = new Answer[2];

        String query = "SELECT *\n" +
                "FROM " + tableName + "\n" +
                "WHERE " + columnNames[0] + " = ?;";

        PreparedStatement statement = PreparedStatementFactory.getPreparedStatement(query);

        try {
            statement.setInt(1, dilemmaId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            answers[0] = createFromResultSet(resultSet);
            resultSet.next();
            answers[1] = createFromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        PreparedStatementFactory.closeTransaction(statement);

        return answers;
    }

    @Override
    public int save(Answer savedAnswer) {
        return GenericDaoImplementation.save(this, savedAnswer);
    }

    @Override
    public boolean update(Answer updatedAnswer) {
        return GenericDaoImplementation.update(this, updatedAnswer, updatedAnswer.getId());
    }

    @Override
    public boolean delete(Answer deletedAnswer) {
        return GenericDaoImplementation.delete(this, deletedAnswer.getId());
    }

    @Override
    public boolean deleteById(int answerId) {
        return GenericDaoImplementation.delete(this, answerId);
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
            return null;
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
}

