package org.water.billing.controller.biz;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.MyException;
import org.water.billing.annotation.OpAnnotation;
import org.water.billing.consts.Consts;
import org.water.billing.consts.ReadMeterCycleEnum;
import org.water.billing.entity.biz.Customer;
import org.water.billing.entity.biz.CustomerType;
import org.water.billing.entity.biz.WaterProvider;
import org.water.billing.service.biz.CustomerService;
import org.water.billing.service.biz.CustomerTypeService;
import org.water.billing.service.biz.WaterProviderService;
import org.water.billing.utils.Utils;

@Controller
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CustomerTypeService customerTypeService;
	
	@Autowired
	WaterProviderService waterProviderService;

	@RequestMapping(value="/customer/manage/list",method=RequestMethod.GET)
	public String customer(@RequestParam(defaultValue="1") int page,
							@RequestParam(defaultValue="10")  int size,
							ModelMap map) {
		page = page < 1?1:page;
		Page<Customer> pageInfo = customerService.findAll(page-1,size);
		map.addAttribute("pageUrl", "/customer/manage/list");
		map.addAttribute("customers",pageInfo.getContent());
		Utils.setPageInfo4ModelMap(pageInfo, map);
		return "/customer/customer_list";
	}
	
	@OpAnnotation(moduleName="客户管理",option="增加修改客户")
	@RequestMapping(value="/customer/manage",method=RequestMethod.POST)
	public String customer(@ModelAttribute Customer customer) {
		Customer tmp = customerService.save(customer);
		return "redirect:/customer/manage/list/" + tmp.getId();
	}
	
	@OpAnnotation(moduleName="客户管理",option="客户销户")
	@RequestMapping(value="/customer/manage/deactivate",method=RequestMethod.GET)
	public String activate(@RequestParam int id) throws Exception {
		Customer customer = customerService.findById(id);
		if(customer == null)
			throw new MyException("客户不存在");
		if((customer.getStatus() & Consts.CUSTOMER_STATUS_PENDING_BIT) == 2)
			throw new MyException("客户正处于等待审核状态");
		if((customer.getStatus() & Consts.CUSTOMER_STATUS_ACTIVE_BIT) == 0)
			throw new MyException("客户已经处于销户状态");
		customer.setStatus(Consts.CUSTOMER_STATUS_PENDING_BIT);
		Customer res = customerService.save(customer);
		return "redirect:/customer/manage/list/" + res.getId();
	}
	
	@OpAnnotation(moduleName="客户管理",option="客户开户")
	@RequestMapping(value="/customer/manage/activate",method=RequestMethod.GET)
	public String customer(@RequestParam int id) throws Exception {
		Customer customer = customerService.findById(id);
		if(customer == null)
			throw new MyException("客户不存在");
		if((customer.getStatus() & Consts.CUSTOMER_STATUS_PENDING_BIT) == 2)
			throw new MyException("客户正处于等待审核状态");
		if((customer.getStatus() & Consts.CUSTOMER_STATUS_ACTIVE_BIT) == 1)
			throw new MyException("客户已经处于开户状态");
		customer.setStatus(Consts.CUSTOMER_STATUS_ACTIVE_BIT | Consts.CUSTOMER_STATUS_PENDING_BIT);
		Customer res = customerService.save(customer);
		return "redirect:/customer/manage/list/" + res.getId();
	}
	
	@RequestMapping(value="/customer/manage/form",method=RequestMethod.GET)
	public String customerForm(@RequestParam(defaultValue="0") int id,ModelMap map) {
		Customer customer = customerService.findById(id);
		if(customer == null)
			customer = new Customer();
		map.addAttribute("customer", customer);
		map.addAttribute("certificateNames",Consts.CUSTOMER_CERTIFICATE_TYPE);
		List<CustomerType> customerTypes = customerTypeService.findAll();
		map.addAttribute("customerTypes",customerTypes);
		List<WaterProvider> waterProviders = waterProviderService.findAll();
		map.addAttribute("waterProviders",waterProviders);
		map.addAttribute("readMeterCycles",getReadMeterCycleList());
		return "/customer/customer_form";
	}

	@RequestMapping(value="/customer/manage/list/{id}",method=RequestMethod.GET)
	public String showCustomer(@PathVariable int id,ModelMap model) {
		Customer customer = customerService.findById(id);
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(customer);
		model.addAttribute("customers",customers);
		Utils.setPageInfo4ModelMap(null, model);
		return "/customer/customer_list";
	}
	
	@RequestMapping(value = "/approve/customer/list",method=RequestMethod.GET)
	public String listPendingMsg(ModelMap model) {
		List<Customer> allPendingCustomers = customerService.findAllPendingCustomer();
		model.addAttribute("allPendingCustomers", allPendingCustomers);
		return "/approve/customer";
	}
	
	@OpAnnotation(moduleName="业务审核",option="客户销户或开户")
	@RequestMapping(value = "/approve/customer",method=RequestMethod.GET)
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
		return "redirect:/approve/customer/list";
	}
	
	private List<String> getReadMeterCycleList() {
		List<String> readMeterCycles = new ArrayList<String>();
		for(ReadMeterCycleEnum cycle : ReadMeterCycleEnum.values())
			readMeterCycles.add(cycle.getName());
		return readMeterCycles;
	}
}
