package org.water.billing.dao.biz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.biz.WaterMeterConfig;

public interface WaterMeterConfigDao extends JpaRepository<WaterMeterConfig, Integer>{

}
