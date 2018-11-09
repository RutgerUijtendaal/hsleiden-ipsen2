package daos;

import exceptions.FillPreparedStatementException;
import exceptions.ReadFromResultSetException;
import models.Right;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Bas de Bruyn
 */
public class RightDao extends GenericDao<Right> {
    private final String tableName = "rights";
    private final String[] columnNames= {
            "can_edit_dilemma",
            "can_view_statistics",
            "can_edit_user_info"
    };

    @Override
    public Right createFromResultSet(ResultSet resultSet){
        try {
            int id = resultSet.getInt("id");
            boolean can_edit_dilemma = resultSet.getBoolean(columnNames[0]);
            boolean can_view_statistics = resultSet.getBoolean(columnNames[1]);
            boolean can_edit_user_info = resultSet.getBoolean(columnNames[2]);
            return new Right(id, can_edit_dilemma, can_view_statistics, can_edit_user_info);
        } catch (SQLException exception){
            throw new ReadFromResultSetException();
        }
    }

    @Override
    public void fillPreparedStatement(PreparedStatement preparedStatement, Right right){
        try {
            preparedStatement.setBoolean(1, right.isCanEditDilemma());
            preparedStatement.setBoolean(2, right.isCanViewStatistics());
            preparedStatement.setBoolean(3, right.isCanEditUserInfo());
        } catch (SQLException exception){
            throw new FillPreparedStatementException();
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

    @Override
    public GenericDao<Right> getDao() {
        return this;
    }
}
