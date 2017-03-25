package org.water.billing.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.water.billing.dao.StudentDao;
import org.water.billing.dao.TeacherDao;
import org.water.billing.entity.Student;
import org.water.billing.entity.Teacher;

@Controller
public class DebugController {
	
	@Autowired
	TeacherDao tDao;
	
	@Autowired
	StudentDao sDao;

	@RequestMapping(value="/debug/school",method=RequestMethod.GET) 
	public String mySchool(@RequestParam(value="name") String name,
							ModelMap model) {
		Teacher teacher = tDao.findByName(name);
		List<Student> ss = sDao.findAll();
		Collections.addAll(ss);
		model.addAttribute(teacher);
		model.addAttribute("students",ss);
		return "/teacher";
	}
	
	@RequestMapping(value="/debug/addteacher",method=RequestMethod.GET) 
	public String mySchool(ModelMap model) {
		Teacher teacher = new Teacher();
		List<Student> ss = sDao.findAll();
		Collections.addAll(ss);
		model.addAttribute(teacher);
		model.addAttribute("students",ss);
		return "/teacher";
	}
	
	@RequestMapping(value="/debug/school",method=RequestMethod.POST)
	public String mySchool(@ModelAttribute Teacher teacher,ModelMap model) {
		String msg = "";
		if(teacher == null) {
			msg += "teacher is null <br>";
		} else {
			msg += "teacher name:" + teacher.getName() + ";";
			msg += "teacher id:" + teacher.getId() + ";";
			if(teacher.getStudents() == null)
				msg += "student is null<br>";
			else {
				for(Student s : teacher.getStudents())
					msg += "student:" + s.getName() + "_" + s.getId() + ",";
			}
		}
		tDao.save(teacher);
		model.addAttribute("msg", msg);
		return "redirect:/debug/school?name=" + teacher.getName();
	}
}
