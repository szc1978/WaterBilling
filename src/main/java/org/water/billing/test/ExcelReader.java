package org.water.billing.test;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class ExcelReader {

	public static void main(String[] args) throws BiffException, IOException {
		Sheet sheet;
        Workbook book;
        Cell cell1,cell2,cell3,cell4;
        
        book= Workbook.getWorkbook(new File("/Users/Jims/Downloads/water.xls"));
        sheet=book.getSheet(0);
        cell1=sheet.getCell(0,0);
        for(int i=0;;i++) {
            cell1=sheet.getCell(0,i);//（列，行）
            if(cell1.getContents().startsWith("EOF"))
            	break;
            cell2=sheet.getCell(1,i);
            cell3=sheet.getCell(2,i);
            cell4=sheet.getCell(3,i);
            if("".equals(cell1.getContents())==true)    //如果读取的数据为空
                break;
            System.out.println(cell1.getContents()+"\t"+cell2.getContents()+"\t"+cell3.getContents()+"\t"+cell4.getContents()); 
        }
        book.close(); 
	}
}
