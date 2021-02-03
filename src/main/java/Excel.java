import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFPivotTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Excel {
    public static void createTable(double[][][][] areaPositions, double[][][] areaValues, boolean[][][] insideLamp) {
        Workbook workbook = new XSSFWorkbook();
        Sheet lightSheet = workbook.createSheet("light");

        Row headerRow = lightSheet.createRow(0);

        Cell xHeader = headerRow.createCell(0);
        Cell yHeader = headerRow.createCell(1);
        Cell zHeader = headerRow.createCell(2);
        Cell lightHeader = headerRow.createCell(3);

        xHeader.setCellValue("x");
        yHeader.setCellValue("y");
        zHeader.setCellValue("z");
        lightHeader.setCellValue("I");

        int rowIndex = 1;
        for (int i = 0; i < areaPositions.length; i++) {
            for (int j = 0; j < areaPositions[i].length; j++) {
                for (int k = 0; k < areaPositions[i][j].length; k++) {
                    if (!insideLamp[i][j][k]) {
                        Row lightRow = lightSheet.createRow(rowIndex);

                        Cell x = lightRow.createCell(0);
                        Cell y = lightRow.createCell(1);
                        Cell z = lightRow.createCell(2);
                        Cell light = lightRow.createCell(3);

                        x.setCellValue(areaPositions[i][j][k][0]);
                        y.setCellValue(areaPositions[i][j][k][1]);
                        z.setCellValue(areaPositions[i][j][k][2]);
                        light.setCellValue(areaValues[i][j][k]);

                        rowIndex++;
                    }
                }
            }
        }
        XSSFSheet pivotSheet = (XSSFSheet) workbook.createSheet("pivot");
        XSSFPivotTable pivotTable = pivotSheet.createPivotTable(
                new AreaReference(
                        new CellReference(0, 0),
                        new CellReference(lightSheet.getLastRowNum(), 3),
                        SpreadsheetVersion.EXCEL2007),
                new CellReference(0, 0),
                lightSheet);

        pivotTable.addRowLabel(0);
        pivotTable.addRowLabel(1);
        pivotTable.addRowLabel(2);
        pivotTable.addRowLabel(3);

        try {
            workbook.write(new FileOutputStream("LIGHT.xlsx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("DONE");
    }
}
