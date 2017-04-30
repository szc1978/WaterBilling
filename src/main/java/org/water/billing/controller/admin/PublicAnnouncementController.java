package org.water.billing.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.MyException;
import org.water.billing.annotation.OpAnnotation;
import org.water.billing.entity.admin.PublicAnnouncement;
import org.water.billing.entity.admin.SysUser;
import org.water.billing.service.admin.PublicAnnouncementService;
import org.water.billing.utils.Utils;

@Controller
public class PublicAnnouncementController {
	
	@Autowired
	PublicAnnouncementService announcementService;
	
	@RequestMapping(value="/admin/announcement/list",method=RequestMethod.GET)
	public String announcementList(@RequestParam(defaultValue="1") int page,
									@RequestParam(defaultValue="10")  int size,
									ModelMap model) {
		page = page < 1?1:page;
		Page<PublicAnnouncement> pageInfo = announcementService.findAll(page-1, size);
		model.addAttribute("pageUrl", "/admin/announcement/list");
		model.addAttribute("announcements",pageInfo.getContent());
		Utils.setPageInfo4ModelMap(pageInfo, model);

		model.addAttribute("announcements", pageInfo.getContent());
		return "/announcement/announcement_list";
	}

	@RequestMapping(value="/admin/announcement/publish",method=RequestMethod.GET)
	public String publicAnnouncement(@RequestParam(defaultValue="0") int id,ModelMap model) {
		PublicAnnouncement announcement = announcementService.findById(id);
		if(announcement == null)
			announcement = new PublicAnnouncement();

		model.addAttribute("announcement", announcement);
		return "/announcement/announcement_form";
	}
	
	@OpAnnotation(moduleName="公告管理",option="发布公告")
	@RequestMapping(value="/admin/announcement/publish",method=RequestMethod.POST)
	public String pubicAnnouncement(@ModelAttribute PublicAnnouncement announcement,HttpServletRequest request,ModelMap model) {
		SysUser user = Utils.getLoginUserInSession(request);
		announcement.setPublisher(user);
		announcementService.save(announcement);
		model.addAttribute("msg","发布公告成功！！！");
		return "redirect:/admin/announcement/list";
	}
	
	@OpAnnotation(moduleName="公告管理",option="删除公告")
	@RequestMapping(value="/admin/announcement/delete",method=RequestMethod.GET)
	public String deleteAnnouncement(@RequestParam int id) throws MyException {
		PublicAnnouncement announcement = announcementService.findById(id);
		if(announcement == null)
			throw new MyException("公告不存在");
		announcementService.remove(announcement);
		return "redirect:/admin/announcement/list";
	}
}
