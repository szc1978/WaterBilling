package org.water.billing.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.water.billing.GlobalConfiguration;
import org.water.billing.GlobalConfigurationService;
import org.water.billing.annotation.OpAnnotation;
import org.water.billing.entity.admin.SysConfigurationItem;
import org.water.billing.service.admin.SysConfigurationItemService;

@Controller
public class SysConfigurationController {
	
	@Autowired
	SysConfigurationItemService configService;
	
	@Autowired
	GlobalConfigurationService globalConfigService;
	
	@RequestMapping(value = "/admin/configuration",method=RequestMethod.GET)
	public String payForm(ModelMap map) throws Exception {
		map.addAttribute("company_name",GlobalConfiguration.getInstance().getConfigValueByItemName("company_name"));
		map.addAttribute("late_pay_day",GlobalConfiguration.getInstance().getConfigValueByItemName("late_pay_day"));
		map.addAttribute("late_pay_ratio",GlobalConfiguration.getInstance().getConfigValueByItemName("late_pay_ratio"));
		return "/admin/configuration";
	}
	
	@OpAnnotation(moduleName="全局配置",option="修改配置")
	@RequestMapping(value = "/admin/configuration",method=RequestMethod.POST)
	public String pay(HttpServletRequest request,
						ModelMap map) {
		for(String key : request.getParameterMap().keySet()) {
			if(key.startsWith("gloabl_config_")) {
				String item = key.replace("gloabl_config_", "");
				String value = request.getParameter(key);
				SysConfigurationItem configItem = configService.findItem(item);
				if(configItem == null) {
					configItem = new SysConfigurationItem(item);
				}
				configItem.setValue(value);
				configService.save(configItem);
			}
		}
		globalConfigService.refreshConfiguration();
		return "redirect:/admin/configuration";
	}
}
