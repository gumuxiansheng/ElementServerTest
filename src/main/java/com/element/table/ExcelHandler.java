package com.element.table;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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
    public static ExcelTable readExcel(final String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return null;
        }

        ExcelTable eTable = null;

        try {
            eTable = readExcel(new FileInputStream(filePath));
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
    public static ExcelTable readExcel(final InputStream inputStream) {
        Workbook workbook = null;
        String title = null;
        Table table = null;

        try {
            workbook = WorkbookFactory.create(inputStream);

            final Sheet sheet = workbook.getSheetAt(0);

            title = sheet.getRow(0).getCell(0).getStringCellValue();
            table = Table.read().usingOptions(XlsxReadOptions.builder(new Source(inputStream)));
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        final ExcelTable eTable = new ExcelTable(title, table);
        return eTable;
    }
}