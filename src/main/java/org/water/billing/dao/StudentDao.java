package org.water.billing.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.water.billing.entity.Student;

public interface StudentDao extends JpaRepository<Student, Integer> {
	public Student findByName(String name);
}
