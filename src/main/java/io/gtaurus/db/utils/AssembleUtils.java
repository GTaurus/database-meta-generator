package io.gtaurus.db.utils;

import io.gtaurus.db.meta.models.TableIndexDetailModel;
import io.gtaurus.db.meta.models.TableIndexSummaryModel;
import io.gtaurus.db.meta.models.TableMetaDetailModel;
import io.gtaurus.db.meta.models.TableMetaSummaryModel;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class AssembleUtils {

  public static List<Map<String, Object>> parseResultSetToMapList(ResultSet rs) {
    //
    List<Map<String, Object>> result = new ArrayList<>();
    //
    if (null == rs) {
      return null;
    }
    //
    try {
      while (rs.next()) {
        //
        Map<String, Object> map = parseResultSetToMap(rs);
        //
        if (null != map) {
          result.add(map);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    //
    return result;
  }

  private static Map<String, Object> parseResultSetToMap(ResultSet rs) {
    //
    if (null == rs) {
      return null;
    }
    //
    Map<String, Object> map = new HashMap<>();
    //
    try {
      ResultSetMetaData meta = rs.getMetaData();
      //
      int colNum = meta.getColumnCount();
      //
      for (int i = 1; i <= colNum; i++) {
        // 列名
        String name = meta.getColumnLabel(i); // i+1
        Object value = rs.getString(i);
        // 加入属性
        map.put(name, value);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    //
    return map;
  }

  public static List<TableMetaSummaryModel> assembleSummary(
      DatabaseMetaData metaData, String schema) throws SQLException {
    return assembleSummary(metaData, schema, "%");
  }

  public static List<TableMetaSummaryModel> assembleSummary(
      DatabaseMetaData metaData, String schema, String table) throws SQLException {
    ResultSet rs = metaData.getTables(null, schema, table, new String[] {"TABLE"});
    List<TableMetaSummaryModel> list = new ArrayList<>();
    List<Map<String, Object>> tableList = parseResultSetToMapList(rs);
    tableList.forEach(m -> list.add(assembleSummaryTable(metaData, schema, m)));
    return list;
  }

  public static TableMetaSummaryModel assembleSummaryTable(
      DatabaseMetaData metaData, String schema, Map<String, Object> m) {
    TableMetaSummaryModel summaryModel = new TableMetaSummaryModel();
    summaryModel.setTableName((String) m.get("TABLE_NAME"));
    summaryModel.setTableNameComment((String) m.get("REMARKS"));
    System.out.println(summaryModel.getTableName() + " START");
    assembleSummaryIndex(metaData, schema, summaryModel);
    assembleSummaryColumn(metaData, schema, summaryModel);
    System.out.println(summaryModel.getTableName() + " END");
    return summaryModel;
  }

  public static void assembleSummaryIndex(
      DatabaseMetaData metaData, String schema, TableMetaSummaryModel summaryModel) {
    try {
      System.out.println(summaryModel.getTableName() + " INDEX START");
      ResultSet idxRs =
          metaData.getIndexInfo(null, schema, summaryModel.getTableName(), false, false);
      List<Map<String, Object>> indexList = parseResultSetToMapList(idxRs);
      indexList.forEach(System.out::println);
      Map<String, SortedSet<TableIndexDetailModel>> uimap = new HashMap<>();
      Map<String, SortedSet<TableIndexDetailModel>> imap = new HashMap<>();

      indexList
          .stream()
          .filter(l -> (Integer.valueOf((String) l.get("TYPE"))).intValue() > 0)
          .forEach(
              l -> {
                Map<String, SortedSet<TableIndexDetailModel>> theMap =
                    "0".equals(l.get("NON_UNIQUE")) ? uimap : imap;
                String indexName = (String) l.get("INDEX_NAME");

                SortedSet<TableIndexDetailModel> set;
                if (theMap.containsKey(indexName)) {
                  set = theMap.get(indexName);
                } else {
                  set =
                      new TreeSet<>(
                          Comparator.comparing(TableIndexDetailModel::getOrdinalPosition));
                  theMap.put(indexName, set);
                }
                TableIndexDetailModel indexDetail = new TableIndexDetailModel();
                indexDetail.setIndexName(indexName);
                indexDetail.setNonUnique((String) l.get("NON_UNIQUE"));
                indexDetail.setTableName((String) l.get("TABLE_NAME"));
                indexDetail.setColumnName((String) l.get("COLUMN_NAME"));
                indexDetail.setAscOrDesc((String) l.get("ASC_OR_DESC"));
                indexDetail.setOrdinalPosition((String) l.get("ORDINAL_POSITION"));
                set.add(indexDetail);
              });
      uimap.forEach(
          (k, v) -> {
            TableIndexSummaryModel summary = new TableIndexSummaryModel();
            summary.setIndexName(k);
            summary.setColumnCount(v.size());
            List<TableIndexDetailModel> ilist = new ArrayList<>();
            v.forEach(i -> ilist.add(i));
            summary.setDetails(ilist);
            summary.setUnique(true);
            summaryModel.getUindexes().put(k, summary);
          });
      imap.forEach(
          (k, v) -> {
            TableIndexSummaryModel summary = new TableIndexSummaryModel();
            summary.setIndexName(k);
            summary.setColumnCount(v.size());
            List<TableIndexDetailModel> ilist = new ArrayList<>();
            v.forEach(i -> ilist.add(i));
            summary.setDetails(ilist);
            summary.setUnique(false);
            summaryModel.getIndexes().put(k, summary);
          });

      System.out.println(summaryModel.getTableName() + " INDEX END");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void assembleSummaryColumn(
      DatabaseMetaData metaData, String schema, TableMetaSummaryModel summaryModel) {
    try {
      System.out.println(summaryModel.getTableName() + " COLUMN START");
      ResultSet cRs = metaData.getColumns(null, schema, summaryModel.getTableName(), "%");
      List<Map<String, Object>> cList = parseResultSetToMapList(cRs);
      //      cList.forEach(System.out::println);
      TreeSet<TableMetaDetailModel> details =
          new TreeSet<>(Comparator.comparing((TableMetaDetailModel::getOrdinalPosition)));

      cList.forEach(
          l -> {
            TableMetaDetailModel detail = new TableMetaDetailModel();
            detail.setColumnName((String) l.get("COLUMN_NAME"));
            detail.setColumnNameComment((String) l.get("REMARKS"));
            detail.setColumnType((String) l.get("TYPE_NAME"));
            detail.setColumnSize((String) l.get("COLUMN_SIZE"));
            detail.setNullable((String) l.get("IS_NULLABLE"));
            detail.setColumnDef((String) l.get("COLUMN_DEF"));
            detail.setAutoIncrement((String) l.get("IS_AUTOINCREMENT"));
            detail.setOrdinalPosition(
                (l.get("ORDINAL_POSITION") == null ? "99" : (String) l.get("ORDINAL_POSITION")));
            details.add(detail);
          });
      List<TableMetaDetailModel> list = new ArrayList<>();
      details.forEach(d -> list.add(d));
      summaryModel.setDetails(list);

      System.out.println(summaryModel.getTableName() + " COLUMN END");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
