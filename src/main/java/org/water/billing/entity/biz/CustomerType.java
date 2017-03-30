package org.water.billing.entity.biz;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "customer_type")
public class CustomerType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column ( name = "id",length=10)
	private int id;
	
	@Column(name="name",unique=true)
	private String name;
	
	@Column(name="description")
	private String description;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="customer_type_default_charge",joinColumns={@JoinColumn(name="type_id")},inverseJoinColumns={@JoinColumn(name="charge_id")})
	private Set<Charge> charges;
	
	public CustomerType() {
		
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Charge> getCharges() {
		return charges;
	}

	public void setCharges(Set<Charge> charges) {
		this.charges = charges;
	}
	
}
