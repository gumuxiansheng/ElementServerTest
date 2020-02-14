package com.casisd.feedback.util.table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tech.tablesaw.api.Table;

@AllArgsConstructor
public class ExcelTable {
    @Getter @Setter String title;
    @Getter @Setter Table table;

}