package io.gtaurus.db.meta.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableMetaSummaryModel {

  private List<TableMetaDetailModel> details;
  private String tableName;
  private String tableNameComment;
  private String columnCount;
  private String[] uniqueIndex;
  private String[] normalIndex;
  private String desc;
  private Map<String, TableIndexSummaryModel> uindexes = new HashMap<>();
  private Map<String, TableIndexSummaryModel> indexes = new HashMap<>();

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

  public String getColumnCount() {
    return columnCount;
  }

  public void setColumnCount(String columnCount) {
    this.columnCount = columnCount;
  }

  public String[] getUniqueIndex() {
    return uniqueIndex;
  }

  public void setUniqueIndex(String[] uniqueIndex) {
    this.uniqueIndex = uniqueIndex;
  }

  public String[] getNormalIndex() {
    return normalIndex;
  }

  public void setNormalIndex(String[] normalIndex) {
    this.normalIndex = normalIndex;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public List<TableMetaDetailModel> getDetails() {
    return details;
  }

  public void setDetails(List<TableMetaDetailModel> details) {
    this.details = details;
    this.columnCount = String.valueOf(details.size());
  }

  public Map<String, TableIndexSummaryModel> getUindexes() {
    return uindexes;
  }

  public void setUindexes(Map<String, TableIndexSummaryModel> uindexes) {
    this.uindexes = uindexes;
  }

  public Map<String, TableIndexSummaryModel> getIndexes() {
    return indexes;
  }

  public void setIndexes(Map<String, TableIndexSummaryModel> indexes) {
    this.indexes = indexes;
  }
}
