package org.water.billing.entity.biz;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "charge")
public class Charge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column ( name = "id",length=10)
	private int id;
	
	@NotBlank(message = "收费名称不能为空")
	@Column(name="name",unique=true,nullable=true)
	private String name;
	
	@Column(name="code",unique=true)
	private String code;
	
	@Column(name="can_free",length=1)
	private int canFree = 0;
	
	@Column(name="need_vat",length=1)
	private int needVat = 0;
	
	@Column(name="data_source")
	private String dataSource;
	
	@Column(name="description")
	private String description;
	
	@Column(name="charge_type",length=1)
	private int chargeType = 0;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="parameter_id")
	private ChargeParameter chargeParameter = new ChargeParameter();
	
	@Column(name = "create_time",length=64)
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createTime; 
	@PrePersist
    protected void onCreate() {
		createTime = new Date();
    }
	
	public Charge() {
		
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getCanFree() {
		return canFree;
	}

	public void setCanFree(int canFree) {
		this.canFree = canFree;
	}

	public int getNeedVat() {
		return needVat;
	}

	public void setNeedVat(int needVat) {
		this.needVat = needVat;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getChargeType() {
		return chargeType;
	}

	public void setChargeType(int chargeType) {
		this.chargeType = chargeType;
	}

	public ChargeParameter getChargeParameter() {
		return chargeParameter;
	}

	public void setChargeParameter(ChargeParameter chargeParameter) {
		this.chargeParameter = chargeParameter;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
