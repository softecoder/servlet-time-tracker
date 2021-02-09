package dao.interfacesdao;

import entities.Tracking;
import entities.User;
import exceptions.DAOException;

import java.sql.Connection;
import java.util.List;

public interface TrackingDAO extends AbstractDAO<Tracking> {

    void deleteTrackingByUserId(int id, Connection connection) throws DAOException;

    void deleteTrackingById(Integer id, Connection connection) throws DAOException ;

    Tracking getTrackingById(String id, Connection connection) throws DAOException;

    void updateTrackingById(String id, Tracking tracking, Connection connection) throws DAOException;

    void updateTrackingStatusAndTimeByID(Tracking tracking, Connection connection) throws DAOException;

    void updateTrackingStatusByID(Tracking tracking, Connection connection) throws DAOException;

    List<Tracking> getTrackingByClientId(User user, Connection connection) throws DAOException;

    void setStatusAndStartTime(String id, String status, Long startTime, Connection connection) throws DAOException;

    void setStatusAndTime(String id, String status, String time, Connection connection) throws DAOException;
}
