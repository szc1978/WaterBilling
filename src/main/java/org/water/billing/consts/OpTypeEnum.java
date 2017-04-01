package org.water.billing.consts;

public enum OpTypeEnum {
	OPERATION(1),
	EXCEPTION(2)
	;
	
	private OpTypeEnum(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	private int id = 0;
}
