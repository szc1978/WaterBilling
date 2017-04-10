package org.water.billing.consts;

import java.util.ArrayList;
import java.util.List;

public class Consts {
	public static final List<String> CHARGE_FROM_TYPE = new ArrayList<String>() {
		private static final long serialVersionUID = -2145618765457370938L;
		{ add("抄表");add("固定");add("其他"); }
	};
	
	public static final List<String> CUSTOMER_CERTIFICATE_TYPE = new ArrayList<String>() {
		private static final long serialVersionUID = -2409239467580519026L;
		{ add("身份证");add("军官证");add("驾驶证"); }
	};
	
	public static final Integer MIN_ADMIN_USER_PWD_LENGTH = 4;
	
	public static final Integer STATUS_DEFINE_ACTIVE = 1;
	public static final Integer STATUS_DEFINE_INACTIVE = 0;
	public static final Integer STATUS_DEFINE_PENDING = -1;
	
	public static final int CUSTOMER_STATUS_ACTIVE_BIT = 1;
	public static final int CUSTOMER_STATUS_PENDING_BIT = 2;
	
	public static final int ACCEPT_PENGDING_MSG = 1;
	public static final int REJECT_PENDING_MSG = 2;
	
	public static final int BILL_AUTO_CHARGE_FLAG = 1;
	public static final int NON_BILL_AUTO_CHARGE_FLAG = 0;
	
	public static final String SuperAdminName = "sys";
}
