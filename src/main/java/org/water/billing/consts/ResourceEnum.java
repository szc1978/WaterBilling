package org.water.billing.consts;

public enum ResourceEnum {
	PAY("缴费管理","/pay"),
	WATER_NUMBER("录入用水量","/waterdata"),
	CUSTOMER_MANAGE("客户管理","/customer/manage"),
	CUSTOMER_TYPE("客户类别管理","/customer/type"),
	WATER_PROVIDER("供水片区管理","/customer/waterprovider"),
	WATER_METER("水表管理","/customer/watermetertype"),
	CUSTOMER_PRESS_PAYMENT("催费通知","/customer/presspayment"),
	CHARGE_TYPE("资费类型","/charge/type"),
	APPROVE_CUSTOMER("客户销户开户审核","/approve/customer"),
	APPROVE_CUSTOMER_WATER("客户用水量审核","/approve/customerwater"),
	APPROVE_CUSTOMER_BILL("客户销账审核","/approve/customerbill"),
	ADMIN_USER("系统用户管理","/admin/user"),
	ADMIN_ROLE("系统用户权限管理","/admin/role"),
	ADMIN_PRIVILEGE("页面功能管理","/admin/privilege"),
	ADMIN_GOLBAL_CONFIG("系统全局配置","/admin/configuration"),
	ADMIN_STATISTIC("统计数据","/admin/statistic"),
	OP_LOG("操作日志","/log/operation"),
	LOGIN_LOG("登录日志","/log/login"),
	PUBLIC_ANNOUNCEMENT("公告管理","/admin/announcement")
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
