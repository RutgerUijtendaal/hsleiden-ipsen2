package daos;

import models.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDao implements GenericDao<Admin>{

    @Override
    public List<Admin> getAll(){
        List<Admin> admins = new ArrayList<>();

        String statement = "SELECT * FROM admin";

        PreparedStatement preparedStatement = DaoManager.getPreparedStatement(statement);

        try {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    admins.add(createAdminFromResultSet(resultSet));
                }
                resultSet.close();
        } catch (SQLException exception){
            exception.printStackTrace();
        }

        DaoManager.closeTransaction(preparedStatement);

        return admins;
    }

    @Override
    public Admin getByPK(int pk) {
        return null;
    }

    @Override
    public void save(Admin newAdmin) {

    }

    @Override
    public void update(Admin updatedAdmin, String[] params) {

    }

    @Override
    public void delete(Admin deletedAdmin) {

    }

    private Admin createAdminFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        int rights_id = resultSet.getInt("rights_id");
        Date signup_date = resultSet.getDate("signup_date");
        return new Admin(id, email, password, rights_id, signup_date);
    }
}

