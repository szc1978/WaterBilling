package org.water.billing.dao.biz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.biz.Charge;

public interface ChargeDao extends JpaRepository<Charge, Integer>{
	public Page<Charge> findAll(Pageable page);
	public Charge findById(int id);
	
	public Charge findByName(String name);
}
