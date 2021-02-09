package dao.interfacesdao;

import entities.User;
import exceptions.DAOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserDAO extends AbstractDAO<User> {

    boolean isAuthorized(String login, String password, Connection connection) throws DAOException;

    User getByLogin(String login, Connection connection) throws DAOException;

    void deleteById(int id, Connection connection) throws DAOException;

    boolean checkUniqueUser(String login, Connection connection) throws DAOException;

    User getById(String id, Connection connection) throws DAOException;

    void update(User user, Connection connection) throws DAOException;

    User createUser(ResultSet resultSet, User user) throws SQLException;
}

