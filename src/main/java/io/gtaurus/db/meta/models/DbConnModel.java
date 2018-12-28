package io.gtaurus.db.meta.models;

public class DbConnModel {

  private String clazz = "oracle.jdbc.OracleDriver";
  private String user = "pmpf20150504";
  private String password = "pmpf";
  private String url = "jdbc:oracle:thin:@10.116.15.100:1521:app";
  private boolean remarksReporting;

  public String getClazz() {
    return clazz;
  }

  public void setClazz(String clazz) {
    this.clazz = clazz;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public boolean isRemarksReporting() {
    return remarksReporting;
  }

  public void setRemarksReporting(boolean remarksReporting) {
    this.remarksReporting = remarksReporting;
  }
}
