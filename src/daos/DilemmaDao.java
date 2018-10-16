package daos;

import models.Dilemma;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DilemmaDao implements GenericDao<Dilemma>{

    private final String tableName = "dilemma";
    private final String[] columnNames= {
            "week_nr",
            "theme",
            "feedback"
    };
    
    @Override
    public List<Dilemma> getAll() {
        List<Dilemma> dilemmas = new ArrayList<>();

        PreparedStatement preparedStatement = DaoManager.getSelectAllStatement(tableName);
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                dilemmas.add(createDilemmaFromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(preparedStatement);

        return dilemmas;
    }

    public List<Dilemma> getByTheme(String theme) {
        List<Dilemma> dilemmas = new ArrayList<>();

        String query =  "SELECT * FROM " + tableName + "\n" +
                        "WHERE " + columnNames[1] + " LIKE ?";

        PreparedStatement statement = DaoManager.getPreparedStatement(query);
        try {
            statement.setString(1, "%" + theme + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                dilemmas.add(createDilemmaFromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);

        return dilemmas;
    }

    @Override
    public Dilemma getById(int id) {
        Dilemma dilemma = null;

        PreparedStatement statement = DaoManager.getSelectByIdStatement(tableName, id);
        try {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            dilemma = createDilemmaFromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);

        return dilemma;
    }

    @Override
    public int save(Dilemma savedDilemma) {
        PreparedStatement statement = DaoManager.getInsertStatement(tableName, columnNames);

        try{
            fillPreparedStatement(statement, savedDilemma);
            statement.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);
    }

    @Override
    public boolean update(Dilemma updatedDilemma) {
        PreparedStatement statement = DaoManager.getUpdateStatement(columnNames, tableName, updatedDilemma.getId());

        try{
            fillPreparedStatement(statement, updatedDilemma);
            statement.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);
    }

    @Override
    public boolean delete(Dilemma deletedDilemma) {
        PreparedStatement statement = DaoManager.getDeleteStatement(tableName, deletedDilemma.getId());

        try{
            statement.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);
    }

    private Dilemma createDilemmaFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        short week_nr = resultSet.getShort(columnNames[0]);
        String theme = resultSet.getString(columnNames[1]);
        String feedback = resultSet.getString(columnNames[2]);

        return new Dilemma(id,week_nr,theme, feedback);
    }

    private void fillPreparedStatement(PreparedStatement preparedStatement, Dilemma dilemma) throws SQLException {
        preparedStatement.setShort(1, dilemma.getWeekNr());
        preparedStatement.setString(2, dilemma.getTheme());
        preparedStatement.setString(3, dilemma.getFeedback());
    }
}

