package org.water.billing.consts;

public class Consts {
	
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
	
	public static final String GCK_LATE_PAY_DAY = "late_pay_day";
	public static final String GCK_LATE_PAY_RATIO = "late_pay_ratio";
	public static final String GCK_DISABLE_APPROVE_CUSTOMER = "disable_approve_customer";
	public static final String GCK_DISABLE_APPROVE_CUSTOMER_WATER = "disable_approve_customer_water";
	public static final String GCK_DISABLE_APPROVE_CUSTOMER_BILL = "disable_approve_customer_bill";
	public static final String GCK_CUSTOMER_WATER_METER_USAGE = "customer_water_meter_usage";
	public static final String GCK_CUSTOMER_WATER_METER_STATUS = "customer_water_meter_status";
	public static final String GCK_CUSTOMER_READ_METER_CYCLE = "customer_read_status_cycle";
	public static final String GCK_CUSTOMER_CERTIFICATE_NAME = "customer_certificate_name";
	public static final String GCK_CHARGE_FROM_TYPE = "charge_from_type";
}
