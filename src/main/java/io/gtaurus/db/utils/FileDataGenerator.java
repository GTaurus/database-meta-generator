package io.gtaurus.db.utils;

import io.gtaurus.db.file.excel.poi.PoiFileWriter;
import io.gtaurus.db.file.models.CellModel;
import io.gtaurus.db.file.models.RowModel;
import io.gtaurus.db.file.models.SheetModel;
import io.gtaurus.db.meta.models.TableMetaSummaryModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileDataGenerator {

  private static final int INFO_COLSPAN = 5;

  private static final String FOREGROUND_COLOR_COLUMN_HEACER = "SKY_BLUE";
  private static final String FOREGROUND_COLOR_SUMMARY_INFO = "YELLOW";

  public static byte[] genFileData(List<TableMetaSummaryModel> tables) throws Exception {
    return PoiFileWriter.genFileData(genSheets(tables));
  }

  public static List<SheetModel> genSheets(List<TableMetaSummaryModel> tables) {
    List<SheetModel> sheets = new ArrayList<>();
    tables.forEach(
        t -> {
          SheetModel sheet = new SheetModel();
          List<RowModel> rows = new ArrayList<>();
          rows.addAll(genTableSummarySection(t));
          rows.add(RowModel.blankRow());
          rows.add(genBackRow("返回", "INDEX"));
          sheet.setColumnCount(Integer.valueOf(t.getColumnCount()));
          sheet.setRows(rows);
          sheet.setTableNameComment(t.getTableNameComment());
          sheet.setTableName(t.getTableName());
          sheet.setSheetName(t.getTableName());
          sheets.add(sheet);
        });
    sheets.add(0, genIndex(sheets));
    return sheets;
  }

  public static SheetModel genIndex(List<SheetModel> sheets) {
    SheetModel sheet = new SheetModel();
    List<RowModel> rows = new ArrayList<>();
    // 2 blank rows
    rows.add(RowModel.blankRow());
    rows.add(RowModel.blankRow());
    rows.add(
        genStyleRow(
            FOREGROUND_COLOR_COLUMN_HEACER,
            "",
            genNormalCell("表名"),
            genNormalCell("表字段"),
            genNormalCell("备注")));
    sheets.forEach(
        s ->
            rows.add(
                genNormalRow(
                    genIndexCell(s.getSheetName()),
                    genNormalCell(String.valueOf(s.getColumnCount())),
                    genNormalCell(s.getTableNameComment()))));

    sheet.setSheetName("INDEX");
    sheet.setRows(rows);
    return sheet;
  }

  public static List<RowModel> genTableSummarySection(TableMetaSummaryModel t) {
    List<RowModel> rows = new ArrayList<>();
    // tableName
    rows.add(
        genStyleRow(
            FOREGROUND_COLOR_SUMMARY_INFO,
            "",
            genNormalCell("表名"),
            genNormalCell(t.getTableName(), INFO_COLSPAN)));
    // tableComment
    rows.add(
        genStyleRow(
            FOREGROUND_COLOR_SUMMARY_INFO,
            "",
            genNormalCell("表中文名"),
            genNormalCell(t.getTableNameComment(), INFO_COLSPAN)));
    // columnCount
    rows.add(
        genStyleRow(
            FOREGROUND_COLOR_SUMMARY_INFO,
            "",
            genNormalCell("字段数量"),
            genNormalCell(t.getColumnCount(), INFO_COLSPAN)));
    // detail_header
    rows.add(
        genStyleRow(
            FOREGROUND_COLOR_COLUMN_HEACER,
            "",
            genNormalCell("序号"),
            genNormalCell("字段名"),
            genNormalCell("类型"),
            genNormalCell("长度"),
            genNormalCell("是否可空"),
            genNormalCell("默认值"),
            genNormalCell("备注")));
    // detail
    t.getDetails()
        .forEach(
            d ->
                rows.add(
                    genNormalRow(
                        d.getOrdinalPosition().toString(),
                        d.getColumnName(),
                        d.getColumnType(),
                        d.getColumnSize(),
                        d.getNullable(),
                        d.getColumnDef(),
                        d.getColumnNameComment())));
    // unique index
    t.getUindexes()
        .forEach(
            (n, i) -> {
              rows.add(
                  genStyleRow(
                      FOREGROUND_COLOR_SUMMARY_INFO,
                      "",
                      genNormalCell("唯一索引"),
                      genNormalCell(n, INFO_COLSPAN)));
              rows.add(
                  genStyleRow(
                      FOREGROUND_COLOR_SUMMARY_INFO,
                      "",
                      genNormalCell("字段数量"),
                      genNormalCell(String.valueOf(i.getColumnCount()), INFO_COLSPAN)));
              rows.add(
                  genStyleRow(
                      FOREGROUND_COLOR_SUMMARY_INFO,
                      "",
                      genNormalCell("序号"),
                      genNormalCell("字段名"),
                      genNormalCell("排序"),
                      CellModel.blankCellWithBorder(),
                      CellModel.blankCellWithBorder(),
                      CellModel.blankCellWithBorder(),
                      CellModel.blankCellWithBorder()));
              i.getDetails()
                  .forEach(
                      c ->
                          rows.add(
                              genNormalRow(
                                  genNormalCell(c.getOrdinalPosition()),
                                  genNormalCell(c.getColumnName()),
                                  genNormalCell(c.getAscOrDesc()),
                                  CellModel.blankCellWithBorder(),
                                  CellModel.blankCellWithBorder(),
                                  CellModel.blankCellWithBorder(),
                                  CellModel.blankCellWithBorder())));
            });
    // non-unique index
    t.getIndexes()
        .forEach(
            (n, i) -> {
              rows.add(
                  genStyleRow(
                      FOREGROUND_COLOR_SUMMARY_INFO,
                      "",
                      genNormalCell("非唯一索引"),
                      genNormalCell(n, INFO_COLSPAN)));
              rows.add(
                  genStyleRow(
                      FOREGROUND_COLOR_SUMMARY_INFO,
                      "",
                      genNormalCell("字段数量"),
                      genNormalCell(String.valueOf(i.getColumnCount()), INFO_COLSPAN)));
              rows.add(
                  genStyleRow(
                      FOREGROUND_COLOR_COLUMN_HEACER,
                      "",
                      genNormalCell("序号"),
                      genNormalCell("字段名"),
                      genNormalCell("排序"),
                      CellModel.blankCellWithBorder(),
                      CellModel.blankCellWithBorder(),
                      CellModel.blankCellWithBorder(),
                      CellModel.blankCellWithBorder()));
              i.getDetails()
                  .forEach(
                      c ->
                          rows.add(
                              genNormalRow(
                                  genNormalCell(c.getOrdinalPosition()),
                                  genNormalCell(c.getColumnName()),
                                  genNormalCell(c.getAscOrDesc()),
                                  CellModel.blankCellWithBorder(),
                                  CellModel.blankCellWithBorder(),
                                  CellModel.blankCellWithBorder(),
                                  CellModel.blankCellWithBorder())));
            });
    return rows;
  }

  private static RowModel genNormalRow(String... cellValues) {
    if (cellValues != null && cellValues.length > 0) {
      CellModel[] cells = new CellModel[cellValues.length];
      for (int i = 0; i < cellValues.length; i++) {
        cells[i] = new CellModel(cellValues[i], true);
      }
      return genNormalRow(cells);
    }
    return RowModel.blankRow();
  }

  private static RowModel genNormalRow(CellModel... cells) {
    if (cells != null && cells.length > 0) {
      List<CellModel> list = Arrays.asList(cells);
      return new RowModel(list);
    }
    return RowModel.blankRow();
  }

  private static RowModel genStyleRow(
      String foreGroundColor, String fontColor, CellModel... cells) {
    if (cells != null && cells.length > 0) {
      List<CellModel> list = Arrays.asList(cells);
      list.forEach(
          c -> {
            c.setFontColor(fontColor);
            c.setForeGroundColor(foreGroundColor);
          });
      return new RowModel(list);
    }
    return RowModel.blankRow();
  }

  private static CellModel genIndexCell(String sheetName) {
    return new CellModel(sheetName, sheetName, true);
  }

  private static CellModel genNormalCell(String cellValue) {
    return new CellModel(cellValue, true);
  }

  private static CellModel genNormalCell(String cellValue, int colspan) {
    return new CellModel(cellValue, colspan, true);
  }

  private static RowModel genBackRow(String value, String location) {
    List<CellModel> cells = new ArrayList<>();
    cells.add(CellModel.blankCell());
    cells.add(new CellModel(value, location, false));
    return new RowModel(cells);
  }
}
