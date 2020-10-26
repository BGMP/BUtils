package cl.bgmp.butils.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class MySQLUpdate extends MySQLStatement {
  private String table;
  private Column[] columnsToUpdate;
  private String[] values;
  private Column columnWhere;
  private String valueWhere;

  public MySQLUpdate(MySQLConnector connector) {
    super(connector);
  }

  public MySQLUpdate atTable(String table) {
    this.table = table;
    return this;
  }

  public MySQLUpdate updating(Column... columnsToUpdate) {
    this.columnsToUpdate = columnsToUpdate;
    return this;
  }

  public MySQLUpdate respectivelyWith(String... values) {
    this.values = values;
    return this;
  }

  public MySQLUpdate where(Column columnWhere) {
    this.columnWhere = columnWhere;
    return this;
  }

  public MySQLUpdate is(String valueWhere) {
    this.valueWhere = valueWhere;
    return this;
  }

  public void execute() {
    try {
      PreparedStatement preparedStatement =
          connector.getConnection().prepareStatement(buildInitialStatement());
      PreparedStatement preparedStatementWithValues =
          Util.applyStatementSets(
              preparedStatement, getColumnValueRelations(), columnWhere, valueWhere);

      preparedStatementWithValues.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private String buildInitialStatement() {
    StringBuilder statementStringBuilder = new StringBuilder();
    statementStringBuilder.append("UPDATE ").append(table).append(" SET ");

    int i = 1;
    for (Column column : columnsToUpdate) {
      statementStringBuilder
          .append(column.getName())
          .append(" = ?")
          .append(values.length == i ? " " : ", ");
      i++;
    }

    statementStringBuilder.append("WHERE ").append(columnWhere.getName()).append(" = ?");
    return statementStringBuilder.toString();
  }

  private LinkedHashMap<Column, String> getColumnValueRelations() {
    LinkedHashMap<Column, String> columnValueRelations = new LinkedHashMap<>();

    int i = 0;
    for (Column column : columnsToUpdate) {
      columnValueRelations.put(column, values[i]);
      i++;
    }

    return columnValueRelations;
  }
}
