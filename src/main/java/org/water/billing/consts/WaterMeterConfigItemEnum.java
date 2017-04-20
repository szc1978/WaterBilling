package org.water.billing.consts;

public enum WaterMeterConfigItemEnum {
	PRODUCER(1,"水表厂家"),
	MODEL(2,"水表型号"),
	SIZE(3,"水表口径"),
	USAGE(4,"水表用途"),
	STATUS(5,"水表状态");
	
	private WaterMeterConfigItemEnum(int id,String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	private int id;
	private String name;
}
