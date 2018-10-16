package daos;

import models.Right;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RightDao implements GenericDao<Right> {
    private final String tableName = "rights";
    private final String[] columnNames= {
            "can_edit_dilemma",
            "can_view_statistics"
    };

    @Override
    public List<Right> getAll() {
        List<Right> rights = new ArrayList<>();

        PreparedStatement preparedStatement = DaoManager.getSelectAllStatement(tableName);

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                rights.add(createRightFromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(preparedStatement);

        return rights;
    }

    @Override
    public Right getById(int id) {
        Right right = null;

        PreparedStatement statement = DaoManager.getSelectByIdStatement(tableName, id);

        try {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            right = createRightFromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);

        return right;
    }

    @Override
    public int save(Right savedRight) {
        int generatedKey = -1;

        PreparedStatement statement = DaoManager.getInsertStatement(tableName, columnNames);

        try{
            fillPreparedStatement(statement, savedRight);
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            generatedKey = resultSet.getInt(1);
            resultSet.close();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);

        return generatedKey;
    }

    @Override
    public void update(Right updatedRight) {
        PreparedStatement statement = DaoManager.getUpdateStatement(columnNames, tableName, updatedRight.getId());

        try{
            fillPreparedStatement(statement, updatedRight);
            statement.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);
    }

    @Override
    public void delete(Right deletedRight) {
        PreparedStatement statement = DaoManager.getDeleteStatement(tableName, deletedRight.getId());

        try{
            statement.execute();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(statement);
    }

    private Right createRightFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        boolean can_edit_dilemma = resultSet.getBoolean(columnNames[0]);
        boolean can_view_statistics = resultSet.getBoolean(columnNames[1]);

        return new Right(id,can_edit_dilemma,can_view_statistics);
    }

    private void fillPreparedStatement(PreparedStatement preparedStatement, Right right) throws SQLException {
        preparedStatement.setBoolean(1, right.getCan_edit_dilemma());
        preparedStatement.setBoolean(2, right.getCan_view_statistics());
    }
}
