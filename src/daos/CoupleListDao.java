package daos;

import exceptions.ReadFromResultSetException;
import models.CoupleListModel;
import models.Parent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bas de Bruyn
 */
public class CoupleListDao implements DatabaseViewDao<CoupleListModel> {

    private final String tableName = "couple_list_view";
    private final String[] columnNames= {
            "couple_id",
            "parent_id1",
            "name1",
            "email1",
            "phone_nr1",
            "parent_id2",
            "name2",
            "email2",
            "phone_nr2"
    };

    @Override
    public List<CoupleListModel> getAll(){
        List<CoupleListModel> coupleListModels = new ArrayList<>();

        PreparedStatement preparedStatement = PreparedStatementFactory.createSelectAllStatement(tableName);

        ResultSet resultSet = GenericDao.executeQuery(preparedStatement);

        try {
            while (resultSet.next()) {
                coupleListModels.add(createCoupleListModelFromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException exception){
            throw new ReadFromResultSetException();
        } finally {
            GenericDao.closeTransaction(preparedStatement);
        }

        return coupleListModels;
    }

    @Override
    public CoupleListModel getById(int couple_id) {
        CoupleListModel coupleListModel;

        String query = "SELECT * FROM " + tableName + " WHERE " + columnNames[0] + " = " + couple_id + ";";
        PreparedStatement statement = PreparedStatementFactory.createPreparedStatement(query);

        ResultSet resultSet = GenericDao.executeQuery(statement);

        try {
            resultSet.next();
            coupleListModel = createCoupleListModelFromResultSet(resultSet);
            resultSet.close();
        } catch (SQLException exception) {
            throw new ReadFromResultSetException();
        } finally {
            GenericDao.closeTransaction(statement);
        }

        return coupleListModel;
    }

    private CoupleListModel createCoupleListModelFromResultSet(ResultSet resultSet){
        try {
            int couple_id = resultSet.getInt(columnNames[0]);

            int parent_id1 = resultSet.getInt(columnNames[1]);
            String name1 = resultSet.getString(columnNames[2]);
            String email1 = resultSet.getString(columnNames[3]);
            String phone_nr1 = resultSet.getString(columnNames[4]);
            Parent parent1 = new Parent(parent_id1, phone_nr1, name1, email1);

            int parent_id2 = resultSet.getInt(columnNames[5]);
            String name2 = resultSet.getString(columnNames[6]);
            String email2 = resultSet.getString(columnNames[7]);
            String phone_nr2 = resultSet.getString(columnNames[8]);
            Parent parent2 = new Parent(parent_id2, phone_nr2, name2, email2);

            return new CoupleListModel(couple_id, parent1, parent2);
        } catch (SQLException excception){
            throw new ReadFromResultSetException();
        }
    }
}
