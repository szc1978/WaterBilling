package org.water.billing.service.admin;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.water.billing.dao.admin.PublicAnnouncementDao;
import org.water.billing.entity.admin.PublicAnnouncement;

@Service("publicAnnouncementService")
public class PublicAnnouncementService {
	
	@Autowired
	PublicAnnouncementDao announcementDao;
	
	public Page<PublicAnnouncement> findAll(int pageIndex,int number) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
		Pageable page = new PageRequest(pageIndex,number,sort);
		Page<PublicAnnouncement> announcement = announcementDao.findAll(page);
		return announcement;
	}
	
	public PublicAnnouncement save(PublicAnnouncement announcement) {
		return announcementDao.save(announcement);
	}
	
	public void remove(PublicAnnouncement announcement) {
		announcementDao.delete(announcement.getId());
	}
	
	public List<PublicAnnouncement> findLatest5Announce() {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
		Pageable page = new PageRequest(0,5,sort);
		Page<PublicAnnouncement> announces = announcementDao.findAll(page);
		return announces.getContent();
	}

	public int countNewAnnouncement(Date fromDate) {
		return announcementDao.countByCreateDateGreaterThan(fromDate);
	}
	
	public PublicAnnouncement findById(int id) {
		return announcementDao.findById(id);
	}
}
