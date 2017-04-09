package org.water.billing.controller.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.MyException;
import org.water.billing.annotation.OpAnnotation;
import org.water.billing.biz.BillGenerater;
import org.water.billing.consts.Consts;
import org.water.billing.entity.biz.Bill;
import org.water.billing.entity.biz.Customer;
import org.water.billing.entity.biz.CustomerWater;
import org.water.billing.service.biz.BillService;
import org.water.billing.service.biz.CustomerService;
import org.water.billing.service.biz.CustomerWaterService;

@Controller
public class ApproveController {

	@Autowired
	CustomerService customerService;
	
	@Autowired
	BillService billService;
	
	@Autowired
	CustomerWaterService customerWaterService;
	
	@RequestMapping(value = "/biz/approver",method=RequestMethod.GET)
	public String listPendingMsg(ModelMap model) {
		List<Customer> allPendingCustomers = customerService.findAllPendingCustomer();
		List<Customer> allCustomersWhichHaveNewWaterNumber = customerService.findAllCustomersWhichHaveNewBill();
		List<Bill> allPendingBill = billService.findAllPendingBill();
		model.addAttribute("allPendingCustomers", allPendingCustomers);
		model.addAttribute("allCustomersWhichHaveNewWaterNumber", allCustomersWhichHaveNewWaterNumber);
		model.addAttribute("allPendingBill",allPendingBill);
		return "/biz/approver";
	}
	
	@OpAnnotation(moduleName="业务审核",option="客户销户或开户")
	@RequestMapping(value = "/biz/approver/customer",method=RequestMethod.GET)
	public String approveCustomer(@RequestParam int id,@RequestParam int action) throws MyException {
		Customer customer = customerService.findById(id);
		if(customer == null)
			throw new MyException("客户不存在");
		
		int status = customer.getStatus();
		if(action == Consts.REJECT_PENDING_MSG)
			status = ~status; //if reject,need set status back to org, so make sure can't activate a active customer in CustomerController.java
		status &= Consts.CUSTOMER_STATUS_ACTIVE_BIT; //set pending bit to 0
		customer.setStatus(status);
		customerService.save(customer);
		return "/biz/approver";
	}
	
	@OpAnnotation(moduleName="业务审核",option="客户用水量")
	@RequestMapping(value = "/biz/approver/customer_water",method=RequestMethod.GET)
	public String approveCustomerBill(@RequestParam int id,@RequestParam int action) throws MyException {
		Customer customer = customerService.findById(id);
		if(customer == null)
			throw new MyException("客户不存在");
		
		CustomerWater customerWater = customer.getCustomerWater();
		if(customerWater.getNewNumber() == new Float(0))
			throw new MyException("该业务已经处理");
		
		if(action == Consts.ACCEPT_PENGDING_MSG) {
			BillGenerater billGenerater = new BillGenerater(customer);
			Bill bill = billGenerater.genBill();
			billService.save(bill);
			
			customerWater.setOrgNumber(customerWater.getNewNumber());
		}
		customerWater.setNewNumber(new Float(0));
		customerWaterService.save(customerWater);
		return "/biz/approver";
	}
}
