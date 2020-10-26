package cl.bgmp.butils.mysql;

public class Column {
  private String name;
  private SQLDataType sqlDataType;

  public Column(String name, SQLDataType sqlDataType) {
    this.name = name;
    this.sqlDataType = sqlDataType;
  }

  public String getName() {
    return name;
  }

  public SQLDataType getType() {
    return sqlDataType;
  }
}
