package org.water.billing.controller.biz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.water.billing.biz.BillGenerater;
import org.water.billing.biz.BillHelper;
import org.water.billing.consts.ChargeTypeEnum;
import org.water.billing.consts.Consts;
import org.water.billing.entity.biz.Bill;
import org.water.billing.entity.biz.Charge;
import org.water.billing.entity.biz.Customer;
import org.water.billing.entity.biz.CustomerWaterMeter;
import org.water.billing.service.biz.BillService;
import org.water.billing.service.biz.ChargeService;
import org.water.billing.service.biz.CustomerService;
import org.water.billing.service.biz.CustomerWaterMeterService;

@Controller
public class PayController {
	
	@Autowired
	ChargeService chargeService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CustomerWaterMeterService customerWaterMeterService;
	
	@Autowired
	BillService billService;
	
	@RequestMapping(value="/pay/listunpaiedbill",method=RequestMethod.GET)
	public String listUnpaiedBill(@RequestParam(required=false) String code,ModelMap map) {
		List<Bill> bills = billService.findUnchargedBill(code);
		map.addAttribute("bills", bills);
		map.addAttribute("customerCode", code);
		return "/pay/unpaied_bill";
	}
	
	@RequestMapping(value="/pay/onebill",method=RequestMethod.GET)
	public String payOneBillForm(@RequestParam int id,ModelMap map) throws MyException {
		Bill bill = billService.findById(id);
		if(bill == null || bill.getIsCharged() == 1)
			throw new MyException("账单不存在或者账单已经缴费");
		Customer customer = customerService.findByCode(bill.getCustomerCode());
		if(customer == null)
			throw new MyException("客户不存在");
		map.addAttribute("bill", bill);
		map.addAttribute("customer", customer);
		Float latePaymentValue = BillHelper.getLatePayment4Bill(bill);
		map.addAttribute("late_payment", latePaymentValue);
		List<Map<String,String>> chargeDetailedList = getDetailCharge4Bill(bill);
		map.addAttribute("chargeDetailedList",chargeDetailedList);
		return "/pay/pay_one_bill_form";
	}
	
	@OpAnnotation(moduleName="客户缴费",option="缴纳水费")
	@RequestMapping(value="/pay/onebill",method=RequestMethod.POST)
	public String payOneBillForm(HttpServletRequest request) throws MyException {
		String customerCode = request.getParameter("customerCode");
		Customer customer = customerService.findByCode(customerCode);
		int billId = Integer.valueOf(request.getParameter("bill_id"));
		Bill bill = billService.findById(billId);
		if(customer == null)
			throw new MyException("客户不存在");
		if(bill == null)
			throw new MyException("客户账单不存在");
		//cal reduce items
		Float thisPay = Float.valueOf(request.getParameter("payment"));
		String reduceContent = "";
		Float reduce = new Float(0);
		for(int i=0;;i++) {
			String chargeName = request.getParameter("reduce_charge_name_" + i);
			String orgValue = request.getParameter("reduce_charge_org_value_" + i);
			String reduceValue = request.getParameter("reduce_charge_reduce_value_" + i);
			if(chargeName != null && orgValue != null && reduceValue != null) {
				reduceContent += chargeName + ":" + orgValue + "," + reduceValue + ";";
			} else {
				break;
			}
			if(Float.valueOf(reduceValue) > Float.valueOf(orgValue))
				throw new MyException(chargeName + "的减免额度高于原始费用");
			reduce += Float.valueOf(reduceValue);
		}
		
		String reduceLatePaymentOrgValue = request.getParameter("reduce_late_payment_org_value");
		String reduceLatePaymentValue = request.getParameter("reduce_late_payment_value");
		if(Float.valueOf(reduceLatePaymentValue) > Float.valueOf(reduceLatePaymentOrgValue))
			throw new MyException("滞纳金的减免额度高于原始费用");
		reduceContent += "滞纳金:" + reduceLatePaymentOrgValue + "," + reduceLatePaymentValue;
		reduce += Float.valueOf(reduceLatePaymentValue);
		
		Float needPay = bill.getTotalPostage() - reduce;
		if(customer.getBalance() + thisPay < needPay)
			throw new MyException("缴费金额不足");
		
		customerPayOnly(customer,thisPay);
		doPay4OneBill( bill, needPay, reduceContent);
		
		return "redirect:/pay/billdetail?id=" + bill.getId();
	}
	
