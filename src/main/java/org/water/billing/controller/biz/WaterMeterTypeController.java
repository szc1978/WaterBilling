package org.water.billing.controller.biz;

import java.util.List;
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
		model.addAttribute("waterMeterType", waterMeterType);
		List<WaterMeterConfig> producers = waterMeterConfigService.findByConfigItemId(WaterMeterConfigItemEnum.PRODUCER.getId());
		model.addAttribute("producers",producers);
		List<WaterMeterConfig> models = waterMeterConfigService.findByConfigItemId(WaterMeterConfigItemEnum.MODEL.getId());
		model.addAttribute("models",models);
		List<WaterMeterConfig> sizes = waterMeterConfigService.findByConfigItemId(WaterMeterConfigItemEnum.SIZE.getId());
		model.addAttribute("sizes",sizes);
		return "/customer/water_meter_type_form";
	}
	
	@RequestMapping(value="/customer/watermetertype/form",method=RequestMethod.POST)
	public String waterMeter(@ModelAttribute WaterMeterType waterMeterType) throws MyException {
		WaterMeterConfig producer = waterMeterConfigService.findByConfigItemIdAndValue(WaterMeterConfigItemEnum.PRODUCER.getId(), waterMeterType.getProducer().getConfigItemValue());
		if(producer == null) {
			producer = new WaterMeterConfig(WaterMeterConfigItemEnum.PRODUCER.getId(), waterMeterType.getProducer().getConfigItemValue());
			producer = waterMeterConfigService.save(producer);
		}
		WaterMeterConfig model = waterMeterConfigService.findByConfigItemIdAndValue(WaterMeterConfigItemEnum.MODEL.getId(), waterMeterType.getModel().getConfigItemValue());
		if(model == null) {
			model = new WaterMeterConfig(WaterMeterConfigItemEnum.MODEL.getId(), waterMeterType.getModel().getConfigItemValue());
			model = waterMeterConfigService.save(model);
		}
		WaterMeterConfig size = waterMeterConfigService.findByConfigItemIdAndValue(WaterMeterConfigItemEnum.SIZE.getId(), waterMeterType.getSize().getConfigItemValue());
		if(size == null) {
			size = new WaterMeterConfig(WaterMeterConfigItemEnum.SIZE.getId(), waterMeterType.getSize().getConfigItemValue());
			size = waterMeterConfigService.save(size);
		}
		waterMeterType.setProducer(producer);
		waterMeterType.setModel(model);
		waterMeterType.setSize(size);
		waterMeterTypeService.save(waterMeterType);
		
		return "redirect:/customer/watermetertype";
	}
	
}
