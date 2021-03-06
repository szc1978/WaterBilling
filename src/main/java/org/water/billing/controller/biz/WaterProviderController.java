package org.water.billing.controller.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.annotation.OpAnnotation;
import org.water.billing.entity.biz.WaterProvider;
import org.water.billing.service.biz.WaterProviderService;

@Controller
public class WaterProviderController {
	
	@Autowired
	WaterProviderService waterProviderService;
	
	@RequestMapping(value = "/customer/waterprovider",method=RequestMethod.GET)
	public String waterProvider(@RequestParam(defaultValue="0") int id,
								ModelMap map) {
		List<WaterProvider> providers = waterProviderService.findAll();
		map.addAttribute("providers",providers);
		WaterProvider provider = waterProviderService.findById(id);
		if(provider == null) 
			provider = new WaterProvider();
		map.addAttribute("provider",provider);
		return "/customer/water_provider";
	}
	
	@OpAnnotation(moduleName="供水片区管理",option="增加或修改供水片区")
	@RequestMapping(value="/customer/waterprovider",method=RequestMethod.POST)
	public String waterProvider(@ModelAttribute WaterProvider provider) throws Exception {
		waterProviderService.save(provider);

		return "redirect:/customer/waterprovider";
	}

}
