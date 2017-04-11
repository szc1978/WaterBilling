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
import org.water.billing.entity.admin.SysUser;
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
	public String inputData(@RequestParam(required=false) String code,
						ModelMap map) {
		Customer customer = customerService.findByCode(code);
		if(customer == null)
			customer = new Customer();
		map.addAttribute("customer",customer);
		return "/waterdata/input_water_data";
	}
	
	@OpAnnotation(moduleName="数据录入",option = "录入用水量")
	@RequestMapping(value="/waterdata/input_water_data",method=RequestMethod.POST)
	public String inputData(@RequestParam int id,
							@RequestParam int month,
							@RequestParam Float waterNumber,
							HttpServletRequest request) throws Exception {
		Customer customer = customerService.findById(id);
		CustomerWater customerWater = customer.getCustomerWater();
		if(waterNumber < customerWater.getOrgNumber()) 
			throw new Exception("本期用水量不应该比前期低");
		
		if(month == customerWater.getPayMonth())
			throw new MyException(month + "月用水已经输入而且审核通过");
		
		SecurityContext securityContext = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		SysUser user = (SysUser) securityContext.getAuthentication().getPrincipal();
		
		customerWater.setNewNumber(waterNumber);
		customerWater.setPayMonth(month);
		customerWater.setInputerName(user.getName());
		
		customerWaterService.save(customerWater);

		return "redirect:/waterdata/input_water_data";
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
        List<ExcelData> excelDatas = readExcelFile(uploadFile);
        SecurityContext securityContext = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		
        verifyExcelData(excelDatas);
        
        SysUser user = (SysUser) securityContext.getAuthentication().getPrincipal();
        saveData(excelDatas,user.getName());
        model.addAttribute("msg", "导入用户用水量成功！！");
		return "/msg";
	}
	
	private void saveData(List<ExcelData> excelDatas,String inputerName) {
		for(ExcelData excelData : excelDatas) {
			Customer customer = customerService.findByCode(excelData.getCustomerCode());
			CustomerWater customerWater = customer.getCustomerWater();
			customerWater.setNewNumber(excelData.getWaterNumber());
			customerWater.setInputerName(inputerName);
			customerWaterService.save(customerWater);
		}
	}
	
	private void verifyExcelData(List<ExcelData> excelDatas) throws MyException {
		for(ExcelData excelData : excelDatas) {
			Customer customer = customerService.findByCode(excelData.getCustomerCode());
			if(customer == null)
				throw new MyException("用户编号" + excelData.getCustomerCode() + "不存在,或者等待审核中");
			if(excelData.getMonth() < 1 || excelData.getMonth() > 12)
				throw new MyException("用户编号" + excelData.getCustomerCode() + "对应的月份不是合理的值！！");
			if(excelData.getWaterNumber() < customer.getCustomerWater().getOrgNumber())
				throw new MyException("用户编号" + excelData.getCustomerCode() + "的用水量低于当前用水量");
		}
	}

	private List<ExcelData> readExcelFile(MultipartFile uploadFile) throws MyException {
		List<ExcelData> excelDatas = new ArrayList<ExcelData>();
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
            	ExcelData excelData = new ExcelData(customerCode.getContents(),month.getContents(),waterNumber.getContents());
            	excelDatas.add(excelData);
            } catch (NumberFormatException e) {
            	throw new MyException("Excel文件第" + i + "行数据格式不对，请检查后再导入");
            }
        }
        book.close(); 
        return excelDatas;
	}
	
}

class ExcelData {
	private String customerCode;
	private int month;
	private Float waterNumber;
	
	public ExcelData(String customerCode,String strMonth,String strWaterNumber) throws NumberFormatException {
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
	

