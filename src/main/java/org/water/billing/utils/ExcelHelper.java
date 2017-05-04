package org.water.billing.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.water.billing.MyException;

public class ExcelHelper {

	@SuppressWarnings("resource")
	public static ExcelData readExcelFile(InputStream is,ExcelData output) throws MyException {
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook(is);
		} catch (IOException e) {
			throw new MyException("打开Excel文件失败");
		}
		HSSFSheet sheet = workbook.getSheetAt(0);
		int rowIndex = output.getFromRow();
		int columnNum = output.getColumnNum();
		while(true) {
			HSSFRow row = sheet.getRow(rowIndex);
			if(row == null)
				break;
			List<String> rowData = new ArrayList<String>();
			for(int i=0;i<columnNum;i++) {
				HSSFCell cell = row.getCell(i);
				if(cell == null || cell.getCellTypeEnum() == CellType.BLANK) {
					throw new MyException("第"+(rowIndex+1)+"行第"+(i+1)+"列数据为空");
				}
				String val = null;
				switch(cell.getCellTypeEnum()) {
					case NUMERIC:
						DecimalFormat df = new DecimalFormat("#");
						int fractionalDigits = 2;
						df.setMaximumFractionDigits(fractionalDigits);
						val = df.format(cell.getNumericCellValue());
						break;
					case STRING:
						val = cell.getStringCellValue();
					default:
						break;
				}
				rowData.add(val);
			}
			output.addRowContent(rowData);
			rowIndex ++;
		}
		return output;
	}
	
	@SuppressWarnings("resource")
	public static void writeExcel(ExcelData data,OutputStream os) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("表1");
		int columnNum = data.getColumnNum();
		for(int rowIndex=0;rowIndex<data.getContent().size();rowIndex++) {
			List<String> rowData = data.getContent().get(rowIndex);
			HSSFRow row = sheet.createRow(rowIndex);
			for(int i=0;i<columnNum;i++){
				HSSFCell cell = row.createCell(i);
				cell.setCellType(CellType.STRING);
				cell.setCellValue(rowData.get(i));
			}
		}
		try {
			wb.write(os);
			os.flush();
			os.close();
		} catch (IOException e) {

		}
	}
	
	public static void main(String[] args) throws IOException, MyException {
		File f = new File("/Users/Jims/Downloads/water.xls");
		FileInputStream fis = new FileInputStream(f);
		ExcelData data = new ExcelData(0,4);
		data = ExcelHelper.readExcelFile(fis, data);
		data.print();
		File ff = new File("/Users/Jims/Downloads/water1.xls");
		if(ff.exists())
			ff.delete();
		FileOutputStream os = new FileOutputStream(ff.getAbsolutePath());
		List<String> title = new ArrayList<String>();
		title.add("第1列");title.add("第2列");title.add("第3列");title.add("第4列");
		data.setTitle(title);
		ExcelHelper.writeExcel(data, os);
	}
}
