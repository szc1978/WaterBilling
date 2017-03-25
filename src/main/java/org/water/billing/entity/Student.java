package org.water.billing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="student")
public class Student {
	@Id  
    @GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="id",length=20)  
    private int id; 
	
	@Column (name = "name")
	private String name;
	
	//@ManyToMany(cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
   // @JoinTable(name="teacher_student",joinColumns={@JoinColumn(name="s_id")},inverseJoinColumns={@JoinColumn(name="t_id")})
    //private Set<Teacher> teachers;
	
	public Student() {
		
	}
	
	public Student(int id,String name) {
		this.id = id;
		this.name = name;
	}
	
	public int hashCode() {
		return new Long(id).hashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if (! (obj instanceof Student)) {
            return false;
        }
        return this.id == ((Student)obj).getId();
	}
	
	public Student(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

/*	public Set<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}*/
}
