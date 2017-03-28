package org.water.billing.entity.biz;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer_type")
public class CustomerType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column ( name = "id",length=10)
	private int id;
	
	@Column(name="name")
	private String name;
}
