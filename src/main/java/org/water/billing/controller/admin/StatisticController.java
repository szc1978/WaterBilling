package org.water.billing.controller.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.entity.biz.Bill;
import org.water.billing.entity.biz.CustomerPayHistory;
import org.water.billing.service.biz.BillService;
import org.water.billing.service.biz.CustomerPayHistoryService;

@Controller
public class StatisticController {
	
	@Autowired
	BillService billService;
	
	@Autowired
	CustomerPayHistoryService payHistoryService;
	
	@RequestMapping(value="/admin/statistic",method={RequestMethod.POST,RequestMethod.GET})
	public String statistic(@RequestParam(defaultValue="0") String year,@RequestParam(defaultValue="0") String month,ModelMap model) {
		Date from = getFromDate(year,month);
		Date to = getToDate(year,month);
		
		Float totalWaterNumber = getTotalWaterNumber(from,to);
		Float totalMoney = getTotalPaiedMoney(from,to);
		
		List<Bill> bills = billService.findChargedBills4Statistic(from,to);
		BillStatistic stat = new BillStatistic(bills);
		
	    model.addAttribute("totalWaterNumber", totalWaterNumber);
	    model.addAttribute("totalMoney", totalMoney);
	    model.addAttribute("chargeitems", stat.getItemContent());
	    model.addAttribute("totalNeedPay", stat.getTotalNeedPay());
	    model.addAttribute("totalReduce", stat.getTotalReduce());
	    model.addAttribute("totalPaied", stat.getTotalPaied());
	    
		return "/admin/statistic";
	}
	
	private Float getTotalWaterNumber(Date fromDate,Date toDate) {
		Float totalWaterNumber = new Float(0);
		List<Bill> bills = billService.findNewBills4Statistic(fromDate, toDate);
		for(Bill bill : bills) {
	    	totalWaterNumber += (bill.getEndWaterWord() - bill.getBeginWaterWord());
		}
		return totalWaterNumber;
	}
	
	private Float getTotalPaiedMoney(Date fromDate,Date toDate) {
		Float totalMoney = new Float(0);
		List<CustomerPayHistory> allhistory = payHistoryService.findHistory4Statistic(fromDate,toDate);
		for(CustomerPayHistory history : allhistory) {
			totalMoney += history.getPayNumber();
		}
		return totalMoney;
	}
	
	private Date getFromDate(String year,String month) {
		String dateString = "";
		if("0".equals(month))
			dateString = year + "-01-01 00:00:00";
		else
			dateString = year + "-" + month + "-01 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date from = null;
		try {
			from = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return from;
	}
	
	private Date getToDate(String year,String month) {
		Integer y = Integer.valueOf(year);
		Integer m = Integer.valueOf(month);
		String dateString = "";
		if(m < 12)
			dateString = y + "-" + (m + 1) + "-01 00:00:00";
		if(m == 0 || m == 12)
			dateString = (y + 1) + "-01-01 00:00:00";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date to = null;
		try {
			to = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return to;
	}
}

 class BillStatistic {
	 Float totalNeedPay = new Float(0);
	 Float totalReduce = new Float(0);
	 Float totalPaied = new Float(0);
	 Map<String,Map<String,Object>> items = new HashMap<String,Map<String,Object>>();
	 
	 public BillStatistic(List<Bill> bills) {
		 for(Bill bill : bills) {
		    String reduceString = bill.getReduceContent();
		    for(String s : reduceString.split(";")) {
		    	String[] ss = s.split(":");
		    	if(ss.length != 2)
		    		continue;
		    	String chargeName = ss[0];
		    	String[] sss = ss[1].split(",");
		    	if(sss.length != 2)
		    		continue;
		    	Float needPay = Float.valueOf(sss[0]);
		    	Float reduce = Float.valueOf(sss[1]);
		    	if(!items.containsKey(chargeName)) {
		    		Map<String,Object> item = new HashMap<String,Object>();
		    		item.put("chargename", chargeName);
		    		item.put("needpay", new Float(0));
		    		item.put("reduce", new Float(0));
		    		item.put("paied", new Float(0));
		    		items.put(chargeName, item);
		    	}
		    	Map<String,Object> item = items.get(chargeName);
		    	item.put("needpay",(Float)item.get("needpay") + needPay);
		    	item.put("reduce",(Float)item.get("reduce") + reduce);
		    	item.put("paied",(Float)item.get("paied") + needPay - reduce);
		    	totalNeedPay += needPay;
		    	totalReduce += reduce;
		    	totalPaied += needPay - reduce;
		    }
		}
	 }
	 
	 public Float getTotalNeedPay() {
		 return totalNeedPay;
	 }
	 
	 public Float getTotalReduce() {
		 return totalReduce;
	 }
	 
	 public Float getTotalPaied() {
		 return totalPaied;
	 }
	 
	 public Collection<Map<String, Object>> getItemContent() {
		 return items.values();
	 }
 }
