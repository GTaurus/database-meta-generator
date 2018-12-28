package io.gtaurus.db.file.models;

import java.util.List;

public class SheetModel {

  private String sheetName;
  private String tableName;
  private String tableNameComment;
  private int columnCount;
  private List<RowModel> rows;

  public String getSheetName() {
    return sheetName;
  }

  public void setSheetName(String sheetName) {
    this.sheetName = sheetName;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getTableNameComment() {
    return tableNameComment;
  }

  public void setTableNameComment(String tableNameComment) {
    this.tableNameComment = tableNameComment;
  }

  public int getColumnCount() {
    return columnCount;
  }

  public void setColumnCount(int columnCount) {
    this.columnCount = columnCount;
  }

  public List<RowModel> getRows() {
    return rows;
  }

  public void setRows(List<RowModel> rows) {
    this.rows = rows;
  }
}
