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
import org.water.billing.service.biz.WaterMeterConfigService;

@Controller
public class WaterMeterController {
	
	@Autowired
	WaterMeterConfigService waterMeterConfigService;

	@RequestMapping(value="/customer/watermeter",method=RequestMethod.GET)
	public String listWaterMeter(ModelMap model) {
		List<WaterMeterConfig> configs = waterMeterConfigService.findAll();
		model.addAttribute("configs", configs);
		return "/customer/water_meter_config";
	}
	
	@RequestMapping(value="/customer/watermeter/form",method=RequestMethod.GET)
	public String waterMeterConfig(@RequestParam(defaultValue="0") int id,ModelMap model) {
		WaterMeterConfig config = waterMeterConfigService.findById(id);
		if(config == null)
			config = new WaterMeterConfig();
		model.addAttribute("config", config);
		List<Map<String,String>> sss = getWaterMeterConfigItem();
		model.addAttribute("items",sss);
		return "/customer/water_meter_form";
	}
	
	@RequestMapping(value="/customer/watermeter",method=RequestMethod.POST)
	public String waterMeter(@ModelAttribute WaterMeterConfig config) throws MyException {
		List<WaterMeterConfig> configs = waterMeterConfigService.findByIdAndValue(config.getConfigItemId(), config.getConfigItemValue());
		if(config.getId() == 0 && configs.size() > 0)
			throw new MyException("已经存在");
		for(WaterMeterConfig c : configs) {
			if(c.getId() != config.getId())
				throw new MyException("已经存在");
		}
		waterMeterConfigService.save(config);
		
		return "/customer/water_meter_list";
	}
	
	private List<Map<String,String>> getWaterMeterConfigItem() {
		List<Map<String,String>> res = new ArrayList<Map<String,String>>();
		for(WaterMeterConfigItemEnum item : WaterMeterConfigItemEnum.values()) {
			Map<String,String> m = new HashMap<String,String>();
			m.put("id", String.valueOf(item.getId()));
			m.put("name", item.getName());
			res.add(m);
		}
		return res;
	}
}
