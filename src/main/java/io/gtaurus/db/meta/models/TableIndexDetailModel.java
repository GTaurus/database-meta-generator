package io.gtaurus.db.meta.models;

public class TableIndexDetailModel {

  private String nonUnique;
  private String tableName;
  private String indexName;
  private String columnName;
  private String ascOrDesc;
  private String ordinalPosition;

  public String getNonUnique() {
    return nonUnique;
  }

  public void setNonUnique(String nonUnique) {
    this.nonUnique = nonUnique;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getIndexName() {
    return indexName;
  }

  public void setIndexName(String indexName) {
    this.indexName = indexName;
  }

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public String getAscOrDesc() {
    return ascOrDesc;
  }

  public void setAscOrDesc(String ascOrDesc) {
    this.ascOrDesc = ascOrDesc;
  }

  public String getOrdinalPosition() {
    return ordinalPosition;
  }

  public void setOrdinalPosition(String ordinalPosition) {
    this.ordinalPosition = ordinalPosition;
  }
}
