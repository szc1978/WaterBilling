package org.water.billing.consts;

public enum ResourceEnum {
	BIZ_USER("客户管理","/biz/customer"),
	BIZ_USER_TYPE("客户类别管理","/biz/customer_type"),
	BIZ_USER_KEY("客户字段管理","/biz/customer_field"),
	BIZ_FEE("资费管理","/biz/charge"),
	BIZ_CUSTOMER_PAY("客户缴费","/biz/pay"),
	BIZ_CUSTOMER_WATER_INPUT("客户用水录入","/biz/input_water_data"),
	BIZ_CUSTOMER_WATER_IMPORT("客户用水导入","/biz/import_water_data"),
	BIZ_CUSTOMER_APPROVER("客户业务审核","/biz/approver"),
	BIZ_WATER_PROVIDER("供水片区管理","/biz/waterprovider"),
	ADMIN_USER("系统用户管理","/admin/user"),
	ADMIN_ROLE("系统用户权限管理","/admin/role"),
	ADMIN_PRIVILEGE("页面功能管理","/admin/privilege"),
	ADMIN_GOLBAL_CONFIG("系统全局配置","/admin/configuration"),
	OP_LOG("操作日志","/admin/oplog"),
	LOGIN_LOG("登录日志","/admin/loginhistory")
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
