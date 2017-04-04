package org.water.billing.consts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Consts {
	public static final List<String> CHARGE_FROM_TYPE = new ArrayList<String>() {
		private static final long serialVersionUID = -2145618765457370938L;
		{ add("录入");add("抄表");add("固定");add("其他"); }
	};
	
	public static final List<String> CUSTOMER_CERTIFICATE_TYPE = new ArrayList<String>() {
		private static final long serialVersionUID = -2409239467580519026L;
		{ add("身份证");add("军官证");add("驾驶证"); }
	};
	
	public static final Map<String,Integer> STATUS_DEFINE = new HashMap<String,Integer>() {
		private static final long serialVersionUID = -4714883658755117677L;
		{put("ACTIVE",1);put("INACTIVE",0);put("PENDING",-1);}
	};
}
