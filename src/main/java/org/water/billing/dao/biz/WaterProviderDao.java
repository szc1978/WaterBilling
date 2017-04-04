package org.water.billing.dao.biz;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.biz.WaterProvider;

public interface WaterProviderDao extends JpaRepository<WaterProvider, Integer>{
	public List<WaterProvider> findAll();
	public WaterProvider findById(int id);
}
