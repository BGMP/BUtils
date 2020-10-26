package cl.bgmp.butils.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class Util {

  static PreparedStatement applyStatementSets(
      PreparedStatement statement,
      LinkedHashMap<Column, String> columnValueRelations,
      Column columnWhere,
      String valueWhere) {
    int i = 1;

    for (Column column : columnValueRelations.keySet()) {
      String value = columnValueRelations.get(column);
      setStatementValue(statement, i, column, value);

      i++;
    }

    setStatementValue(statement, i, columnWhere, valueWhere);

    return statement;
  }

  private static void setStatementValue(
      PreparedStatement statement, int index, Column column, String value) {
    try {
      switch (column.getType()) {
        case STRING:
          statement.setString(index, value);
          break;
        case INTEGER:
          statement.setInt(index, Integer.parseInt(value));
          break;
        case BOOLEAN:
          statement.setBoolean(index, Boolean.parseBoolean(value));
          break;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
