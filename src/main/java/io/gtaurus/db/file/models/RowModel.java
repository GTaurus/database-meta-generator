package io.gtaurus.db.file.models;

import java.util.List;

public class RowModel {

  private final static RowModel BLANK_ROW = new RowModel(true);

  private boolean blankRow;
  private List<CellModel> cells;

  public RowModel(List<CellModel> cells) {
    this.cells = cells;
  }

  private RowModel(boolean blankRow) {
    this.blankRow = blankRow;
  }

  public static RowModel blankRow() {
    return BLANK_ROW;
  }

  public boolean isBlankRow() {
    return blankRow;
  }

  public List<CellModel> getCells() {
    return cells;
  }

  public void setCells(List<CellModel> cells) {
    this.cells = cells;
  }
}
