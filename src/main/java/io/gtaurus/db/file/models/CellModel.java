package io.gtaurus.db.file.models;

public class CellModel {

  private String value;
  private String location;
  private String fontColor;
  private String foreGroundColor;
  private int colspan;
  private boolean border;

  public CellModel(boolean border) {
    this.value = null;
    this.border = border;
  }

  public CellModel(String value, boolean border) {
    this.value = value;
    this.border = border;
  }

  public CellModel(String value, int colspan, boolean border) {
    this.value = value;
    this.colspan = colspan;
    this.border = border;
  }

  public CellModel(String value, String location, boolean border) {
    this.value = value;
    this.location = location;
    this.border = border;
  }

  public static CellModel blankCell() {
    return new CellModel(null, false);
  }

  public static CellModel blankCellWithBorder() {
    return new CellModel(true);
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getFontColor() {
    return fontColor;
  }

  public void setFontColor(String fontColor) {
    this.fontColor = fontColor;
  }

  public String getForeGroundColor() {
    return foreGroundColor;
  }

  public void setForeGroundColor(String foreGroundColor) {
    this.foreGroundColor = foreGroundColor;
  }

  public int getColspan() {
    return colspan;
  }

  public void setColspan(int colspan) {
    this.colspan = colspan;
  }

  public boolean isBorder() {
    return border;
  }

  public void setBorder(boolean border) {
    this.border = border;
  }
}
