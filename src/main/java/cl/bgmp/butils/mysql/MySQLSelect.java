package cl.bgmp.butils.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLSelect extends MySQLStatement {
  private String table;
  private Column columnGet;
  private Column columnWhere;
  private String value;
  private String result;

  public MySQLSelect(MySQLConnector connector) {
    super(connector);
  }

  public MySQLSelect atTable(String table) {
    this.table = table;
    return this;
  }

  public MySQLSelect getting(Column columnGet) {
    this.columnGet = columnGet;
    return this;
  }

  public MySQLSelect where(Column columnWhere) {
    this.columnWhere = columnWhere;
    return this;
  }

  public MySQLSelect is(String value) {
    this.value = value;
    return this;
  }

  public String asString() {
    execute();
    return result;
  }

  public boolean asBoolean() {
    execute();
    return Boolean.parseBoolean(result);
  }

  public int asInteger() {
    execute();
    return Integer.parseInt(result);
  }

  private void execute() {
    try {
      PreparedStatement preparedStatement =
          connector
              .getConnection()
              .prepareStatement(
                  "SELECT * FROM " + table + " WHERE " + columnWhere.getName() + " = ?");

      switch (columnGet.getType()) {
        case STRING:
          preparedStatement.setString(1, value);
          break;
        case INTEGER:
          preparedStatement.setInt(1, Integer.parseInt(value));
          break;
        case BOOLEAN:
          preparedStatement.setBoolean(1, Boolean.parseBoolean(value));
          break;
      }

      ResultSet results = preparedStatement.executeQuery();
      if (results.next()) result = results.getString(columnGet.getName());

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
