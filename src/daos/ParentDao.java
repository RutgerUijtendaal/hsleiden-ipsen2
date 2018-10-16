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
        return DaoManager.getAll(this);
    }

    @Override
    public Parent getById(int id) {
        return DaoManager.getById(this, id);
    }

    @Override
    public int save(Parent savedParent) {
        return DaoManager.save(this, savedParent);
    }

    @Override
    public boolean update(Parent updatedParent) {
        return DaoManager.update(this, updatedParent, updatedParent.getId());
    }

    @Override
    public boolean delete(Parent deletedParent) {
        return DaoManager.delete(this, deletedParent.getId());
    }

    @Override
    public boolean deleteById(int coupleId) {
        return DaoManager.delete(this, coupleId);
    }

    @Override
    public Parent createFromResultSet(ResultSet resultSet){
        try {
            int id = resultSet.getInt("id");
            String first_name = resultSet.getString(columnNames[0]);
            String email = resultSet.getString(columnNames[1]);
            String phone_number = resultSet.getString(columnNames[2]);

            return new Parent(id, phone_number, first_name, email);
        } catch (SQLException exception){
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void fillPreparedStatement(PreparedStatement preparedStatement, Parent parent){
        try {
            preparedStatement.setString(1, parent.getFirstName());
            preparedStatement.setString(2, parent.getEmail());
            preparedStatement.setString(3, parent.getPhoneNr());
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

    public boolean checkIfEmailsExists(String parent1_email, String parent2_email) {
        boolean exists = false;

        String query = "SELECT (COUNT(" + columnNames[1] + ") >= 1)\n" +
                "FROM " + tableName + "\n" +
                "WHERE " + columnNames[1] + " = ?\n" +
                "OR " + columnNames[1] + " = ?;";

        PreparedStatement statement = PreparedStatementFactory.getPreparedStatement(query);

        try {
            statement.setString(1, parent1_email);
            statement.setString(2, parent2_email);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            exists = resultSet.getBoolean(1);
            resultSet.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);

        return exists;
    }
}