	@RequestMapping(value = "/pay/allbills",method=RequestMethod.GET)
	public String payAllBillsForm(@RequestParam String code, ModelMap map) throws Exception {
		Customer customer = customerService.findByCode(code);
		if(customer == null )
			throw new MyException("用户不存在");
		
		List<Bill> bills = billService.findUnchargedBill(code);
		Map<String,Float> payInformation = BillHelper.getPayInformation(bills);
		
		Float unpaied = payInformation.get("unpaied") == null ? new Float(0) : payInformation.get("unpaied");
		Float latePayment = payInformation.get("latePayment") == null ? new Float(0) : payInformation.get("latePayment");
		
		if(unpaied == 0)
			throw new MyException("用户不欠费");
		
		map.addAttribute("customer", customer);
		map.addAttribute("un_paied",unpaied);
		map.addAttribute("late_payment",latePayment);
	
		return "/pay/pay_all_bills_form";
	}
	
	@OpAnnotation(moduleName="客户缴费",option="统一缴费")
	@RequestMapping(value = "/pay/allbills",method=RequestMethod.POST)
	public String payAllBills(HttpServletRequest request,ModelMap model) throws MyException {
		String customerCode = request.getParameter("customerCode");
		String payment = request.getParameter("payment");
		Float thisPay = Float.valueOf(payment);
		
		Customer customer = customerService.findByCode(customerCode);
		if(customer == null )
			throw new MyException("用户不存在");
		
		List<Bill> bills = billService.findUnchargedBill(customerCode);
		Map<String,Float> payInformation = BillHelper.getPayInformation(bills);
		Float unpaied = payInformation.get("unpaied") == null ? new Float(0) : payInformation.get("unpaied");
		Float latePayment = payInformation.get("latePayment") == null ? new Float(0) : payInformation.get("latePayment");
		
		if((customer.getBalance() + thisPay) < (unpaied + latePayment))
			throw new MyException("费用不够，请检查");
		
		customerPayOnly(customer,thisPay);
		
		for(Bill bill : bills) {
			Float thisLatePayment = BillHelper.getLatePayment4Bill(bill);
			Float needPay = bill.getTotalPostage() + thisLatePayment;
			doPay4OneBill(bill,needPay,null);
		}

		model.addAttribute("msg", "统一缴费成功，如果需要打印账单，请前往搜索");
		return "/msg";
	}
	
	private Bill doPay4OneBill(Bill bill,Float needPay,String reduceContent) throws MyException {
		String customerCode = bill.getCustomerCode();
		Customer customer = customerService.findByCode(customerCode);

		customer = customerService.pay(customer, needPay);
		bill = billService.payBill(bill, needPay, customer.getBalance(), reduceContent);

		return bill;
	}
	
	private void customerPayOnly(Customer customer,Float money) {
		billService.customerPayOnlyBill(customer, money);
		customerService.pay(customer, money * -1);
	}
	
	@RequestMapping(value="/pay/biz",method=RequestMethod.GET) 
	public String payBiz(@RequestParam(required=false) String code,ModelMap model) throws MyException{
		Customer customer = customerService.findByCode(code);
		if(customer == null && code != null)
			throw new MyException("用户不存在");
		
		if(customer == null && code == null)
			customer = new Customer();
		
		model.addAttribute("customer", customer);
		
		List<Charge> charges = new ArrayList<Charge>();
		for(Charge charge : chargeService.findAll()) {
			if(charge.getChargeType() == ChargeTypeEnum.CHARGE_BY_SPECIAL.getId()) {
				charges.add(charge);
			}
		}
		model.addAttribute("charges", charges);
		return "/pay/business";
	}
	
