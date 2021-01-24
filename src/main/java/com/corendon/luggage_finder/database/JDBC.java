/*
 * Fasten Your Seatbelt
 * Corendon
 *
 * 2017 (c) IS108 Groep 4 - Tom J. Wassing, Vince de Leeuw, Dylan Tweebeeke, Yessin el Khaldi, Fethi K. Tewelde, Petar Dimitrov
 */
package com.corendon.luggage_finder.database;
import java.sql.*;

/**
 * This class holds the active connection to the database and abstracts the
 * access to perform queries on it. The class implements the Singleton patteren
 * to avoid having multiple connection to the database.
 *
 * @author Petar
 * @author Tom J. Wassing
 */
public final class JDBC {

    private static JDBC instance = null;

    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_DATABASE = "corendon";
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 3306;

    private static final String DRIVER_PREFIX = "jdbc:mysql://";

    private Connection connection;

    /**
     * Private constructor to prevent instantiation
     */
    private JDBC() {
    }

    /**
     * Returns the JDBC instance;
     *
     * @return JDBC instance
     */
    public static JDBC getInstance() {
        if (instance == null) {
            instance = new JDBC();
        }

        return instance;
    }

    /**
     * Connect to the default database. Using default username and password.
     * Disables allowMultiQueries.
     *
     * @return If successfully connected.
     */
    public boolean connect() {
        return connect(DEFAULT_USERNAME, "admin", true);
    }

    /**
     * Connect to the default database. Disables allowMultiQueries.
     *
     * @param username Database username.
     * @param password Database password.
     * @return Whether connection succeeded.
     */
    public boolean connect(String username, String password) {
        return connect(username, password, false);
    }

    /**
     * Connect to the default database. Optionally enable allowMultiQueries.
     *
     * @param username Database username.
     * @param password Database password.
     * @param allowMultiQueries Allow multi queries?
     * @return Whether connection succeeded.
     */
    public boolean connect(String username, String password, boolean allowMultiQueries) {
        return connect(username, password, DEFAULT_HOST, DEFAULT_PORT, DEFAULT_DATABASE, allowMultiQueries, false);
    }

    /**
     * Connect to a MySQL database.
     *
     * @param username Database username.
     * @param password Database password.
     * @param host Database server.
     * @param port Database port.
     * @param databaseName Database name.
     * @param allowMultiQueries Allow MultiQueries?
     * @return Whether connection succeeded.
     */
    public boolean connect(String username, String password,
            String host, int port, String databaseName, boolean allowMultiQueries, boolean debug) {
        try {
            String connectionUrl = String.format("%s%s:%d/%s?allowPublicKeyRetrieval=true&serverTimezone=UTC&useSSL=false&allowMultiQueries=%b&&profileSQL=%b",
                    DRIVER_PREFIX, host, port, databaseName, allowMultiQueries, debug);

            connection = DriverManager.getConnection(connectionUrl, username, password);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * Closes the database connection.
     */
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Executes a update (INSERT, UPDATE, DELETE) query in the database and
     * returns if the update row count is greater than 0.
     *
     * @param query the SQL query
     * @param params the params
     * @return if the query succeeded
     */
    public boolean executeUpdateQuery(String query, Object... params) throws SQLException {
        PreparedStatement stmt = null;
        try {
            // creating sql query and setting parameters
            stmt = connection.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            return stmt.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();

            return false;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }

    }

    /**
     * Executes a SELECT query in the database and returns the
     * {@link ResultSet}.
     *
     * @param query the SQL query
     * @param params the parameters
     * @return the resultset
     */
    public ResultSet executeSelectQuery(String query, Object... params) throws SQLException {
        PreparedStatement stmt = null;
        try {
            // creating sql query and setting parameters
             stmt = connection.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            return stmt.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();

            return null;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

}
