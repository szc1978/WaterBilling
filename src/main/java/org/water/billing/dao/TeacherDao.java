package org.water.billing.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.Teacher;

public interface TeacherDao extends JpaRepository<Teacher, Integer> {
	public Teacher findByName(String name);
}
