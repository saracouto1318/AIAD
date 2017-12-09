package stats;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Statistics {
	private volatile XSSFWorkbook workbook;
	private volatile XSSFSheet spreadSheet;
	
	public static Statistics instance = new Statistics();

	private Statistics() {
		workbook = WorkBook.create();
		spreadSheet = workbook.createSheet("Sheet1");
		
		initialize();
	}
	
	private void initialize() {
		XSSFRow row = WorkBook.createRow(spreadSheet, 0);
		String[] labels = {"Receive", "Floor", "Elevator Floor", "Orientation", "Elevator Orientation", "Start", "Finish", "Diff", "", 
							"Take", "Floor", "Elevator Floor", "Orientation", "Elevator Orientation", "Start", "Finish", "Diff"};
		
		for (int cellid = 0; cellid < labels.length; cellid++) {
			Cell cell = row.createCell(cellid);
			cell.setCellValue(labels[cellid]);
		}
	}
	
	public synchronized void newRequest(int id, Object[] info) throws IOException {
		if(info.length != 9)
			throw new Error("Error updating new request");
		
		int startRow = id + 1;
		int startCell = (Boolean)info[0] ? 0 : 9;
		XSSFRow row = spreadSheet.getRow(startRow);
		if(row == null)
			row = WorkBook.createRow(spreadSheet, startRow);

		for(int i = 1; i < info.length; i++) {
			XSSFCell cell = row.createCell(startCell + i - 1);
			cell.setCellValue(info[i].toString());
		}
		
		WorkBook.publishContents(workbook);
	}
		
	public synchronized void updateInfo(int id, Object[] info) throws IOException {
		if(info.length != 9)
			throw new Error("Error updating new request");
		
		int startCell = (Boolean)info[0] ? 0 : 9, startRow = id + 1;
		XSSFRow row = spreadSheet.getRow(startRow);
		
		for(int i = 1; i < info.length; i++) {
			XSSFCell cell = row.getCell(startCell + i - 1);
			cell.setCellValue(info[i].toString());
		}

		WorkBook.publishContents(workbook);
	}
		
	private void testRun() throws IOException {
		//This data needs to be written (Object[])
		Map < Integer, Object[] > empinfo = new TreeMap < Integer, Object[] >();
		empinfo.put(0, new Object[] { true, "EMP NAME", "DESIGNATION", "fjfj", "ffkkf", "fjfjfj", "fjfjfj", "fjfjfj", "ddkdkdk" });
		empinfo.put(1, new Object[] { true, "Gopal", "Technical Manager", "fjfj", "ffkkf", "fjfjfj", "fjfjfj", "fjfjfj", "ddkdkdk" });
		empinfo.put(2, new Object[] { true, "Manisha", "Proof Reader", "fjfj", "ffkkf", "fjfjfj", "fjfjfj", "fjfjfj", "ddkdkdk" });
		empinfo.put(3, new Object[] { true, "Masthan", "Technical Writer", "fjfj", "ffkkf", "fjfjfj", "fjfjfj", "fjfjfj", "ddkdkdk" });
		empinfo.put(4, new Object[] { true, "Satish", "Technical Writer", "fjfj", "ffkkf", "fjfjfj", "fjfjfj", "fjfjfj", "ddkdkdk" });
		empinfo.put(5, new Object[] { true, "Krishna", "Technical Writer", "fjfj", "ffkkf", "fjfjfj", "fjfjfj", "fjfjfj", "ddkdkdk" });
	  
		//Iterate over data and write to sheet
		Set < Integer > keyid = empinfo.keySet();
		
		for (Integer key : keyid) {
			Object[] arr = empinfo.get(key);
			key = key == 1 ? 4 : key == 4 ? 1 : key;
			newRequest(key, arr);
			arr[0] = false;
			newRequest(key, arr);
		}		
	}
	
	public static void main(String[] args) throws Exception {
		Statistics s = new Statistics();
		s.testRun();
	}

}
