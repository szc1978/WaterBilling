package org.water.billing.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.LoginLog;

public interface LoginLogDao extends JpaRepository<LoginLog, Integer>{

}
