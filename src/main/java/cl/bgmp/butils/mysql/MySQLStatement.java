package cl.bgmp.butils.mysql;

import java.sql.SQLException;

public abstract class MySQLStatement {
  protected MySQLConnector connector;

  public MySQLStatement(MySQLConnector connector) {
    this.connector = connector;

    try {
      if (!connector.getConnection().isValid(0)) {
        connector.connect();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