	@OpAnnotation(moduleName="客户缴费",option="业务缴费")
	@RequestMapping(value = "/pay/biz",method=RequestMethod.POST)
	public String payBiz(HttpServletRequest request) throws Exception {
		String customerCode = request.getParameter("customerCode");
		Customer customer = customerService.findByCode(customerCode);
		if(customer == null)
			throw new MyException("客户不存在");
		
		String payment = request.getParameter("payment");
		Float thisPay = Float.valueOf(payment);
		
		String reduceContent = "";
		List<Charge> charges = new ArrayList<Charge>();
		for(String key : request.getParameterMap().keySet()) {
			if(key.startsWith("Charge_")) {
				String chargeName = key.replace("Charge_", "");
				Charge charge = chargeService.findByName(chargeName);
				if(charge != null) {
					charges.add(charge);
					reduceContent += chargeName + ":" + charge.getChargeParameter().getSpecialItemPrice() + ",0;";
				}
			}
		}
		BillGenerater billGenerater = new BillGenerater(customer,Consts.BILL_TYPE_BIZ);
		Bill bill = billGenerater.genBill4DedivatedCharge("业务缴费", charges);
		bill.setBillType(Consts.BILL_TYPE_BIZ);
		
		if(customer.getBalance() + thisPay < bill.getTotalPostage())
			throw new MyException("缴费金额不足");
		customerPayOnly(customer,thisPay);
		bill = doPay4OneBill(bill,bill.getTotalPostage(),reduceContent);

		return "redirect:/pay/billdetail?id=" + bill.getId();
	}
	
	@OpAnnotation(moduleName="客户缴费",option="撤销缴费")
	@RequestMapping(value = "/pay/pay_rollback",method=RequestMethod.GET)
	public String payRollback(@RequestParam int id,ModelMap model) throws Exception {
		Bill bill = billService.findById(id);
		if(bill == null)
			throw new MyException("账单不存在");
		if(bill.getIsPrintExpenses() == 1)
			throw new MyException("该账单已经打印过发票，无法撤销");
		
		Customer customer = customerService.findByCode(bill.getCustomerCode());
		customerService.pay(customer, bill.getPaied() * -1);
		if(bill.getBillType() == Consts.BILL_TYPE_WATER) {
			billService.rollbackPay(bill, customer.getBalance() + bill.getPaied());
		}
		if(bill.getBillType() == Consts.BILL_TYPE_BIZ)
			billService.delete(bill);

		model.addAttribute("msg", "该账单缴费撤销成功！");
		return "/msg";
	}
	
	@RequestMapping(value="/pay/billdetail",method=RequestMethod.GET)
	public String paySuccess(int id,ModelMap model) throws MyException {
		Bill bill = billService.findById(id);
		if(bill == null)
			throw new MyException("账单不存在");
		
		Float total = new Float(0);
		List<Map<String,String>> payItems = new ArrayList<Map<String,String>>();
		for(String s : bill.getDetailContent().split(";")) {
			if(s.length() == 0)
				continue;
			String[] ss = s.split(":");
			if(ss.length != 2)
				continue;
			Map<String,String> sss = new HashMap<String,String>();
			sss.put("name", ss[0]);
			sss.put("price", ss[1]);
			payItems.add(sss);
			total += Float.valueOf(ss[1]);
		}
		model.addAttribute("payitems", payItems);
		model.addAttribute("bill", bill);
		model.addAttribute("total", total);
		return "/pay/pay_bill_detail";
	}
	
