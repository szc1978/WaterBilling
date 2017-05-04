package org.water.billing.entity.biz;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.water.billing.consts.Consts;

@Entity
@Table(name = "bill")
public class Bill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column ( name = "id",length=10)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="begin_water_word")
	private Float beginWaterWord = new Float(0);
	
	@Column(name="end_water_word")
	private Float endWaterWord = new Float(0);
	
	@Column(name="inputer_name")
	private String inputerName;
	
	@Column(name="total_postage")
	private Float totalPostage = new Float(0);
	
	@Column(name="reduce_content",length=1024)
	private String reduceContent;
	
	@Column(name="detail_content",length=1024)
	private String detailContent;
	
	@Column(name="status",length=1)
	private int status = 0;
	
	@Column(name="is_charged",length=1)
	private int isCharged = 0;
	
	@Column(name="is_print_expenses")
	private int isPrintExpenses = 0;
	
	@Column(name="bill_type")
	private int billType = Consts.BILL_TYPE_WATER;
	
	@Column(name="auto_charge_flag")
	private int autoChargeFlag = Consts.NON_BILL_AUTO_CHARGE_FLAG;
		
	@Column(name="input_date",length=64)
	@Temporal(TemporalType.TIMESTAMP) 
	private Date inputDate; 
	
	@Column(name="charge_date")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date chargeDate;
	
	@Column(name="customer_water_meter_id")
	private int customerWaterMeterId;
	
	@Column(name="customer_code")
	private String customerCode;
	
	public Bill() {
		
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

	public Float getBeginWaterWord() {
		return beginWaterWord;
	}

	public void setBeginWaterWord(Float beginWaterWord) {
		this.beginWaterWord = beginWaterWord;
	}

	public Float getEndWaterWord() {
		return endWaterWord;
	}

	public void setEndWaterWord(Float endWaterWord) {
		this.endWaterWord = endWaterWord;
	}

	public String getInputerName() {
		return inputerName;
	}

	public void setInputerName(String inputerName) {
		this.inputerName = inputerName;
	}

	public Float getTotalPostage() {
		return totalPostage;
	}

	public void setTotalPostage(Float totalPostage) {
		this.totalPostage = totalPostage;
	}

	public String getReduceContent() {
		return reduceContent;
	}

	public void setReduceContent(String reduceContent) {
		this.reduceContent = reduceContent;
	}

	public String getDetailContent() {
		return detailContent;
	}

	public void setDetailContent(String detailContent) {
		this.detailContent = detailContent;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public Date getChargeDate() {
		return chargeDate;
	}

	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}

	public int getIsCharged() {
		return isCharged;
	}

	public void setIsCharged(int isCharged) {
		this.isCharged = isCharged;
	}

	public int getAutoChargeFlag() {
		return autoChargeFlag;
	}

	public void setAutoChargeFlag(int autoChargeFlag) {
		this.autoChargeFlag = autoChargeFlag;
	}

	public int getIsPrintExpenses() {
		return isPrintExpenses;
	}

	public void setIsPrintExpenses(int isPrintExpenses) {
		this.isPrintExpenses = isPrintExpenses;
	}

	public int getBillType() {
		return billType;
	}

	public void setBillType(int billType) {
		this.billType = billType;
	}

	public int getCustomerWaterMeterId() {
		return customerWaterMeterId;
	}

	public void setCustomerWaterMeterId(int customerWaterMeterId) {
		this.customerWaterMeterId = customerWaterMeterId;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/*public CustomerWaterMeter getCustomerWaterMeter() {
		return customerWaterMeter;
	}

	public void setCustomerWaterMeter(CustomerWaterMeter customerWaterMeter) {
		this.customerWaterMeter = customerWaterMeter;
	}*/
	
	
	
}
