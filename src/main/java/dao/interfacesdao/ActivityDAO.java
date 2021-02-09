package dao.interfacesdao;

import entities.Activity;
import exceptions.DAOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ActivityDAO extends AbstractDAO<Activity> {

    void deleteById(int id, Connection connection) throws DAOException;

    Activity getByName(String name, Connection connection) throws DAOException;

    Activity getById(String id, Connection connection) throws DAOException;

    void update(Activity activity, Connection connection) throws DAOException;

    boolean checkUniqueActivity(String activityName, Connection connection) throws DAOException;

    Activity createActivity(ResultSet resultSet, Activity activity) throws SQLException;

    boolean checkUniqueActivityByUser(String id, String userId, Connection connection) throws DAOException;
}