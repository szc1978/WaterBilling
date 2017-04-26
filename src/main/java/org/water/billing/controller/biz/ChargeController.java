package org.water.billing.controller.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.GlobalConfiguration;
import org.water.billing.annotation.OpAnnotation;
import org.water.billing.consts.ChargeTypeEnum;
import org.water.billing.consts.Consts;
import org.water.billing.entity.biz.Charge;
import org.water.billing.service.biz.ChargeService;
import org.water.billing.utils.Utils;

@Controller
public class ChargeController {
	
	@Autowired
	ChargeService chargeService;
	
	@RequestMapping(value = "/charge/type",method=RequestMethod.GET)
	public String charge(@RequestParam(defaultValue="1") int page,
						@RequestParam(defaultValue="10")  int size,
						ModelMap map) {
		page = page < 1?1:page;
		Page<Charge> pageInfo = chargeService.findAll(page-1, size);
		map.addAttribute("charges",pageInfo.getContent());
		Utils.setPageInfo4ModelMap(pageInfo, map);
		return "/charge/charge_list";
	}
	
	@OpAnnotation(moduleName="资费管理",option="资费修改")
	@RequestMapping(value="/charge/type",method=RequestMethod.POST)
	public String charge(@ModelAttribute Charge charge,HttpServletRequest request) {
		Charge tmp = chargeService.save(charge);
		return "redirect:/charge/type?" + tmp.getId();
	}
	
	
	@RequestMapping(value = "/charge/type/form",method=RequestMethod.GET)
	public String chargeForm(@RequestParam(defaultValue="0") int id,ModelMap map) {
		Charge charge = chargeService.findById(id);
		if(charge == null)
			charge = new Charge();

		map.addAttribute("charge", charge);
		
		List<Map<String,String>> chargeTypes = genChargeType4Thymeleaf();
		map.addAttribute("chargeTypes", chargeTypes);
		map.addAttribute("chargeFroms",GlobalConfiguration.getInstance().getConfigValueList(Consts.GCK_CHARGE_FROM_TYPE));
		
		return "/charge/charge_form";
	}
	
	@RequestMapping(value="/charge/type/{id}",method=RequestMethod.GET)
	public String showCharge(@PathVariable int id,ModelMap map) {
		Charge charge = chargeService.findById(id);
		List<Charge> charges = new ArrayList<Charge>();
		if(charge != null)
			charges.add(charge);
		map.addAttribute("charges", charges);
		Utils.setPageInfo4ModelMap(null, map);
		return "/charge/charge_list";
	}
	
	private List<Map<String,String>> genChargeType4Thymeleaf() {
		List<Map<String,String>> chargeTypes = new ArrayList<Map<String,String>>();
		for(ChargeTypeEnum type : ChargeTypeEnum.values()) {
			Map<String,String> chargeType = new HashMap<String,String>();
			chargeType.put("id", String.valueOf(type.getId()));
			chargeType.put("name", type.getName());
			chargeTypes.add(chargeType);
		}
		return chargeTypes;
	}
}