	@RequestMapping(value="/pay/history",method=RequestMethod.GET)
	public String payHistory(HttpServletRequest request,ModelMap model) throws MyException {
		String customerCode = request.getParameter("customerCode");
		String fromToDate = request.getParameter("from_to_date");
		if(customerCode != null && fromToDate != null && fromToDate.contains("-")) {
			String[] tmp = fromToDate.split(" - ");
			String strFromDate = tmp[0];
			String strToDate = tmp[1];
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date fromDate = sdf.parse(strFromDate);
				Date toDate = sdf.parse(strToDate);
				List<Bill> bills = billService.findCustomerChargedBill(customerCode,fromDate,toDate);
				model.addAttribute("bills", bills);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("code", customerCode==null?"" : customerCode);
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
	public String autoPay(@RequestParam int id) throws Exception {
		Bill bill = billService.findById(id);
		doPay4OneBill(bill,new Float(0),"");
		return "redirect:/approve/customerbill/list";
	}
	
	@OpAnnotation(moduleName="客户缴费",option="打印账单发票")
	@RequestMapping(value = "/pay/print_expenses",method=RequestMethod.GET)
	public String printExpenses(@RequestParam int id,ModelMap model) throws Exception {
		Bill bill = billService.findById(id);
		if(bill.getIsCharged() == 0)
			throw new MyException("该账单还未缴费！！！");
		if(bill.getIsPrintExpenses() == 1)
			throw new MyException("该账单已经打印！！！");
		bill.setIsPrintExpenses(1);
		billService.save(bill);
		model.addAttribute("msg", "该账单发票已打印，将无法再打印");
		return "/msg";
	}
	
	@RequestMapping(value="/customer/presspayment",method=RequestMethod.GET)
	public String pressPayment(ModelMap model) {
		List<Bill> bills = billService.findAllUnchargedBill();
		Map<String,Map<String,Object>> ss = new HashMap<String,Map<String,Object>>();
		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
		String time = formatter.format(now);
		for(Bill bill : bills) {
			String customerCode = bill.getCustomerCode();
			Customer customer = customerService.findByCode(customerCode);
			String meterBodyNumber = bill.getWaterMeterBodyNumber();
			CustomerWaterMeter meter = customerWaterMeterService.findByMeterBodyNumber(meterBodyNumber);
			if(!ss.containsKey(meterBodyNumber)) {
				Map<String,Object> item = new HashMap<String,Object>();
				item.put("unpaied", new Float(0));
				item.put("latepayment", new Float(0));
				item.put("waternumber",new Float(0));
				item.put("customercode",customerCode);
				item.put("customername",customer.getName());
				item.put("customeraddress",customer.getCustomerInfo().getAddress());
				item.put("meterbodynumber",meter.getBodyNumber());
				ss.put(meterBodyNumber, item);
			}
			Map<String,Object> item = ss.get(meterBodyNumber);
			Float unpaied = bill.getTotalPostage() + (Float)item.get("unpaied");
			Float latepayment = BillHelper.getLatePayment4Bill(bill) + (Float)item.get("latepayment");
			Float waternumber = bill.getEndWaterWord() - bill.getBeginWaterWord() + (Float)item.get("waternumber");
			item.put("unpaied",unpaied);
			item.put("latepayment",latepayment);
			item.put("waternumber", waternumber);
			item.put("msg",customerCode + "(" + meterBodyNumber + ")用户，截止" 
							+ time + " 时间，你已累计用水 " 
							+ waternumber + "吨，合计"
							+ unpaied +"元，滞纳金"
							+ latepayment +"元，合计"
							+ (unpaied + latepayment) +"元，请尽快缴费");
		}
		model.addAttribute("bill_message", ss.values());
		return "/customer/press_payment";
	}
	
	/*private Map<String,Object> getPayInformation(List<Bill> bills) {
		Map<String,Object> res = new HashMap<String,Object>();
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
	
	private Float getLatePayment4Bill(Bill bill) {
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
	}*/
	
	private List<Map<String,String>> getDetailCharge4Bill(Bill bill) {
		List<Map<String,String>> res = new ArrayList<Map<String,String>>();
		String detailedContent = bill.getDetailContent();
		if(detailedContent == null)
			return res;
		for(String s : detailedContent.split(";")) {
			String[] ss = s.split(":");
			if(ss.length != 2)
				continue;
			Map<String,String> item = new HashMap<String,String>();
			item.put("charge_name", ss[0]);
			item.put("charge_value", ss[1]);
			res.add(item);
		}
		return res;
	}
}
