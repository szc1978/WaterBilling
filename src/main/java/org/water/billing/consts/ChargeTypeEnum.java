package org.water.billing.consts;

public enum ChargeTypeEnum {
	CHARGE_BY_PER_PRICE(1,"水量单价方式收费"),
	CHARGE_BY_DEDICATE_PRICE(2,"周期固定价格收费"),
	CHARGE_STEP_BY_STEP(3,"阶梯价格方式收费")
	;
	
	private ChargeTypeEnum(int id,String name) {
		this.name = name;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	private String name = "";
	private int id = 0;
}
