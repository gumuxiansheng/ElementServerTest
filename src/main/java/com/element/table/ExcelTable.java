package com.element.table;

import tech.tablesaw.api.Table;

public class ExcelTable {

    public ExcelTable(String title, Table table) {
        this.title = title;
        this.table = table;
    }

    String title;
    Table table;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

}