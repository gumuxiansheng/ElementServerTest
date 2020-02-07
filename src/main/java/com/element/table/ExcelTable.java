package com.element.table;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tech.tablesaw.api.Table;

@AllArgsConstructor
public class ExcelTable {
    @Getter @Setter String title;
    @Getter @Setter Table table;

}