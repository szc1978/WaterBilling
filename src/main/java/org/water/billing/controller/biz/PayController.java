package org.water.billing.controller.biz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.MyException;
import org.water.billing.annotation.OpAnnotation;
import org.water.billing.consts.Consts;
import org.water.billing.entity.biz.Bill;
import org.water.billing.entity.biz.Customer;
import org.water.billing.service.biz.BillService;
import org.water.billing.service.biz.ChargeService;
import org.water.billing.service.biz.CustomerService;

@Controller
public class PayController {
	
	@Autowired
	ChargeService chargeService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	BillService billService;
	
	@RequestMapping(value = "/pay/water",method=RequestMethod.GET)
	public String payForm(@RequestParam(required=false) String code,
							ModelMap map) throws Exception {
		Customer customer = customerService.findByCode(code);
		if(customer == null && code != null)
			throw new MyException("用户不存在");
		
		if(customer == null && code == null)
			customer = new Customer();
		
		Map<String,Object> payInformation = getPayInformation(code);
		
		Float unpaied = payInformation.get("unpaied") == null ? new Float(0) : (Float) payInformation.get("unpaied");
		Float latePayment = payInformation.get("latePayment") == null ? new Float(0) : (Float) payInformation.get("latePayment");
		Bill latestBill = payInformation.get("latestBill") == null ? new Bill() : (Bill) payInformation.get("latestBill");
		
		map.addAttribute("customer_name", customer.getName());
		map.addAttribute("customer_code", customer.getCustomerInfo().getCode());
		map.addAttribute("customer_address", customer.getCustomerInfo().getAddress());
		map.addAttribute("customer_balance", customer.getBalance());
		map.addAttribute("bill",latestBill);
		map.addAttribute("un_paied",unpaied);
		map.addAttribute("late_payment",latePayment);
		return "/pay/water";
	}
	
	@RequestMapping(value="/pay/biz",method=RequestMethod.GET) 
	public String payBiz(){
		return "/pay/biz";
	}
	
	@OpAnnotation(moduleName="客户缴费",option="客户缴费")
	@RequestMapping(value = "/pay/water",method=RequestMethod.POST)
	public String pay(HttpServletRequest request) throws Exception {
		String customerCode = request.getParameter("customerCode");
		String payment = request.getParameter("payment");
		Float thisPay = Float.valueOf(payment);
		
		doPay(customerCode,thisPay);

		return "redirect:/pay/water?code=" + customerCode;
	}
	
	@RequestMapping(value="/pay/history",method=RequestMethod.GET)
	public String payHistory(HttpServletRequest request,ModelMap model) {
		String customerCode = request.getParameter("customerCode");
		String strFromDate = request.getParameter("fromDate");
		String strToDate = request.getParameter("toDate");
		if(customerCode != null && strFromDate != null && strToDate != null) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date fromDate = sdf.parse(strFromDate);
				Date toDate = sdf.parse(strToDate);
				List<Bill> bills = billService.findCustomerBill(customerCode,fromDate,toDate);
				model.addAttribute("bills", bills);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return "/pay/history";
	}
	
	@RequestMapping(value = "/approve/customerbill/list",method=RequestMethod.GET)
	public String approveCustomerBill(ModelMap model) {
		List<Bill> allPendingBill = billService.findAllPendingBill();
		model.addAttribute("allPendingBill",allPendingBill);
		return "/approve/customerbill";
	}
	
	@OpAnnotation(moduleName="客户缴费",option="自动销账")
	@RequestMapping(value = "/approve/customerbill",method=RequestMethod.GET)
	public String autoPay(@RequestParam String customerCode) throws Exception {
		doPay(customerCode,new Float(0));
		return "/approve/customerbill";
	}
	
	private void doPay(String customerCode,Float thisPay) throws MyException {
		Map<String,Object> payInformation = getPayInformation(customerCode);
		Float unpaied = payInformation.get("unpaied") == null ? new Float(0) : (Float) payInformation.get("unpaied");
		Float latePayment = payInformation.get("latePayment") == null ? new Float(0) : (Float) payInformation.get("latePayment");
		Bill latestBill = payInformation.get("latestBill") == null ? new Bill() : (Bill) payInformation.get("latestBill");
		
		
		Customer customer = customerService.findByCode(customerCode);
		if((customer.getBalance() + thisPay) < (unpaied + latePayment + latestBill.getTotalPostage()))
				throw new MyException("费用不够，请检查");
		
		List<Bill> bills = billService.findUnchargedBill(customerCode);
		for(Bill bill : bills) {
			bill.setAutoChargeFlag(Consts.NON_BILL_AUTO_CHARGE_FLAG);
			bill.setIsCharged(1);
			bill.setChargeDate(new Date());
			billService.save(bill);
		}
		Float newBalance = customer.getBalance() + thisPay - (unpaied + latePayment + latestBill.getTotalPostage());
		customer.setBalance(newBalance);
		customerService.save(customer);
	}
	
	private Map<String,Object> getPayInformation(String customerCode) {
		Map<String,Object> res = new HashMap<String,Object>();
		List<Bill> bills = billService.findUnchargedBill(customerCode);
		if(bills.size() == 0)
			return res;
		Float unpaied = new Float(0);
		Float latePayment = new Float(0);
		Bill latestBill = bills.get(0);
		Date now = new Date();
		for(int i=1;i<bills.size();i++) {
			unpaied += bills.get(i).getTotalPostage();
			Date inputDate = bills.get(i).getInputDate();
			Long days = (now.getTime() - inputDate.getTime())/(86400 * 1000);
			if(days > 30) {
				Float tmp =  (days - 30) * (bills.get(i).getTotalPostage() * new Float(0.005));
				latePayment += tmp;
			}
		}
		
		res.put("unpaied", unpaied);
		res.put("latePayment", latePayment);
		res.put("latestBill", latestBill);
		return res;
	}
}
