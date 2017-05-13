package org.water.billing.biz;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.water.billing.GlobalConfiguration;
import org.water.billing.consts.Consts;
import org.water.billing.entity.biz.Bill;

public class BillHelper {
	
	public static Map<String,Float> getPayInformation(List<Bill> bills) {
		Map<String,Float> res = new HashMap<String,Float>();
		if(bills.size() == 0)
			return res;
		Float unpaied = new Float(0);
		Float latePayment = new Float(0);

		for(int i=0;i<bills.size();i++) {
			unpaied += bills.get(i).getTotalPostage();
			latePayment += getLatePayment4Bill(bills.get(i));
		}
		
		res.put("unpaied", unpaied);
		res.put("latePayment", latePayment);
		return res;
	}
	
	public static Float getLatePayment4Bill(Bill bill) {
		int latePayDay = Integer.valueOf(GlobalConfiguration.getInstance().getConfigValueByItemName(Consts.GCK_LATE_PAY_DAY));
		Float latePayRatio = Float.valueOf(GlobalConfiguration.getInstance().getConfigValueByItemName(Consts.GCK_LATE_PAY_RATIO));
		Float billPostage = bill.getTotalPostage();
		Date now = new Date();
		Long days = (now.getTime() - bill.getInputDate().getTime())/(86400 * 1000);
		Float latePaymentValue = new Float(0);
		if(days > latePayDay) {
			latePaymentValue =  (days - latePayDay) * (billPostage * (latePayRatio/100));
		}
		return latePaymentValue;
	}
}
