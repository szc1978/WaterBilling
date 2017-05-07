package org.water.billing.controller.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.entity.biz.Bill;
import org.water.billing.service.biz.BillService;

@Controller
public class StatisticController {
	
	@Autowired
	BillService billService;

	@RequestMapping(value="/admin/statistic",method=RequestMethod.GET)
	public String statistic(ModelMap model) {
		Calendar cal= Calendar.getInstance();
		Integer thisyear = cal.get(Calendar.YEAR);
		List<Integer> years = new ArrayList<Integer>();
		years.add(thisyear);years.add(thisyear - 1);
		model.addAttribute("years", years);
		return "/admin/statistic";
	}
	
	@RequestMapping(value="/admin/statistic",method=RequestMethod.POST)
	public String statistic(@RequestParam String year,@RequestParam String month,ModelMap model) {
		Calendar cal= Calendar.getInstance();
		Integer thisyear = cal.get(Calendar.YEAR);
		List<Integer> years = new ArrayList<Integer>();
		years.add(thisyear);years.add(thisyear - 1);
		model.addAttribute("years", years);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Float totalWaterNumber = new Float(0);
	    Float totalMoney = new Float(0);
	    try {
	    	Date from = sdf.parse(year + "-" + month + "-01 00:00:00");
			cal.set(Calendar.YEAR, Integer.parseInt(year));
			cal.set(Calendar.DATE, 1);
			cal.add(Calendar.MONTH, 1); 
			Date to = cal.getTime();
			List<Bill> bills = billService.findBills4Statistic(from, to);
			for(Bill bill : bills) {
		    	totalWaterNumber += (bill.getEndWaterWord() - bill.getBeginWaterWord());
		    	totalMoney += bill.getPaied();
		    }
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    model.addAttribute("totalWaterNumber", totalWaterNumber);
	    model.addAttribute("totalMoney", totalMoney);
	    
		return "/admin/statistic";
	}
}
