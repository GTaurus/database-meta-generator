package io.gtaurus.db;

import io.gtaurus.db.meta.models.DbConnModel;
import io.gtaurus.db.meta.models.TableMetaSummaryModel;
import io.gtaurus.db.utils.AssembleUtils;
import io.gtaurus.db.utils.DBUtils;
import io.gtaurus.db.utils.Env;
import io.gtaurus.db.utils.FileDataGenerator;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class MetaReportGenerator {

  public static void main(String... args) throws Exception {
    //    genefsBp();
    genecd();
  }

  public static void genLoan() throws Exception {

    String clazz = "oracle.jdbc.OracleDriver";
    String url = "jdbc:oracle:thin:@//10.116.50.190:1521/efspdb_sit";
    String user = "LOAN";
    String password = "loan";
    boolean remarksReporting = true;
    List<TableMetaSummaryModel> models =
        genMeta(clazz, url, user, password, remarksReporting, user, "%");
    writeToFile(user, FileDataGenerator.genFileData(models));
  }

  public static void genCust() throws Exception {

    String clazz = "oracle.jdbc.OracleDriver";
    String url = "jdbc:oracle:thin:@//10.116.50.184:1521/efspdb_dev";
    String user = "CUST";
    String password = "cust";
    boolean remarksReporting = true;

    List<TableMetaSummaryModel> models =
        genMeta(clazz, url, user, password, remarksReporting, user, "CUST%");
    writeToFile(user, FileDataGenerator.genFileData(models));
  }

  public static void genvfs2Bp() throws Exception {

    String clazz = "oracle.jdbc.OracleDriver";
    String url = "jdbc:sqlserver://10.116.17.93\\QCDATA:1433;database=QCDATA";
    String user = "qcdatawrite";
    String password = "aaaa";
    boolean remarksReporting = true;

    List<TableMetaSummaryModel> models =
        genMeta(clazz, url, user, password, remarksReporting, "dbo", "AFW_BP%");

    writeToFile("AFW_BP", FileDataGenerator.genFileData(models));
  }

  public static void genefsBp() throws Exception {

    String clazz = "oracle.jdbc.OracleDriver";
    String url = "jdbc:oracle:thin:@//10.116.50.184:1521/efspdb_dev";
    String user = "BP";
    String password = "bp";
    boolean remarksReporting = true;

    List<TableMetaSummaryModel> models =
        genMeta(clazz, url, user, password, remarksReporting, user, "%");
    writeToFile(user, FileDataGenerator.genFileData(models));
  }

  public static void genefsw() throws Exception {

    String clazz = "oracle.jdbc.OracleDriver";
    String url = "jdbc:oracle:thin:@//10.116.50.184:1521/efspdb_dev";
    String user = "EFSW";
    String password = "efsw";
    boolean remarksReporting = true;

    List<TableMetaSummaryModel> models =
        genMeta(clazz, url, user, password, remarksReporting, user, "%");
    writeToFile(user, FileDataGenerator.genFileData(models));
  }

  public static void genecd() throws Exception {

    String clazz = "oracle.jdbc.OracleDriver";
    String url = "jdbc:oracle:thin:@//10.116.50.184:1521/efspdb_dev";
    String user = "ECD";
    String password = "ecd";
    boolean remarksReporting = true;

    List<TableMetaSummaryModel> models =
        genMeta(clazz, url, user, password, remarksReporting, user, "%");
    writeToFile(user, FileDataGenerator.genFileData(models));
  }

  public static void genFund() throws Exception {

    String clazz = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://10.116.50.109:3306/fundDB";
    String user = "fundDB";
    String password = "fundDB";
    boolean remarksReporting = true;

    List<TableMetaSummaryModel> models =
        genMeta(clazz, url, user, password, remarksReporting, user, "%");
    writeToFile(user, FileDataGenerator.genFileData(models));
  }

  private static List<TableMetaSummaryModel> genMeta(
      String clazz,
      String url,
      String user,
      String password,
      boolean remarksReporting,
      String schema,
      String tablePattern) {
    try (Connection conn =
        DBUtils.getConnection(genDbConnModel(clazz, url, user, password, remarksReporting))) {
      DatabaseMetaData metaData = conn.getMetaData();

      return AssembleUtils.assembleSummary(metaData, schema, tablePattern);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Collections.emptyList();
  }

  private static DbConnModel genDbConnModel(
      String clazz, String url, String user, String password, boolean remarksReporting) {
    DbConnModel connModel = new DbConnModel();
    connModel.setClazz(clazz);
    connModel.setUrl(url);
    connModel.setUser(user);
    connModel.setPassword(password);
    connModel.setRemarksReporting(remarksReporting);
    return connModel;
  }

  public static void writeToFile(String fileName, byte[] fileData) throws Exception {
    Files.write(Paths.get(Env.FILE_PATH + fileName + ".xlsx"), fileData, StandardOpenOption.CREATE);
  }
}
