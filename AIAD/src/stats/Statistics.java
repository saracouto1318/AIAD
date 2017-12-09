package stats;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Statistics extends Thread {
	private class StatisticsInfo {
		private int id;
		private Object[] info;
		private boolean isNew;
		
		private StatisticsInfo(int id, Object[] info, boolean isNew) {
			this.id = id;
			this.info = info;
			this.isNew = isNew;
		}
	}
	

	private XSSFWorkbook workbook;
	private XSSFSheet spreadSheet;
	
	private BlockingQueue<StatisticsInfo> toAddInfos;
	
	public static Statistics instance;
	
	static {
		instance = new Statistics();
		instance.start();
	}

	private Statistics() {
		workbook = WorkBook.create();
		spreadSheet = workbook.createSheet("Sheet1");
		toAddInfos = new LinkedBlockingQueue<StatisticsInfo>();
		
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
	
	private void newRequest(int id, Object[] info) throws IOException {
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
		
	private void updateInfo(int id, Object[] info) throws IOException {
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

	
	public synchronized void addInfo(int id, Object[] info, boolean isNew) {
		System.out.println("Write info for " + id + " " + info[0].toString());
		try {
			toAddInfos.put(new StatisticsInfo(id, info, isNew));
		} catch (InterruptedException ignore) {}
	}
	
	@Override
	public void run() {
		try {
			while(!isInterrupted()) {
				StatisticsInfo info = toAddInfos.take();
				if(info.isNew)
					newRequest(info.id, info.info);
				else
					updateInfo(info.id, info.info);
			}
		} catch (InterruptedException | IOException ignore) {}
	}
}

