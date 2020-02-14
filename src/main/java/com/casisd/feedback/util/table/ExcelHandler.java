package com.casisd.feedback.util.table;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import tech.tablesaw.api.Table;
import tech.tablesaw.io.Source;
import tech.tablesaw.io.xlsx.XlsxReadOptions;

public class ExcelHandler {
    /**
     * 根据文件路径，生成workbook实例
     * 
     * @param filePath
     * @return
     */
    public static ExcelTable readExcel(final String filePath, boolean hasHeader) {
        if (filePath == null || filePath.isEmpty()) {
            return null;
        }

        ExcelTable eTable = null;

        try {
            eTable = readExcel(new FileInputStream(filePath), hasHeader);
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }

        return eTable;
    }

    /**
     * 通过流获取workbook的实例
     * 
     * @param inputStream
     * @return
     */
    public static ExcelTable readExcel(final InputStream inputStream, boolean hasHeader) {
        Workbook workbook = null;
        String title = null;
        Table table = null;

        try {
            workbook = WorkbookFactory.create(inputStream);

            final Sheet sheet = workbook.getSheetAt(0);

            // The first row is the title of this table, and the data starts from the second row.
            if (hasHeader){
                Row titleRow = sheet.getRow(0);
                title = titleRow.getCell(0).getStringCellValue();
                sheet.removeRow(titleRow);
            }

            // write into temp file then it's convenient for tablesaw to read.
            FileOutputStream fOutputStream = new FileOutputStream("temp.xlsx");
            workbook.write(fOutputStream);
            FileInputStream inputStream2 = new FileInputStream(new File("temp.xlsx"));

            table = Table.read().usingOptions(XlsxReadOptions.builder(new Source(inputStream2)).header(true));
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        ExcelTable eTable = new ExcelTable(title, table);
        return eTable;
    }
}