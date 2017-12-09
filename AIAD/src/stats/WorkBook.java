package stats;

import java.io.*;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WorkBook {
	public static XSSFWorkbook create() {
		//Create a workbook
		XSSFWorkbook workbook = new XSSFWorkbook(); 
		return workbook;
	}	
	
	public static XSSFRow createRow(XSSFSheet sheet, int id) {
		//Create a workbook
		System.out.println("NEW ROW " + id);
		return sheet.createRow(id);
	}

	public static void publishContents(XSSFWorkbook workbook) throws IOException {
		//Create file system using specific name
		File myFile = new File("src/assets/statistics.xlsx");
		// if file already exists will do nothing 
		myFile.createNewFile(); 
		FileOutputStream out = new FileOutputStream(myFile,false);

		//write operation workbook using file out object 
		workbook.write(out);
		out.flush();
		out.close();
	}

}
