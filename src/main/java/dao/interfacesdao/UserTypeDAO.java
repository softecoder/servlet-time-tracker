package dao.interfacesdao;

import entities.UserType;
import exceptions.DAOException;

import java.sql.Connection;

public interface UserTypeDAO extends AbstractDAO<UserType> {


    void deleteById(int id, Connection connection) throws DAOException;

    UserType getById(int id, Connection connection) throws DAOException;

    UserType getByType(String type, Connection connection) throws DAOException;

    void update(UserType userType, Connection connection) throws DAOException;
}
