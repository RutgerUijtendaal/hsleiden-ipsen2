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

    public List<Dilemma> getByTheme(String theme) {
        List<Dilemma> dilemmas = new ArrayList<>();

        String query =  "SELECT * FROM " + tableName + "\n" +
                        "WHERE " + columnNames[1] + " LIKE ?";

        PreparedStatement statement = PreparedStatementFactory.getPreparedStatement(query);
        try {
            statement.setString(1, "%" + theme + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                dilemmas.add(createFromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);

        return dilemmas;
    }

    @Override
    public List<Dilemma> getAll() {
        return DaoManager.getAll(this);
    }

    @Override
    public Dilemma getById(int id) {
        return DaoManager.getById(this, id);
    }

    @Override
    public int save(Dilemma savedDilemma) {
        return DaoManager.save(this, savedDilemma);
    }

    @Override
    public boolean update(Dilemma updatedDilemma) {
        return DaoManager.update(this, updatedDilemma, updatedDilemma.getId());
    }

    @Override
    public boolean delete(Dilemma deletedDilemma) {
        return DaoManager.delete(this, deletedDilemma.getId());
    }

    @Override
    public boolean deleteById(int dilemmaId) {
        return DaoManager.delete(this, dilemmaId);
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
            return null;
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

