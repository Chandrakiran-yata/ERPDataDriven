package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil {
Workbook wb;

public ExcelFileUtil(String Excelpath) throws Throwable

{
	FileInputStream fi=new FileInputStream(Excelpath);
	wb=WorkbookFactory.create(fi);
}
//method for counting no of rows in a sheet
 public int rowCount(String sheetName)
 {
	return wb.getSheet(sheetName).getLastRowNum();
 }
 //method for reading cell data
 public String getCellData(String sheetName,int row,int colum)
 {
	 String data="";
	 if(wb.getSheet(sheetName).getRow(row).getCell(colum).getCellType()==CellType.NUMERIC)
	 {
		 int celldata=(int)wb.getSheet(sheetName).getRow(row).getCell(colum).getNumericCellValue();
		 data=String.valueOf(celldata);
	 }
	 else
	 {
		 data=wb.getSheet(sheetName).getRow(row).getCell(colum).getStringCellValue(); 
	 }
	 return data;
 }
	 //method for writing results
	 public void setCellData(String sheetName,int row,int colum,String status,String WriteExcel) throws Throwable
	 {
		 //get sheet from wb
		 Sheet ws=wb.getSheet(sheetName);
		 //get row from sheet
		 Row rowNum=ws.getRow(row);
		 //create cell
		 Cell cell=rowNum.createCell(colum);
		 //write status
		 cell.setCellValue(status);
		 
		 if(status.equalsIgnoreCase("pass"))
	     {
		 CellStyle Style=wb.createCellStyle();
		 Font font=wb.createFont();
		 font.setColor(IndexedColors.GREEN.getIndex());
		 font.setBold(true);
		 Style.setFont(font);
		 ws.getRow(row).getCell(colum).setCellStyle(Style);
		 
	     }
		 else if(status.equalsIgnoreCase("Fail"))
		 {
			 CellStyle Style=wb.createCellStyle();
			 Font font=wb.createFont();
			 font.setColor(IndexedColors.RED.getIndex());
			 font.setBold(true);
			 Style.setFont(font);
			 ws.getRow(row).getCell(colum).setCellStyle(Style);
			 
		 }
		 else if(status.equalsIgnoreCase("blocked"))
		 {
			 CellStyle Style=wb.createCellStyle();
			 Font font=wb.createFont();
			 font.setColor(IndexedColors.BLUE.getIndex());
			 font.setBold(true);
			 Style.setFont(font);
			 ws.getRow(row).getCell(colum).setCellStyle(Style);
			 
		 }
		 FileOutputStream fo= new FileOutputStream(WriteExcel);
		 wb.write(fo);
	 }
	 public static void main(String[] args)throws Throwable {
		ExcelFileUtil xl=new ExcelFileUtil("D:/Sample.xlsx");
		//count no of rows in emp sheet
		int rc=xl.rowCount("emp");
		System.out.println(rc);
		for(int i=1;i<=rc;i++)
		{ 
			String fname=xl.getCellData("emp",i,0);
			String mname=xl.getCellData("emp",i,1);
			String lname=xl.getCellData("emp",i,2);
			String eid =xl.getCellData("emp",i,3);
		System.out.println(fname+"  "+mname+"    "+lname+"    "+eid);
		xl.setCellData("emp",i,4,"pass","D:/Results.xlsx");
		xl.setCellData("emp",i,4,"pass","D:/Results.xlsx");
		xl.setCellData("emp",i,4,"pass","D:/Results.xlsx");

		}}
}
