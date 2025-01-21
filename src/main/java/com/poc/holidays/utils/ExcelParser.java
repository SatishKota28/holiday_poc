package com.poc.holidays.utils;

import com.poc.holidays.entity.Holiday;
import com.poc.holidays.enums.Country;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExcelParser {

    public static List<Holiday> parseExcelFile(MultipartFile[] file) throws IOException {
        List<Holiday> holidays = new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            try (InputStream is = multipartFile.getInputStream()) {
                Workbook workbook = new XSSFWorkbook(is);
                Sheet sheet = workbook.getSheetAt(0);
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) continue;
                    Holiday holiday = new Holiday();
                    if (null == row.getCell(0) || null == row.getCell(1) || null == row.getCell(2) || null == row.getCell(3)) {
                        return Collections.emptyList();
                    }
                    String holidayName = row.getCell(0).getStringCellValue();
                    if (null == holidayName || holidayName.isBlank())
                        return Collections.emptyList();
                    holiday.setName(holidayName);

                    DataFormatter formatter = new DataFormatter();
                    FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

                    String holidayDate = formatter.formatCellValue(row.getCell(1), evaluator);
                    if (null == holidayDate || holidayDate.isBlank())
                        return Collections.emptyList();
                    ;
                    holiday.setHolidayDate(holidayDate);

                    String day = row.getCell(2).getStringCellValue();
                    if (null == day || day.isBlank())
                        return Collections.emptyList();
                    ;
                    holiday.setDay(day);

                    Country country = Country.valueOf(row.getCell(3).getStringCellValue());
                    holiday.setCountry(country);
                    holidays.add(holiday);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Collections.emptyList();
            }
        }
        return holidays;
    }
}
