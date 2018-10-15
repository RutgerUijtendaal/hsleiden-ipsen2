package daos;

import models.Parent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParentDao implements GenericDao<Parent>{

    private final String tableName = "parent";
    private final String[] columnNames= {
            "first_name",
            "email",
            "phone_nr"
    };

    @Override
    public List<Parent> getAll() {
        List<Parent> parents = new ArrayList<>();

        PreparedStatement preparedStatement = DaoManager.getSelectAllStatement(tableName);

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                parents.add(createParentFromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(preparedStatement);

        return parents;
    }

    @Override
    public Parent getById(int id) {
        Parent parent = null;

        PreparedStatement statement = DaoManager.getSelectByIdStatement(tableName, id);

        try {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            parent = createParentFromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);

        return parent;
    }

    @Override
    public void save(Parent savedParent) {
        PreparedStatement statement = DaoManager.getInsertStatement(tableName, columnNames);

        try{
            fillPreparedStatement(statement, savedParent);
            statement.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);
    }

    @Override
    public void update(Parent updatedParent) {
        PreparedStatement statement = DaoManager.getUpdateStatement(columnNames, tableName, updatedParent.getId());

        try{
            fillPreparedStatement(statement, updatedParent);
            statement.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);
    }

    @Override
    public void delete(Parent deletedParent) {
        PreparedStatement statement = DaoManager.getDeleteStatement(tableName, deletedParent.getId());

        try{
            statement.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);
    }

    private Parent createParentFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String first_name = resultSet.getString(columnNames[0]);
        String email = resultSet.getString(columnNames[1]);
        String phone_number = resultSet.getString(columnNames[2]);

        return new Parent(id,phone_number,first_name,email);
    }

    private void fillPreparedStatement(PreparedStatement preparedStatement, Parent parent) throws SQLException {
        preparedStatement.setString(1, parent.getFirstName());
        preparedStatement.setString(2, parent.getEmail());
        preparedStatement.setString(3, parent.getPhoneNr());
    }
}

