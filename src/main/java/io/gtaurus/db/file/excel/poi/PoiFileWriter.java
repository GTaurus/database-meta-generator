package io.gtaurus.db.file.excel.poi;

import io.gtaurus.db.file.models.CellModel;
import io.gtaurus.db.file.models.RowModel;
import io.gtaurus.db.file.models.SheetModel;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiFileWriter {

  public static byte[] genFileData(List<SheetModel> sheets) throws Exception {
    // 创建新工作簿
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFCreationHelper creationHelper = new XSSFCreationHelper(workbook);
    sheets.forEach(
        s -> {
          String sName = s.getSheetName();
          // 新建工作表
          XSSFSheet sheet = workbook.createSheet(sName);
          // 150%
          sheet.setZoom(150);
          // 创建行,行号作为参数传递给createRow()方法,第一行从0开始计算
          s.getRows().forEach(r -> {});

          for (int i = 0; i < s.getRows().size(); i++) {
            XSSFRow row = sheet.createRow(i);
            RowModel r = s.getRows().get(i);
            if (r.isBlankRow()) {
              continue;
            }
            for (int j = 0; j < r.getCells().size(); j++) {
              CellModel c = r.getCells().get(j);
              XSSFCell cell = row.createCell(j);
              cell.setCellValue(c.getValue());
              // link
              if (c.getLocation() != null && c.getLocation().trim().length() > 0) {
                cell.setHyperlink(creationHelper.createHyperlink(HyperlinkType.DOCUMENT));
                cell.getHyperlink().setLocation(c.getLocation() + "!A1");
              }
              // colspan
              CellRangeAddress region = null;
              if (c.getColspan() > 0) {
                region = new CellRangeAddress(i, i, j, j + c.getColspan());
                sheet.addMergedRegion(region);
              }
              // font
              if (c.getFontColor() != null && c.getFontColor().trim().length() > 0) {
                cell.getCellStyle().setFont(workbook.createFont());
                cell.getCellStyle()
                    .getFont()
                    .setColor(IndexedColors.valueOf(c.getFontColor()).getIndex());
              }
              // foreground
              CellStyle cs = null;
              if (c.getForeGroundColor() != null && c.getForeGroundColor().trim().length() > 0) {
                if (cs == null) {
                  cs = workbook.createCellStyle();
                }
                cs.setFillForegroundColor(IndexedColors.valueOf(c.getForeGroundColor()).getIndex());
                cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
              }
              if (c.isBorder()) {
                if (cs == null) {
                  cs = workbook.createCellStyle();
                }
                cs.setBorderBottom(BorderStyle.THIN); // 下边框
                cs.setBorderLeft(BorderStyle.THIN); // 左边框
                cs.setBorderTop(BorderStyle.THIN); // 上边框
                cs.setBorderRight(BorderStyle.THIN); // 右边框
                if (region != null) {
                  RegionUtil.setBorderBottom(BorderStyle.THIN, region, cell.getSheet()); // 下边框
                  RegionUtil.setBorderLeft(BorderStyle.THIN, region, cell.getSheet()); // 左边框
                  RegionUtil.setBorderTop(BorderStyle.THIN, region, cell.getSheet()); // 上边框
                  RegionUtil.setBorderRight(BorderStyle.THIN, region, cell.getSheet()); // 右边框
                }
              }
              if (cs != null) {
                cell.setCellStyle(cs);
              }
              if (c.getValue() != null && sheet.getColumnWidth(j) < c.getValue().length() * 256) {
                sheet.setColumnWidth(j, c.getValue().length() * 256);
              }
            }
          }
        });
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    workbook.write(baos);
    workbook.close();
    return baos.toByteArray();
  }
}
