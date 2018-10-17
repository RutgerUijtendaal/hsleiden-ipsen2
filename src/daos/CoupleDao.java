package daos;

import exceptions.FailedToFillPreparedStatementException;
import exceptions.FailedToReadFromResultSetException;
import models.Couple;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return GenericDaoImplementation.getAll(this);
    }

    @Override
    public Couple getById(int id) {
        return GenericDaoImplementation.getById(this, id);
    }

    @Override
    public int save(Couple savedCouple) {
        return GenericDaoImplementation.save(this, savedCouple);
    }

    @Override
    public boolean update(Couple updatedCouple) {
        return GenericDaoImplementation.update(this, updatedCouple, updatedCouple.getId());
    }

    @Override
    public boolean delete(Couple deletedCouple) {
        return GenericDaoImplementation.delete(this, deletedCouple.getId());
    }

    @Override
    public boolean deleteById(int coupleId) {
        return GenericDaoImplementation.delete(this, coupleId);
    }

    @Override
    public Couple createFromResultSet(ResultSet resultSet){
        try {
            int id = resultSet.getInt("id");
            Date signup_date = resultSet.getDate("signup_date");
            int parent1_id = resultSet.getInt("parent1_id");
            int parent2_id = resultSet.getInt("parent2_id");
            return new Couple(id, signup_date, parent1_id, parent2_id);
        } catch (SQLException exception){
            exception.printStackTrace();
            throw new FailedToReadFromResultSetException();
        }
    }

    public void fillPreparedStatement(PreparedStatement preparedStatement, Couple couple){
        try {
            preparedStatement.setInt(1, couple.getParent1_id());
            preparedStatement.setInt(2, couple.getParent2_id());
            preparedStatement.setDate(3, couple.getSignupDate());
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
}

