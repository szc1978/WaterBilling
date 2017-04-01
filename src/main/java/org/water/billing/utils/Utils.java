package org.water.billing.utils;

import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.water.billing.consts.ChargeTypeEnum;

public class Utils {
	
	@SuppressWarnings("rawtypes")
	public static void setPageInfo4ModelMap(Page pageInfo,ModelMap map) {
		if(pageInfo != null) {
			map.addAttribute("pageNum",pageInfo.getNumber() + 1);
			map.addAttribute("pageSize",pageInfo.getSize());
			map.addAttribute("totalPages",pageInfo.getTotalPages());
			map.addAttribute("isFirstPage",pageInfo.isFirst());
			map.addAttribute("isLastPage",pageInfo.isLast());
			map.addAttribute("totalCount",pageInfo.getTotalElements());
		} else {
			map.addAttribute("pageNum",1);
			map.addAttribute("pageSize",20);
			map.addAttribute("totalPages",1);
			map.addAttribute("isFirstPage",true);
			map.addAttribute("isLastPage",true);
			map.addAttribute("totalCount",1);
		}
	}
	
	public static ChargeTypeEnum getChargeTypeById(int id) {
		for(ChargeTypeEnum type : ChargeTypeEnum.values()) {
			if(type.getId() == id)
				return type;
		}
		return null;
	}
}
