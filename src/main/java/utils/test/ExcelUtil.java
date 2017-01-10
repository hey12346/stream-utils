package utils.test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.eclipse.jetty.util.ByteArrayOutputStream2;

/**
 * Created by kevin.gao on 1/10/2017.
 */
public class ExcelUtil <T extends Object> {
    List<Map<String, String>> rows = new ArrayList<>();

    public ExcelUtil(List <T> rowsOfData) {
        for(T myObj: rowsOfData) {
            ObjectMapper oMapper = new ObjectMapper();
            Map<String, String> data = oMapper.convertValue(myObj, Map.class);

            rows.add(data);
        }
    }

    public void getExcelFile(OutputStream myStream) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sample sheet");

        int counter = 0;
        for(String key: rows.get(0).keySet()) {
            Row row = sheet.createRow(counter);
            Cell cell = row.createCell(counter);
            cell.setCellValue(key);
            counter++;
        }

        for(Map <String, String> obj: rows) {
            counter = 0;
            Row row = sheet.createRow(counter);
            for(String key: obj.keySet()) {
                Cell cell = row.createCell(counter);
                cell.setCellValue(obj.get(key));
                counter++;
            }
        }

        workbook.write(myStream);
    }
}
