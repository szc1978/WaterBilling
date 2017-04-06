package org.water.billing.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.entity.admin.SysConfigurationItem;
import org.water.billing.service.admin.SysConfigurationItemService;

@Controller
public class SysConfigurationController {
	
	@Autowired
	SysConfigurationItemService configService;
	
	@RequestMapping(value = "/admin/configuration",method=RequestMethod.GET)
	public String payForm(ModelMap map) throws Exception {
		List<SysConfigurationItem> configItems = configService.findAll();
		Map<String,String> configs = new HashMap<String,String>();
		for(SysConfigurationItem item : configItems) {
			configs.put(item.getItem(), item.getValue());
		}
		map.addAttribute("configs", configs);
		return "/admin/configuration";
	}
	
	@RequestMapping(value = "/admin/configuration",method=RequestMethod.POST)
	public String pay(@RequestParam int id,
						ModelMap map) {
		return "redirect:/admin/configuration";
	}
}
