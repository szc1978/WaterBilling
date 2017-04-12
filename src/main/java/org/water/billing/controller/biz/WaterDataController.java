package org.water.billing.controller.biz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.water.billing.MyException;
import org.water.billing.annotation.OpAnnotation;
import org.water.billing.biz.BillGenerater;
import org.water.billing.consts.Consts;
import org.water.billing.entity.admin.SysUser;
import org.water.billing.entity.biz.Bill;
import org.water.billing.entity.biz.Customer;
import org.water.billing.entity.biz.CustomerWater;
import org.water.billing.service.biz.BillService;
import org.water.billing.service.biz.CustomerService;
import org.water.billing.service.biz.CustomerWaterService;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

@Controller
public class WaterDataController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CustomerWaterService customerWaterService;
	
	@Autowired
	BillService billService;
	
	@RequestMapping(value = "/waterdata/input_water_data",method=RequestMethod.GET)
	public String inputData(HttpServletRequest request,ModelMap map) {
		String searchField = request.getParameter("search_key");
		String searchValue = request.getParameter("search_value");
		List<Customer> customers = new ArrayList<Customer>();
		if(searchField != null && searchValue != null) {
			switch(searchField) {
			case "code":
				Customer customer = customerService.findByCode(searchValue);
				if(customer != null)
					customers.add(customer);
				break;
			case "address":
				customers = customerService.findByStatusAndAddress(searchValue);
				break;
			default:
				break;
			}
		}

		map.addAttribute("customers",customers);
		return "/waterdata/input_water_data";
	}
	
	@OpAnnotation(moduleName="数据录入",option = "录入用水量")
	@RequestMapping(value="/waterdata/input_water_data",method=RequestMethod.POST)
	public String inputWaterData(HttpServletRequest request,ModelMap model) throws Exception {

		SecurityContext securityContext = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		SysUser user = (SysUser) securityContext.getAuthentication().getPrincipal();
		
		String month = request.getParameter("month");
		List<InputData> inputDatas = new ArrayList<InputData>();
		for(String key : request.getParameterMap().keySet()) {
			if(!key.startsWith("WaterNumber_"))
				continue;
			String code = key.replace("WaterNumber_", "");
			String number = request.getParameter(key);
			InputData inputData = new InputData(code,month,number);
			inputDatas.add(inputData);
		}
		
		verifyExcelData(inputDatas);
		saveData(inputDatas,user.getName());
        model.addAttribute("msg", "输入用户用水量成功！！");
		return "/msg";
	}
	
	@RequestMapping(value = "/waterdata/import_water_data",method=RequestMethod.GET)
	public String importData() {
		return "/waterdata/import_water_data";
	}
	
	@OpAnnotation(moduleName="数据录入",option = "导入用水量")
	@RequestMapping(value = "/waterdata/import_water_data",method=RequestMethod.POST) 
	public String importData(MultipartFile inputfile,HttpServletRequest request,ModelMap model) throws MyException, IOException{
		MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;  
        MultipartFile uploadFile = multipartRequest.getFile("inputfile");
        List<InputData> excelDatas = readExcelFile(uploadFile);
        SecurityContext securityContext = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		
        verifyExcelData(excelDatas);
        
        SysUser user = (SysUser) securityContext.getAuthentication().getPrincipal();
        saveData(excelDatas,user.getName());
        model.addAttribute("msg", "导入用户用水量成功！！");
		return "/msg";
	}
	
	@RequestMapping(value = "/approve/customerwater/list",method=RequestMethod.GET)
	public String approveCustomerWater(ModelMap model) {
		List<Customer> allCustomersWhichHaveNewWaterNumber = customerService.findAllCustomersWhichHaveNewBill();
		model.addAttribute("allCustomersWhichHaveNewWaterNumber", allCustomersWhichHaveNewWaterNumber);
		return "/approve/customerwater";
	}
	
	@OpAnnotation(moduleName="业务审核",option="客户用水量")
	@RequestMapping(value = "/approve/customerwater",method=RequestMethod.GET)
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
			Float yearCount = customerWater.getYearCount() + customerWater.getNewNumber();
			customerWater.setYearCount(yearCount);
		}
		
		customerWater.setNewNumber(new Float(0));
		
		customerWaterService.save(customerWater);
		return "redirect:/approve/customerwater/list";
	}
	
	private void saveData(List<InputData> excelDatas,String inputerName) {
		for(InputData excelData : excelDatas) {
			Customer customer = customerService.findByCode(excelData.getCustomerCode());
			CustomerWater customerWater = customer.getCustomerWater();
			customerWater.setNewNumber(excelData.getWaterNumber());
			customerWater.setInputerName(inputerName);
			customerWaterService.save(customerWater);
		}
	}
	
	private void verifyExcelData(List<InputData> excelDatas) throws MyException {
		for(InputData excelData : excelDatas) {
			Customer customer = customerService.findByCode(excelData.getCustomerCode());
			if(customer == null)
				throw new MyException("用户编号" + excelData.getCustomerCode() + "不存在,或者等待审核中");
			if(excelData.getMonth() < 1 || excelData.getMonth() > 12)
				throw new MyException("用户编号" + excelData.getCustomerCode() + "对应的月份不是合理的值！！");
			if(excelData.getWaterNumber() < customer.getCustomerWater().getOrgNumber())
				throw new MyException("用户编号" + excelData.getCustomerCode() + "的用水量低于当前用水量");
		}
	}

	private List<InputData> readExcelFile(MultipartFile uploadFile) throws MyException {
		List<InputData> excelDatas = new ArrayList<InputData>();
		Sheet sheet;
        Workbook book;
        Cell customerCode,month,waterNumber;
        
        try {
			book= Workbook.getWorkbook(uploadFile.getInputStream());
		} catch (BiffException | IOException e) {
			throw new MyException("打开Excel文件失败，请联系管理员！！！");
		}
        sheet=book.getSheet(0);
        for(int i=1;;i++) {
        	customerCode=sheet.getCell(0,i);//（列，行）
            if("".equals(customerCode.getContents())==true || customerCode.getContents().startsWith("EOF"))
            	break;
            month = sheet.getCell(2, i);
            waterNumber = sheet.getCell(3, i);
            try {
            	InputData excelData = new InputData(customerCode.getContents(),month.getContents(),waterNumber.getContents());
            	excelDatas.add(excelData);
            } catch (NumberFormatException e) {
            	throw new MyException("Excel文件第" + i + "行数据格式不对，请检查后再导入");
            }
        }
        book.close(); 
        return excelDatas;
	}
	
}

class InputData {
	private String customerCode;
	private int month;
	private Float waterNumber;
	
	public InputData(String customerCode,String strMonth,String strWaterNumber) throws NumberFormatException {
		this.customerCode = customerCode;
		this.month = Integer.valueOf(strMonth);
		this.waterNumber = Float.valueOf(strWaterNumber);
	}
	
	public String getCustomerCode() {
		return customerCode;
	}
	
	public int getMonth() {
		return month;
	}
	
	public Float getWaterNumber() {
		return waterNumber;
	}
	
	
	
}
	

