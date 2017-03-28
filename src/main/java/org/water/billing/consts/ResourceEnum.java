package org.water.billing.consts;

public enum ResourceEnum {
	BIZ_USER("用户管理","/biz/customer"),
	BIZ_USER_DOC("用户档案管理","/biz/userdoc"),
	BIZ_USER_TYPE("用户类别管理","/biz/usertype"),
	BIZ_USER_KEY("用户字段管理","/biz/userkey"),
	BIZ_FEE("资费管理","/biz/fee"),
	BIZ_CHARGE("用户缴费","/biz/charge"),
	ADMIN_USER("管理员管理","/admin/user"),
	ADMIN_ROLE("管理员角色管理","/admin/role"),
	ADMIN_PRIVILEGE("角色权限管理","/admin/privilege"),
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
