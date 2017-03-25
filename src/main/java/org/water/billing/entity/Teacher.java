package org.water.billing.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="teacher")
public class Teacher {
	@Id  
    @GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="id",length=20)  
    private Long id; 
	
	@Column (name = "name")
	private String name;
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
    @JoinTable(name="teacher_student",joinColumns={@JoinColumn(name="t_id")},inverseJoinColumns={@JoinColumn(name="s_id")})
    private Set<Student> students;
	
	public Teacher() {
		
	}
	
	public Teacher(Long id,String name) {
		this.id = id;
		this.name = name;
	}
	
	public Teacher(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public Set<Student> getStudents() {
		return students;
	}
}
