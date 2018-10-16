package daos;

import models.Couple;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoupleDao implements GenericDao<Couple>{

    private final String tableName = "couple";
    private final String[] columnNames= {
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
        } catch (SQLException exception){
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

    @Override
    public void save(Couple savedCouple) {
        PreparedStatement statement = DaoManager.getInsertStatement(tableName,columnNames);
        try {
            fillPreparedStatement(statement, savedCouple);
            statement.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
        DaoManager.closeTransaction(statement);
    }

    /**
     * Insert a Couple into the db and return the key. This method does not close the transaction.
     *
     * If you're saving a single couple or are at the end of a transaction use save().
     *
     * @param savedCouple Couple to save.
     * @return int db key of the saved couple
     */
    public int saveWithReturnId(Couple savedCouple) throws SQLException {
        PreparedStatement statement = DaoManager.getInsertStatementWithReturn(tableName, columnNames);
        try {
            fillPreparedStatement(statement, savedCouple);
            statement.execute();
            statement.getConnection().commit();
        } catch (SQLException exception){
            DaoManager.rollBackTransaction(statement);
            throw exception;
        }
        return getKeyFromStatement(statement);
    }

    @Override
    public void update(Couple updatedCouple) {

    }

    @Override
    public void delete(Couple deletedCouple) {
        PreparedStatement statement = DaoManager.getDeleteStatement(tableName, deletedCouple.getId());
        try{
            statement.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
        DaoManager.closeTransaction(statement);
    }

    public void deleteById(int couple_id) {
        PreparedStatement statement = DaoManager.getDeleteStatement(tableName, couple_id);
        try{
            statement.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
        DaoManager.closeTransaction(statement);
    }
    
    private Couple createCoupleFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        Date signup_date = resultSet.getDate("signup_date");
        int parent1_id = resultSet.getInt("parent1_id");
        int parent2_id = resultSet.getInt("parent2_id");
        return new Couple(id,signup_date,parent1_id,parent2_id);
    }

    private void fillPreparedStatement(PreparedStatement preparedStatement, Couple couple) throws SQLException {
        preparedStatement.setInt(1, couple.getParent1_id());
        preparedStatement.setInt(2, couple.getParent2_id());
        preparedStatement.setDate(3, couple.getSignupDate());
    }

    private int getKeyFromStatement(PreparedStatement statement) {
        int key = 0;
        try {
            ResultSet rs = statement.getGeneratedKeys();
            if (rs != null && rs.next()) {
                key = rs.getInt(1);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return key;
    }
}

