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
public class ExcelUtil <T extends ExcelObject> {
    private List<Map<String, Object>> rows = new ArrayList<>();

    public ExcelUtil(List <T> rowsOfData) {
        for(T myObj: rowsOfData) {
            ObjectMapper oMapper = new ObjectMapper();
            Map<String, Object> data = myObj.getData();

            rows.add(data);
        }
    }

    public void getExcelFile(OutputStream myStream) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sample sheet");

        int colCounter = 0;
        int rowCounter = 0;
        Row headerRow = sheet.createRow(rowCounter);
        for(String key: rows.get(0).keySet()) {
            Cell cell = headerRow.createCell(colCounter);
            cell.setCellValue(key);
            colCounter++;
        }
        rowCounter++;
        
        for(Map <String, Object> obj: rows) {
            colCounter = 0;
            Row row = sheet.createRow(rowCounter);
            for(String key: obj.keySet()) {
                Cell cell = row.createCell(colCounter);
                cell.setCellValue(obj.get(key).toString());
                colCounter++;
            }
            
            rowCounter++;
        }

        workbook.write(myStream);
    }
}
