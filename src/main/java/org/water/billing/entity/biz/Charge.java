package org.water.billing.entity.biz;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "charge")
public class Charge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column ( name = "id",length=10)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="code")
	private String code;
	
	@Column(name="can_free",length=1)
	private int canFree;
	
	@Column(name="needVat",length=1)
	private int needVat;
	
	@Column(name="data_source")
	private String dataSource;
	
	@Column(name="description")
	private String description;
	
	@Column(name="calculate_type")
	private String calculateType;
}
