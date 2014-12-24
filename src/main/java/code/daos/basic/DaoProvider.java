package code.daos.basic;

import code.daos.*;

public class DaoProvider {

    private static WindowDAO windowDAO = new WindowDAO();
    private static ConnectionDAO connectionDAO = new ConnectionDAO();
    private static LoggedUserDAO loggedUserDAO = new LoggedUserDAO();
    private static EngineDAO engineDAO = new EngineDAO();
    private static MapDAO mapDAO = new MapDAO();

    public static WindowDAO getWindowDAO() {
        return windowDAO;
    }

    public static void setWindowDAO(WindowDAO windowDAO) {
        DaoProvider.windowDAO = windowDAO;
    }

    public static ConnectionDAO getConnectionDAO() {
        return connectionDAO;
    }

    public static void setConnectionDAO(ConnectionDAO connectionDAO) {
        DaoProvider.connectionDAO = connectionDAO;
    }

    public static LoggedUserDAO getLoggedUserDAO() {
        return loggedUserDAO;
    }

    public static void setLoggedUserDAO(LoggedUserDAO loggedUserDAO) {
        DaoProvider.loggedUserDAO = loggedUserDAO;
    }

    public static EngineDAO getEngineDAO() {
        return engineDAO;
    }

    public static void setEngineDAO(EngineDAO engineDAO) {
        DaoProvider.engineDAO = engineDAO;
    }

    public static MapDAO getMapDAO() {
        return mapDAO;
    }

    public static void setMapDAO(MapDAO mapDAO) {
        DaoProvider.mapDAO = mapDAO;
    }
}
