package cl.bgmp.butils.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {
  private final Class<?> synchronizedClass;
  private Connection connection;
  private String host;
  private String database;
  private String username;
  private String password;
  private int port;
  private boolean ssl;

  public MySQLConnector(
      Class<?> synchronizedClass,
      String host,
      String database,
      String username,
      String password,
      int port,
      boolean ssl) {
    this.synchronizedClass = synchronizedClass;
    this.host = host;
    this.database = database;
    this.username = username;
    this.password = password;
    this.port = port;
    this.ssl = ssl;
  }

  public Connection getConnection() {
    return connection;
  }

  public boolean connect() {
    synchronized (synchronizedClass) {
      try {
        if (connection != null && !connection.isClosed()) return false;

        Class.forName("com.mysql.jdbc.Driver");
        final String url =
            "jdbc:mysql://"
                + host
                + ":"
                + port
                + "/"
                + database
                + "?autoReconnect=true&useSSL="
                + ssl;

        connection = DriverManager.getConnection(url, username, password);
        return true;
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    }
    return false;
  }
}
