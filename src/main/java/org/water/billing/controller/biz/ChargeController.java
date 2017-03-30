package org.water.billing.controller.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.water.billing.consts.ChargeTypeEnum;
import org.water.billing.consts.Consts;
import org.water.billing.entity.biz.Charge;

@Controller
public class ChargeController {
	
	
	@RequestMapping(value = "/biz/charge",method=RequestMethod.GET)
	public String bizuser(ModelMap map) {
		Charge charge = new Charge();
		map.addAttribute("charge", charge);
		List<Map<String,String>> chargeTypes = new ArrayList<Map<String,String>>();
		for(ChargeTypeEnum type : ChargeTypeEnum.values()) {
			Map<String,String> chargeType = new HashMap<String,String>();
			chargeType.put("id", String.valueOf(type.getId()));
			chargeType.put("name", type.getName());
			chargeTypes.add(chargeType);
		}
		map.addAttribute("chargeTypes", chargeTypes);
		map.addAttribute("chargeFroms",Consts.CHARGE_FROM_TYPE);
		return "/biz/charge";
	}

}
