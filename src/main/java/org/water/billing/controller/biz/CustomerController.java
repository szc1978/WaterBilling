package org.water.billing.controller.biz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.water.billing.GlobalConfiguration;
import org.water.billing.MyException;
import org.water.billing.annotation.OpAnnotation;
import org.water.billing.consts.Consts;
import org.water.billing.entity.biz.Bill;
import org.water.billing.entity.biz.Customer;
import org.water.billing.entity.biz.CustomerType;
import org.water.billing.entity.biz.CustomerWaterMeter;
import org.water.billing.entity.biz.WaterMeterMaintainHistory;
import org.water.billing.entity.biz.WaterMeterType;
import org.water.billing.entity.biz.WaterProvider;
import org.water.billing.service.biz.BillService;
import org.water.billing.service.biz.CustomerService;
import org.water.billing.service.biz.CustomerTypeService;
import org.water.billing.service.biz.CustomerWaterMeterService;
import org.water.billing.service.biz.WaterMeterMaintainHistoryService;
import org.water.billing.service.biz.WaterMeterTypeService;
import org.water.billing.service.biz.WaterProviderService;
import org.water.billing.utils.ExcelData;
import org.water.billing.utils.ExcelHelper;
import org.water.billing.utils.Utils;

@Controller
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CustomerTypeService customerTypeService;
	
	@Autowired
	WaterProviderService waterProviderService;
	
	@Autowired
	WaterMeterTypeService waterMeterTypeService;
	
	@Autowired
	CustomerWaterMeterService customerWaterMeterService;
	
	@Autowired
	BillService billService;
	
	@Autowired
	WaterMeterMaintainHistoryService waterMeterMaintainHistoryService;

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
	public String customer(@ModelAttribute Customer customer) throws MyException {
		if(customer.getId() == 0 && customerService.existsByCustomerCode(customer.getCustomerInfo().getCode()))
			throw new MyException("增加的新客户编号已经存在");
		Customer customerInDB = customerService.findById(customer.getId());
		if(customerInDB != null)
			customer.setMeters(customerInDB.getMeters());
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
		
		List<Bill> unchangedBills = billService.findUnchargedBill(customer.getCustomerInfo().getCode());
		if(unchangedBills.size() != 0)
			throw new MyException("客户还在欠费中，无法销户！");
		
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
		map.addAttribute("certificateNames",GlobalConfiguration.getInstance().getConfigValueList(Consts.GCK_CUSTOMER_CERTIFICATE_NAME));
		
		map.addAttribute("readMeterCycles",GlobalConfiguration.getInstance().getConfigValueList(Consts.GCK_CUSTOMER_READ_METER_CYCLE));
		List<CustomerType> customerTypes = customerTypeService.findAll();
		map.addAttribute("customerTypes",customerTypes);
		List<WaterProvider> waterProviders = waterProviderService.findAll();
		map.addAttribute("waterProviders",waterProviders);
		
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
	
	@RequestMapping(value="/customer/manage/watermeter",method=RequestMethod.GET)
	public String listCustomerWaterMeter(@RequestParam int id,ModelMap model) throws MyException {
		List<CustomerWaterMeter> meters = customerWaterMeterService.findCustomerMeters(id);
		model.addAttribute("meters", meters);
		model.addAttribute("customer_id", id);
		return "/customer/customer_water_meter";
	}
	
	@OpAnnotation(moduleName="客户水表操作",option="水表编辑检修更换")
	@RequestMapping(value="/customer/manage/watermeter",method=RequestMethod.POST)
	public String customerWaterMeter(@RequestParam int customerId,
									@RequestParam int action,
									@RequestParam String reason,
									@ModelAttribute CustomerWaterMeter customerWaterMeter) throws MyException {
		Customer customer = customerService.findById(customerId);
		if(customer == null)
			throw new MyException("客户不存在");
		
		if(action != Consts.WATER_METER_MAINTAIN_ACTION_EDIT) {
			customerWaterMeter.setLatestCheckTime(new Date());
		}
		customerWaterMeter.setCustomer(customer);
		CustomerWaterMeter meter = customerWaterMeterService.save(customerWaterMeter);
		
		if(action != Consts.WATER_METER_MAINTAIN_ACTION_EDIT) {
			WaterMeterMaintainHistory history = new WaterMeterMaintainHistory();
			history.setAction(action);
			history.setReason(reason);
			history.setWaterMeter(meter);;
			waterMeterMaintainHistoryService.save(history);
		}

		return "redirect:/customer/manage/watermeter?id=" + customer.getId();
	}
	
	@RequestMapping(value="/customer/manage/watermetermainhistorylist",method=RequestMethod.GET)
	public String listWaterMeterMaintainHistory(@RequestParam int customerId,ModelMap model) {
		List<WaterMeterMaintainHistory> allhistory = waterMeterMaintainHistoryService.findHistory4Customer(customerId);
		model.addAttribute("allhistory", allhistory);
		return "/customer/customer_water_meter_maintain_history";
	}
	
	@RequestMapping(value="/customer/manage/watermeterform",method=RequestMethod.GET)
	public String customerWaterMeterForm(@RequestParam(defaultValue="0") int meterId,
										@RequestParam int customerId,
										ModelMap model) {
		CustomerWaterMeter meter = customerWaterMeterService.findById(meterId);
		if(meter == null)
			meter = new CustomerWaterMeter();
		model.addAttribute("meter", meter);
		model.addAttribute("customer_id", customerId);
		model.addAttribute("meter_usage",GlobalConfiguration.getInstance().getConfigValueList(Consts.GCK_CUSTOMER_WATER_METER_USAGE));
		List<WaterMeterType> waterMeterTypeList = waterMeterTypeService.findAll();
		model.addAttribute("waterMeterTypeList", waterMeterTypeList);
		return "/customer/customer_water_meter_form";
	}

	@OpAnnotation(moduleName="客户水表操作",option="停水")
	@RequestMapping(value="/customer/manage/watermeter/stop",method=RequestMethod.GET)
	public String disMeter(@RequestParam(defaultValue="0") int meterId,
							@RequestParam int customerId) throws MyException {
		CustomerWaterMeter meter = customerWaterMeterService.findById(meterId);
		if(meter == null)
			throw new MyException("水表不存在");
		meter.setStatus(2);
		customerWaterMeterService.save(meter);
		return "redirect:/customer/manage/watermeter?id=" + customerId;
	}
	
	@OpAnnotation(moduleName="客户水表操作",option="复水")
	@RequestMapping(value="/customer/manage/watermeter/use",method=RequestMethod.GET)
	public String enMeter(@RequestParam(defaultValue="0") int meterId,
							@RequestParam int customerId) throws MyException {
		CustomerWaterMeter meter = customerWaterMeterService.findById(meterId);
		if(meter == null)
			throw new MyException("水表不存在");
		meter.setStatus(1);
		customerWaterMeterService.save(meter);
		return "redirect:/customer/manage/watermeter?id=" + customerId;
	}
	
	@RequestMapping(value="/customer/manage/search",method=RequestMethod.GET)
	public String searchCustomer(ModelMap model) {
		List<WaterProvider> waterProviders = waterProviderService.findAll();
		model.addAttribute("waterProviders",waterProviders);
		return "/customer/customer_search_form";
	}
	
	@RequestMapping(value="/customer/manage/search",method=RequestMethod.POST)
	public String searchCustomer(@RequestParam(defaultValue="") String customerCode,
								 @RequestParam(defaultValue="") String customerName,
								 @RequestParam(defaultValue="") String customerAddress,
								 @RequestParam(defaultValue="0") int waterProvider,
								 @RequestParam(defaultValue="0") int customerStatus,
								 @RequestParam(defaultValue="0") int meterStatus,
								 ModelMap model) {
		List<CustomerWaterMeter> meters = customerWaterMeterService.diySearch(customerCode,customerName,customerAddress,waterProvider,customerStatus,meterStatus);
		model.addAttribute("meters", meters);
		model.addAttribute("customerCode", customerCode);
		model.addAttribute("customerName", customerName);
		model.addAttribute("customerAddress", customerAddress);
		model.addAttribute("waterProvider", waterProvider);
		model.addAttribute("customerStatus", customerStatus);
		model.addAttribute("meterStatus", meterStatus);
		return "/customer/customer_search_result";
	}
	
	@RequestMapping(value="/customer/manage/search/export",method=RequestMethod.POST)
	public void download(@RequestParam(defaultValue="") String customerCode,
						 @RequestParam(defaultValue="") String customerName,
						 @RequestParam(defaultValue="") String customerAddress,
						 @RequestParam(defaultValue="0") int waterProvider,
						 @RequestParam(defaultValue="0") int customerStatus,
						 @RequestParam(defaultValue="0") int meterStatus,
						 HttpServletResponse resp) throws IOException {
		List<CustomerWaterMeter> meters = customerWaterMeterService.diySearch(customerCode,customerName,customerAddress,waterProvider,customerStatus,meterStatus);
		ExcelData excelData = new ExcelData(0,5);
		List<String> title = new ArrayList<String>();
		title.add("用户编号");title.add("表号");title.add("水表用途");title.add("水表状态");title.add("水表度数");
		excelData.setTitle(title);
		for(CustomerWaterMeter meter : meters) {
			List<String> row = new ArrayList<String>();
			row.add(meter.getCustomer().getCustomerInfo().getCode());
			row.add(meter.getBodyNumber());
			row.add(meter.getUsage());
			row.add(String.valueOf(meter.getStatus()));
			row.add(String.valueOf(meter.getWaterMeterData().getOrgNumber()));
			excelData.addRowContent(row);
		}
		resp.addHeader("Content-disposition", "attachment;filename=export.xls");
		
		resp.setContentType("txt/plain");
		ExcelHelper.writeExcel(excelData, resp.getOutputStream());
		resp.flushBuffer();
	}
	
	@RequestMapping(value="/customer/manage/importmeterstatus",method=RequestMethod.GET)
	public String importMeterStatus() {
		return "/customer/import_meter_status";
	}
	
	@OpAnnotation(moduleName="客户水表操作",option="导入水表状态")
	@RequestMapping(value="/customer/manage/importmeterstatus",method=RequestMethod.POST)
	public String importMeterStatus(MultipartFile inputfile,HttpServletRequest request,ModelMap model) throws MyException, IOException {
		MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;  
        MultipartFile uploadFile = multipartRequest.getFile("inputfile");
        
        ExcelData data = new ExcelData(1,2);
        ExcelHelper.readExcelFile(uploadFile.getInputStream(), data);
        verifyData(data);
        saveData(data);
		model.addAttribute("msg", "输入用户用水量成功！！");
		return "/msg";
	}
	
	private void verifyData(ExcelData data) throws MyException {
		List<List<String>> content = data.getContent();
		for(int i=0;i<content.size();i++) {
			List<String> row = content.get(i);
			String meterBodyNumber = row.get(0);
			String status = row.get(1);
			if(!"停水".equals(status) && !"复水".equals(status))
				throw new MyException("第" + (i+1) + "行，水表状态只能是停水或者复水");
			CustomerWaterMeter meter = customerWaterMeterService.findByMeterBodyNumber(meterBodyNumber);
			if(meter == null)
				throw new MyException("第" + (i+1) + "行，水表号不存在");
		}
	}
	
	private void saveData(ExcelData data) {
		List<List<String>> content = data.getContent();
		for(int i=0;i<content.size();i++) {
			List<String> row = content.get(i);
			String meterBodyNumber = row.get(0);
			String status = row.get(1);
			CustomerWaterMeter meter = customerWaterMeterService.findByMeterBodyNumber(meterBodyNumber);
			if("停水".equals(status))
				meter.setStatus(Consts.WATER_METER_STATUS_STOPING);
			if("复水".equals(status))
				meter.setStatus(Consts.WATER_METER_STATUS_USING);
			customerWaterMeterService.save(meter);
		}
	}
}
