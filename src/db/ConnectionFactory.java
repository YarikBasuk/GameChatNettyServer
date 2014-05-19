package db;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {




    public Connection getConnection() {
        Connection connection = null;
        try {
            String host = DBConfig.DB_HOST;
            String name = DBConfig.BASE_NAME;
            String user = DBConfig.DB_USER;
            String password = DBConfig.DB_USER_PASSWORD;

            connection = (Connection) DriverManager.getConnection(host + "/"+ name, user, password);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }


    public void closeConnection(Connection connection) {
      /*  try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }     */
    }


}