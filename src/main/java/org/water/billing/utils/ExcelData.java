package org.water.billing.utils;

import java.util.ArrayList;
import java.util.List;

public class ExcelData {
	private int fromRow = 0;
	private int columnNum = 0;
	private List<List<String>> content = new ArrayList<List<String>>();
	
	public ExcelData(int fromRow,int columnNum) {
		this.fromRow = fromRow;
		this.columnNum = columnNum;
	}
	
	public void addRowContent(List<String> row) {
		content.add(row);
	}
	
	public List<List<String>> getContent() {
		return content;
	}

	public int getFromRow() {
		return fromRow;
	}

	public void setFromRow(int fromRow) {
		this.fromRow = fromRow;
	}

	public int getColumnNum() {
		return columnNum;
	}

	public void setColumnNum(int columnNum) {
		this.columnNum = columnNum;
	}

	public void setTitle(List<String> title) {
		content.add(0, title);
	}
	
	public void print() {
		for(List<String> l : content) {
			System.out.println(l);
		}
	}
}
