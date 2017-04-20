package org.water.billing.controller.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.MyException;
import org.water.billing.consts.WaterMeterConfigItemEnum;
import org.water.billing.entity.biz.WaterMeterConfig;
import org.water.billing.entity.biz.WaterMeterType;
import org.water.billing.service.biz.WaterMeterConfigService;
import org.water.billing.service.biz.WaterMeterTypeService;

@Controller
public class WaterMeterTypeController {
		
	@Autowired
	WaterMeterTypeService waterMeterTypeService;
	
	@Autowired
	WaterMeterConfigService waterMeterConfigService;

	@RequestMapping(value="/customer/watermetertype",method=RequestMethod.GET)
	public String listWaterMeter(ModelMap model) {
		List<WaterMeterType> waterMeterTypes = waterMeterTypeService.findAll();
		model.addAttribute("waterMeterTypes", waterMeterTypes);
		return "/customer/water_meter_type_list";
	}
	
	@RequestMapping(value="/customer/watermetertype/form",method=RequestMethod.GET)
	public String waterMeterConfig(@RequestParam(defaultValue="0") int id,ModelMap model) {
		WaterMeterType waterMeterType = waterMeterTypeService.findById(id);
		if(waterMeterType == null)
			waterMeterType = new WaterMeterType();
		model.addAttribute("waterMterType", waterMeterType);
		List<WaterMeterConfig> producers = waterMeterConfigService.findByConfigItemId(WaterMeterConfigItemEnum.PRODUCER.getId());
		model.addAttribute("producers",producers);
		return "/customer/water_meter_type_form";
	}
	
	@RequestMapping(value="/customer/watermetertype/form",method=RequestMethod.POST)
	public String waterMeter(@ModelAttribute WaterMeterType waterMeterType) throws MyException {

		waterMeterTypeService.save(waterMeterType);
		
		return "/customer/water_meter_type_list";
	}
	
}
