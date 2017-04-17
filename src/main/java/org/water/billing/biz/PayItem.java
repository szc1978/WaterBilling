package org.water.billing.biz;

public class PayItem {
	private String itemName = "";
	private Float itemPrice = new Float(0);
	
	public PayItem() {
		
	}
	
	public PayItem(String itemName,Float itemPrice) {
		this.itemName = itemName;
		this.itemPrice = itemPrice;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Float getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Float itemPrice) {
		this.itemPrice = itemPrice;
	}
}
