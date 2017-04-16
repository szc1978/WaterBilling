package org.water.billing.controller.biz;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WaterMeterController {

	@RequestMapping(value="/customer/watermeter",method=RequestMethod.GET)
	public String listWaterMeter() {
		
		return "/customer/water_meter_list";
	}
}
