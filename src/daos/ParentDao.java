package daos;

import models.Admin;
import models.Parent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParentDao implements GenericDao<Parent>{
    @Override
    public List<Parent> getAll() {
        List<Parent> parents = new ArrayList<>();

        String statement = "SELECT * FROM parent";

        PreparedStatement preparedStatement = DaoManager.getPreparedStatement(statement);

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                parents.add(createParentFromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransactio(preparedStatement);

        return parents;
    }

    @Override
    public Parent getByPK(int pk) {
        return null;
    }

    @Override
    public void save(Parent savedParent) {

    }

    @Override
    public void update(Parent updatedParent, String[] params) {

    }

    @Override
    public void delete(Parent deletedParent) {

    }

    private Parent createParentFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String first_name = resultSet.getString("first_name");
        String email = resultSet.getString("email");
        String phone_number = resultSet.getString("phone_nr");
        return new Parent(id,phone_number,first_name,email);
    }
}

