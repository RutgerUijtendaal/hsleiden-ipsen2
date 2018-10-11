package daos;

import models.Couple;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoupleDao implements GenericDao<Couple>{
    @Override
    public List<Couple> getAll() {
        List<Couple> couples = new ArrayList<>();

        String statement = "SELECT * FROM couple";

        PreparedStatement preparedStatement = DaoManager.getPreparedStatement(statement);

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
    public Couple getByPK(int pk) {
        return null;
    }

    @Override
    public void save(Couple savedCouple) {

    }

    @Override
    public void update(Couple updatedCouple, String[] params) {

    }

    @Override
    public void delete(Couple deletedCouple) {

    }
    
    private Couple createCoupleFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        Date signup_date = resultSet.getDate("signup_date");
        int parent1_id = resultSet.getInt("parent1_id");
        int parent2_id = resultSet.getInt("parent2_id");
        return new Couple(id,signup_date,parent1_id,parent2_id);
    }
}

