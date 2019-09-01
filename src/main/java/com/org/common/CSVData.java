package com.org.common;

import java.util.List;
import java.util.Map;

public class CSVData {

	List<String[]> rowData;
	Integer rowCount;
	Integer columnCount;
	Map<String, Integer> columnNameToIndex;

	public List<String[]> getRowData() {
		return rowData;
	}

	public void setRowData(List<String[]> rowData) {
		this.rowData = rowData;
	}

	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	public Integer getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(Integer columnCount) {
		this.columnCount = columnCount;
	}

	public Map<String, Integer> getColumnNameToIndex() {
		return columnNameToIndex;
	}

	public void setColumnNameToIndex(Map<String, Integer> columnToIndex) {
		this.columnNameToIndex = columnToIndex;
	}

}
