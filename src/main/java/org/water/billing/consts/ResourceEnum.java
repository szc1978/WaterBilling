package org.water.billing.consts;

public enum ResourceEnum {
	PAY("缴费管理","/pay"),
	WATER_NUMBER("录入用水量","/waterdata"),
	CUSTOMER_MANAGE("客户管理","/customer/manage"),
	CUSTOMER_TYPE("客户类别管理","/customer/type"),
	CUSTOMER_FIELD("客户字段管理","/customer/field"),
	WATER_PROVIDER("供水片区管理","/customer/waterprovider"),
	CHARGE_TYPE("资费类型","/charge/type"),
	APPROVE_CUSTOMER("客户销户开户审核","/approve/customer"),
	APPROVE_CUSTOMER_WATER("客户用水量审核","/approve/customerwater"),
	APPROVE_CUSTOMER_BILL("客户销账审核","/approve/customerbill"),
	ADMIN_USER("系统用户管理","/admin/user"),
	ADMIN_ROLE("系统用户权限管理","/admin/role"),
	ADMIN_PRIVILEGE("页面功能管理","/admin/privilege"),
	ADMIN_GOLBAL_CONFIG("系统全局配置","/admin/configuration"),
	OP_LOG("操作日志","/log/operation"),
	LOGIN_LOG("登录日志","/log/login")
	;
	
	
	private ResourceEnum(String name,String resourceString) {
		this.name = name;
		this.resourceString = resourceString;
	}
	
	public String getName() {
		return name;
	}
	
	public String getResourceString() {
		return resourceString;
	}
	
	private String name = "";
	private String resourceString = "";
}
