package db;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBExecutor {
    public static final ConnectionFactory FACTORY = new ConnectionFactory();

    public static int executeInsert(String sql) {
        Connection connection;
        connection = FACTORY.getConnection();
        int result = 0;
        try {
            result = connection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        FACTORY.closeConnection(connection);
        return result;
    }

    public static ResultSet executeSelect(String sql) throws SQLException {
        Connection connection;
        connection = FACTORY.getConnection();
        ResultSet result = null;

            result = connection.createStatement().executeQuery(sql);

        FACTORY.closeConnection(connection);
        return result;
    }

}
