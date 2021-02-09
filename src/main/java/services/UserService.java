package services;

import connection.ConnectionPool;
import connection.TransactionHandler;
import constants.Parameters;
import dao.interfacesdao.UserDAO;
import entities.Activity;
import entities.Tracking;
import entities.User;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private volatile static UserService instance;
    private UserDAO userDao;
    private ConnectionPool connectionPool;

    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public static UserService getInstance() {
        if (instance == null) {
            synchronized (UserService.class) {
                if (instance == null) {
                    return instance = new UserService();
                }
            }
        }
        return instance;
    }

    public boolean checkUserAuthorization(String login, String password) throws SQLException {
        final boolean[] isAuthorized = new boolean[1];
        TransactionHandler.runInTransaction(connection ->
                        isAuthorized[0] = userDao.isAuthorized(login, password, connection),
                connectionPool.getConnection()
        );
        return isAuthorized[0];
    }

    public User getUserByLogin(String login) throws SQLException {
        final User[] user = new User[1];
        TransactionHandler.runInTransaction(connection ->
                user[0] = userDao.getByLogin(login, connection), connectionPool.getConnection()
        );
        return user[0];
    }

    public User getUserById(String overviewUserId) throws SQLException {
        final User[] user = new User[1];
        TransactionHandler.runInTransaction(connection ->
                user[0] = userDao.getById(overviewUserId, connection), connectionPool.getConnection()
        );
        return user[0];
    }

    public void updateUser(User user) throws SQLException {
        TransactionHandler.runInTransaction(connection ->
                userDao.update(user, connection), connectionPool.getConnection()
        );
    }

    public boolean isUniqueUser(User user) throws SQLException {
        final boolean[] isUnique = new boolean[1];
        TransactionHandler.runInTransaction(connection ->
                        isUnique[0] = userDao.checkUniqueUser(user.getLogin(), connection),
                connectionPool.getConnection()
        );
        return isUnique[0];
    }

    public void registerUser(User user) throws SQLException {
        TransactionHandler.runInTransaction(connection ->
                userDao.add(user, connection), connectionPool.getConnection()
        );
    }

    public List<User> getAllUser() throws SQLException {
        final List<User>[] userList = new List[]{new ArrayList<>()};
        TransactionHandler.runInTransaction(connection ->
                userList[0] = userDao.getAll(connection), connectionPool.getConnection()
        );
        return userList[0];
    }
// pagination
    public List<String> getNumbersPages(List<User> userList, int itemsPerPage) {
        List<String> pagesList = new ArrayList<>();
        int fullPage = (userList.size() - 1) / itemsPerPage;
        int partPage = (userList.size() - 1) % itemsPerPage == 0 ? 0 : 1;
        int numbersPages = fullPage + partPage;
        for (int i = 1; i <= numbersPages; i++) {
            pagesList.add(String.valueOf(i));
        }
        return pagesList;
    }

    public void setAttributeAdminToSession(User adminUser, HttpSession session) {
        session.setAttribute(Parameters.ADMIN_USER, adminUser);
    }

    public void setAttributeOverviewUserToSession(User overviewUser, HttpSession session) {
        session.setAttribute(Parameters.OVERVIEWUSER, overviewUser);
    }

    public void setAttributeClientToSession(User clientUser, HttpSession session) {
        session.setAttribute(Parameters.CLIENTUSER, clientUser);
    }

    public void setAttributeToSession(List<Activity> activityAdminList, List<Tracking> trackingList,
                                      List<User> userList, HttpSession session) {
        session.setAttribute(Parameters.ACTIVITY_ADMIN_LIST, activityAdminList);
        session.setAttribute(Parameters.TRACKING_LIST, trackingList);
        session.setAttribute(Parameters.USER_LIST, userList);
    }

    public void setAttributeToSession(List<Tracking> trackingList,
                                      List<User> userList, HttpSession session) {
        session.setAttribute(Parameters.TRACKING_LIST, trackingList);
        session.setAttribute(Parameters.USER_LIST, userList);
    }

    public void setPaginationAttributeToSession(List<String> numbersPages, String lastPage, String currentPage,
                                                int itemsPerPage, HttpSession session) {
        session.setAttribute(Parameters.NUMBERSPAGES, numbersPages);
        session.setAttribute(Parameters.LASTPAGE, lastPage);
        session.setAttribute(Parameters.CURRENTPAGE, currentPage);
        session.setAttribute(Parameters.ITEMSPERPAGE, itemsPerPage);
    }
}