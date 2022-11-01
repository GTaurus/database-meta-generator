package io.gtaurus.db.utils;

import io.gtaurus.db.meta.models.TableMetaSummaryModel;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static io.gtaurus.db.utils.MetaUtils.genMeta;

/** Created by GTaurus on 2019/2/11. */
public class MetaUtilsTest {
  
  public static final String DB_ADDRESS = "";

  @Test
  public void genLoan() throws Exception {

    String clazz = "oracle.jdbc.OracleDriver";
    String url = "jdbc:oracle:thin:@//" + DB_ADDRESS + "/efspdb_sit";
    String user = "LOAN";
    String password = "loan";
    boolean remarksReporting = true;
    List<TableMetaSummaryModel> models =
        genMeta(clazz, url, user, password, remarksReporting, user, "%");
    writeToFile(user, FileDataGenerator.genFileData(models));
  }

  @Test
  public void genCust() throws Exception {

    String clazz = "oracle.jdbc.OracleDriver";
    String url = "jdbc:oracle:thin:@//" + DB_ADDRESS + "/efspdb_dev";
    String user = "CUST";
    String password = "cust";
    boolean remarksReporting = true;

    List<TableMetaSummaryModel> models =
        genMeta(clazz, url, user, password, remarksReporting, user, "CUST%");
    writeToFile(user, FileDataGenerator.genFileData(models));
  }


  @Test
  public void genefsBp() throws Exception {

    String clazz = "oracle.jdbc.OracleDriver";
    String url = "jdbc:oracle:thin:@//" + DB_ADDRESS + "/efspdb_dev";
    String user = "BP";
    String password = "bp";
    boolean remarksReporting = true;

    List<TableMetaSummaryModel> models =
        genMeta(clazz, url, user, password, remarksReporting, user, "%");
    writeToFile(user, FileDataGenerator.genFileData(models));
  }

  @Test
  public void genefsw() throws Exception {

    String clazz = "oracle.jdbc.OracleDriver";
    String url = "jdbc:oracle:thin:@//" + DB_ADDRESS + "/efspdb_dev";
    String user = "EFSW";
    String password = "efsw";
    boolean remarksReporting = true;

    List<TableMetaSummaryModel> models =
        genMeta(clazz, url, user, password, remarksReporting, user, "EFSW%");
    writeToFile(user, FileDataGenerator.genFileData(models));
  }

  @Test
  public void genintfc() throws Exception {

    String clazz = "oracle.jdbc.OracleDriver";
    String url = "jdbc:oracle:thin:@//" + DB_ADDRESS + "/efspdb_dev";
    String user = "EFSW";
    String password = "efsw";
    boolean remarksReporting = true;

    List<TableMetaSummaryModel> models =
            genMeta(clazz, url, user, password, remarksReporting, user, "%INTFC%");
    writeToFile(user, FileDataGenerator.genFileData(models));
  }

  @Test
  public void genaccounting() throws Exception {

    String clazz = "oracle.jdbc.OracleDriver";
    String url = "jdbc:oracle:thin:@//" + DB_ADDRESS + "/efspdb_dev";
    String user = "EFSW";
    String password = "efsw";
    boolean remarksReporting = true;

    List<TableMetaSummaryModel> models =
            genMeta(clazz, url, user, password, remarksReporting, user, "ACC%");
    writeToFile(user, FileDataGenerator.genFileData(models));
  }

  @Test
  public void genefswLoanInfo() throws Exception {

    String clazz = "oracle.jdbc.OracleDriver";
    String url = "jdbc:oracle:thin:@//" + DB_ADDRESS + "/efspdb_dev";
    String user = "EFSW";
    String password = "efsw";
    boolean remarksReporting = true;

    List<TableMetaSummaryModel> models =
        genMeta(clazz, url, user, password, remarksReporting, user, "EFSW_L%");
    writeToFile(user, FileDataGenerator.genFileData(models));
  }

  @Test
  public void genecd() throws Exception {

    String clazz = "oracle.jdbc.OracleDriver";
    String url = "jdbc:oracle:thin:@//" + DB_ADDRESS + "/efspdb_dev";
    String user = "ECD";
    String password = "ecd";
    boolean remarksReporting = true;

    List<TableMetaSummaryModel> models =
        genMeta(clazz, url, user, password, remarksReporting, user, "%");
    writeToFile(user, FileDataGenerator.genFileData(models));
  }

  @Test
  public void genFund() throws Exception {

    String clazz = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://10.116.50.109:3306/fundDB";
    String user = "fundDB";
    String password = "fundDB";
    boolean remarksReporting = true;

    List<TableMetaSummaryModel> models =
        genMeta(clazz, url, user, password, remarksReporting, user, "%");
    writeToFile(user, FileDataGenerator.genFileData(models));
  }

  public void writeToFile(String fileName, byte[] fileData) throws Exception {
    Files.write(Paths.get(Env.FILE_PATH + fileName + ".xlsx"), fileData, StandardOpenOption.CREATE);
  }
}
