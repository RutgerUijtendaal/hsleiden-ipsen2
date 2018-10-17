package daos;

import models.Couple;
import models.Parent;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoupleDao implements GenericDao<Couple> {

    private final String tableName = "couple";
    private final String[] columnNames = {
            "parent1_id",
            "parent2_id",
            "signup_date"
    };

    @Override
    public List<Couple> getAll() {
        List<Couple> couples = new ArrayList<>();

        PreparedStatement preparedStatement = DaoManager.getSelectAllStatement(tableName);

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                couples.add(createCoupleFromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(preparedStatement);

        return couples;
    }

    @Override
    public Couple getById(int id) {
        Couple couple = null;

        PreparedStatement statement = DaoManager.getSelectByIdStatement(tableName, id);

        try {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            couple = createCoupleFromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);

        return couple;
    }

    public Couple getByParent(Parent parent) {
        Couple couple = null;

        PreparedStatement statement = DaoManager.getPreparedStatement(
                "SELECT * FROM " + tableName +
                        " WHERE " + columnNames[0] + " = " + parent.getId() + " OR " + columnNames[1] + " = " + parent.getId()
        );

        try {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            couple = createCoupleFromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);

        return couple;
    }

    @Override
    public void save(Couple savedCouple) {
        PreparedStatement statement = DaoManager.getInsertStatement(tableName, columnNames);
        try {
            fillPreparedStatement(statement, savedCouple);
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        DaoManager.closeTransaction(statement);
    }

    @Override
    public void update(Couple updatedCouple) {

    }

    @Override
    public void delete(Couple deletedCouple) {
        PreparedStatement statement = DaoManager.getDeleteStatement(tableName, deletedCouple.getId());
        try {
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        DaoManager.closeTransaction(statement);
    }

    public void deleteById(int couple_id) {
        PreparedStatement statement = DaoManager.getDeleteStatement(tableName, couple_id);
        try {
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        DaoManager.closeTransaction(statement);
    }

    private Couple createCoupleFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        Date signup_date = resultSet.getDate("signup_date");
        int parent1_id = resultSet.getInt("parent1_id");
        int parent2_id = resultSet.getInt("parent2_id");
        return new Couple(id, signup_date, parent1_id, parent2_id);
    }

    private void fillPreparedStatement(PreparedStatement preparedStatement, Couple couple) throws SQLException {
        preparedStatement.setInt(1, couple.getParent1_id());
        preparedStatement.setInt(2, couple.getParent2_id());
        preparedStatement.setDate(3, couple.getSignupDate());
    }
}

