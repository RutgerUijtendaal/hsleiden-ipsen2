package daos;

import exceptions.FailedToFillPreparedStatementException;
import exceptions.FailedToReadFromResultSetException;
import models.Parent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return GenericDaoImplementation.getAll(this);
    }

    @Override
    public Parent getById(int id) {
        return GenericDaoImplementation.getById(this, id);
    }

    @Override
    public int save(Parent savedParent) {
        return GenericDaoImplementation.save(this, savedParent);
    }

    @Override
    public boolean update(Parent updatedParent) {
        return GenericDaoImplementation.update(this, updatedParent, updatedParent.getId());
    }

    @Override
    public boolean delete(Parent deletedParent) {
        return GenericDaoImplementation.delete(this, deletedParent.getId());
    }

    public Parent getByEmail(String email) {
        return GenericDaoImplementation.getByColumn(this, columnNames[1], email);
    }

    @Override
    public boolean deleteById(int coupleId) {
        return GenericDaoImplementation.delete(this, coupleId);
    }

    /**
     * Check if the email already exists in the database.
     *
     * @param email email to check.
     * @return true if email exists, false otherwise.
     */
    public boolean emailExists(String email) {
        return getByEmail(email) == null;
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
            throw new FailedToReadFromResultSetException();
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

