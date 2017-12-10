package stats;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.poi.ss.examples.CellStyleDetails;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sun.javafx.css.converters.FontConverter.FontStyleConverter;

public class Statistics extends Thread implements Finishable {
	private XSSFWorkbook workbook;
	private XSSFSheet spreadSheet;
	private StatisticsGeneral general;
	private BlockingQueue<StatisticsInfo> toAddInfos;
	private boolean interrupted;
	
	public static Statistics instance;
	
	static {
		instance = new Statistics();
		instance.start();
	}

	private Statistics() {
		workbook = WorkBook.create();
		spreadSheet = workbook.createSheet("Sheet1");
		general = new StatisticsGeneral(Integer.MIN_VALUE, Integer.MAX_VALUE);
		toAddInfos = new LinkedBlockingQueue<StatisticsInfo>();
		interrupted = false;
		
		initialize();
	}
	
	private void initialize() {
	    CellStyle style = spreadSheet.getWorkbook().createCellStyle();
	    style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
	    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    
		XSSFRow row = spreadSheet.createRow(1);
		String[] generalLabels = {"Wait time", "Min", "Max"};
		
		Cell cell = row.createCell(1);
		cell.setCellStyle(style);
		cell.setCellValue(generalLabels[0]);
		spreadSheet.addMergedRegion(new CellRangeAddress(1,1,1,2));
		
		row = spreadSheet.createRow(2);
		cell = row.createCell(1);
		cell.setCellStyle(style);
		cell.setCellValue(generalLabels[1]);
		cell = row.createCell(2);
		cell.setCellStyle(style);
		cell.setCellValue(generalLabels[2]);
		
		row = spreadSheet.createRow(5);
		String[] labels = {"Elevator", "Occupation", "Usage", "", 
							"Receive", "Elevator", "Floor", "Elevator Floor", "Orientation", "Elevator Orientation", "Start", "Finish", "Diff", "", 
							"Take", "Elevator", "Floor", "Elevator Floor", "Orientation", "Elevator Orientation", "Start", "Finish", "Diff"};
		
		for (int cellid = 0; cellid < labels.length; cellid++) {
			cell = row.createCell(cellid + 1);
			if(!labels[cellid].equals(""))
				cell.setCellStyle(style);
			cell.setCellValue(labels[cellid]);
		}
	}
	
	private void newRequest(StatisticsInfo info) throws IOException {
		if(info.getType() == StatisticsInfo.StatisticsType.REQUEST)
			requestInfo((StatisticsRequest) info);
	}
	
	private void generalInfo(StatisticsGeneral request) throws IOException {
		int startRow = 3;
		int startCell = 1;
		XSSFRow row = spreadSheet.getRow(startRow);
		if(row == null)
			row = spreadSheet.createRow(startRow);

		XSSFCell cell = row.createCell(startCell++);
		cell.setCellValue(request.getMinWait());
		cell = row.createCell(startCell++);
		cell.setCellValue(request.getMaxWait());

		WorkBook.publishContents(workbook);
	}
	
	public void elevatorInfo(StatisticsElevator request) throws IOException {
		int startRow = request.getId() + 6;
		int startCell = 1;
		XSSFRow row = spreadSheet.getRow(startRow);
		if(row == null)
			row = spreadSheet.createRow(startRow);

		XSSFCell cell = row.createCell(startCell++);
		cell.setCellValue(request.getName());
		cell = row.createCell(startCell++);
		cell.setCellValue(request.getOccupation());
		cell = row.createCell(startCell++);
		cell.setCellValue(request.getUsage());

		WorkBook.publishContents(workbook);
	}

	private void requestInfo(StatisticsRequest request) throws IOException {
		int startRow = request.getId() + 6;
		int startCell = request.isReceive() ? 5 : 15;
		XSSFRow row = spreadSheet.getRow(startRow);
		if(row == null)
			row = spreadSheet.createRow(startRow);

		if(request.isReceive()) {
			if(request.getDiffTime() > general.getMaxWait())
				general.setMaxWait(request.getDiffTime());
			if(request.getDiffTime() < general.getMinWait())
				general.setMinWait(request.getDiffTime());
		}

		XSSFCell cell = row.createCell(startCell++);
		cell.setCellValue(request.getId());
		cell = row.createCell(startCell++);
		cell.setCellValue(request.getElevatorId());
		cell = row.createCell(startCell++);
		cell.setCellValue(request.getRequestFloor());
		cell = row.createCell(startCell++);
		cell.setCellValue(request.getElevatorFloor());
		cell = row.createCell(startCell++);
		cell.setCellValue(request.getRequestDirection());
		cell = row.createCell(startCell++);
		cell.setCellValue(request.getElevatorDirection());
		cell = row.createCell(startCell++);
		cell.setCellValue(request.getStartTime());
		cell = row.createCell(startCell++);
		cell.setCellValue(request.getFinishTime());
		cell = row.createCell(startCell);
		cell.setCellValue(request.getDiffTime());

		WorkBook.publishContents(workbook);
	}
	
	public void addInfo(StatisticsInfo info) {
		try {
			toAddInfos.put(info);
		} catch (InterruptedException ignore) {}
	}
		
	@Override
	public void run() {
		while(!interrupted || toAddInfos.size() != 0) {
			try {
				StatisticsInfo info = toAddInfos.take();
				newRequest(info);
			} catch (InterruptedException | IOException ignore) {}
		}
	}
	
	@Override
	public void interrupt() {
		super.interrupt();
		interrupted = true;
	}

	@Override
	public void finish() {
		try {
			generalInfo(general);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

