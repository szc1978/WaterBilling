package org.water.billing.dao.admin;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.admin.PublicAnnouncement;

public interface PublicAnnouncementDao extends JpaRepository<PublicAnnouncement, Integer>{
	
	public Page<PublicAnnouncement> findAll(Pageable page);
	
	public int countByCreateDateGreaterThan(Date fromDate);
	
	public PublicAnnouncement findById(int id);
}
