package org.water.billing.service.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.water.billing.dao.biz.ChargeDao;
import org.water.billing.entity.biz.Charge;

@Service("ChargeService")
public class ChargeService {
	
	@Autowired
	ChargeDao chargeDao;
	
	public Page<Charge> findAll(int pageIndex,int number) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
		Pageable page = new PageRequest(pageIndex,number,sort);
		Page<Charge> charges = chargeDao.findAll(page);
		return charges;
	}

	public List<Charge> findAll() {
		return chargeDao.findAll();
	}
	
	public Charge save(Charge charge) {
		return chargeDao.save(charge);
	}
	
	public Charge findById(int id) {
		return chargeDao.findById(id);
	}
	
	public Charge findByName(String name) {
		return chargeDao.findByName(name);
	}
}
