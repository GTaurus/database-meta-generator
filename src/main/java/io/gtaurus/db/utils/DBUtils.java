package io.gtaurus.db.utils;

import io.gtaurus.db.meta.models.DbConnModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by linhaoran on 2017/10/10.
 */
public class DBUtils {

  public static Connection getConnection(DbConnModel connModel) {
    Connection conn = null;
    try {
      Class.forName(connModel.getClazz());
      Properties prop = new Properties();
      prop.put("user", connModel.getUser());
      prop.put("password", connModel.getPassword());
      prop.put("remarksReporting", Boolean.toString(connModel.isRemarksReporting()));
      conn = DriverManager.getConnection(connModel.getUrl(), prop);
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return conn;
  }

  public static void closeJDBC(ResultSet rs, Statement stmt, Connection conn) {
    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    if (stmt != null) {
      try {
        stmt.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
