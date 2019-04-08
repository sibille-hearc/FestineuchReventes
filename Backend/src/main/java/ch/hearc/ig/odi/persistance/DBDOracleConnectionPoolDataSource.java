package ch.hearc.ig.odi.persistance;

import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;

class DBDOracleConnectionPoolDataSource {

    private static final String DBDRIVER = "thin";
    private static final String DBSRVNAME = "db.ig.he-arc.ch";
    private static final String DBDATANAME = "ens2";
    private static final int DBPORT = 1521;
    private static final String DBUSER = "PROJET_DEV01";
    private static final String DBPWD = "PROJET_DEV01";

    private static final OracleConnectionPoolDataSource dataSource = getDataSource();

    private static OracleConnectionPoolDataSource getDataSource() {
        OracleConnectionPoolDataSource ds = null;
        try {
            ds = new OracleConnectionPoolDataSource();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ds.setDriverType(DBDRIVER);
        ds.setServerName(DBSRVNAME);
        ds.setDatabaseName(DBDATANAME);
        ds.setPortNumber(DBPORT);
        ds.setUser(DBUSER);
        ds.setPassword(DBPWD);
        return ds;
    }

    static Connection getConnection() throws SQLException {
        Connection cnn = dataSource.getConnection();
        cnn.setAutoCommit(false);
        return cnn;
    }
}
