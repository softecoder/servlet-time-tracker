package dao.interfacesdao;

import exceptions.DAOException;

import java.sql.Connection;
import java.util.List;

public interface AbstractDAO<T> {

    void add(T entity, Connection connection) throws DAOException;

    List<T> getAll(Connection connection) throws DAOException;
}
