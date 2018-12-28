package io.gtaurus.db.meta.models;

import java.util.List;

public class TableIndexSummaryModel {

  private String indexName;
  private int columnCount;
  private boolean unique;
  private List<TableIndexDetailModel> details;

  public String getIndexName() {
    return indexName;
  }

  public void setIndexName(String indexName) {
    this.indexName = indexName;
  }

  public int getColumnCount() {
    return columnCount;
  }

  public void setColumnCount(int columnCount) {
    this.columnCount = columnCount;
  }

  public boolean isUnique() {
    return unique;
  }

  public void setUnique(boolean unique) {
    this.unique = unique;
  }

  public List<TableIndexDetailModel> getDetails() {
    return details;
  }

  public void setDetails(List<TableIndexDetailModel> details) {
    this.details = details;
  }
}
