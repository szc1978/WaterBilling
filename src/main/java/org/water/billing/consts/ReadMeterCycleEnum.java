package org.water.billing.consts;

public enum ReadMeterCycleEnum {
	MONTHLY("单月抄表"),
	BI_MONTHLY("双月抄表"),
	QUARTER("季度抄表")
	;
	
	private ReadMeterCycleEnum(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	private String name = "";
}
