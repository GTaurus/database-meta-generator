package io.gtaurus.db.meta.models;


public class TableMetaDetailModel {

  private String ordinalPosition;
  private String columnName;
  private String columnNameComment;
  private String columnType;
  private String columnDef;
  private String columnSize;
  private String autoIncrement;
  private String nullable;

  public Integer getOrdinalPosition() {
    return Integer.valueOf(ordinalPosition);
  }

  public void setOrdinalPosition(String ordinalPosition) {
    this.ordinalPosition = ordinalPosition;
  }

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public String getColumnNameComment() {
    return columnNameComment;
  }

  public void setColumnNameComment(String columnNameComment) {
    this.columnNameComment = columnNameComment;
  }

  public String getColumnType() {
    return columnType;
  }

  public void setColumnType(String columnType) {
    this.columnType = columnType;
  }

  public String getColumnDef() {
    return columnDef;
  }

  public void setColumnDef(String columnDef) {
    this.columnDef = columnDef;
  }

  public String getColumnSize() {
    return columnSize;
  }

  public void setColumnSize(String columnSize) {
    this.columnSize = columnSize;
  }

  public String getAutoIncrement() {
    return autoIncrement;
  }

  public void setAutoIncrement(String autoIncrement) {
    this.autoIncrement = autoIncrement;
  }

  public String getNullable() {
    return nullable;
  }

  public void setNullable(String nullable) {
    this.nullable = nullable;
  }
}
