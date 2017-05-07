package org.water.billing.biz;

public class DataCache {
	private static DataCache instance = null;
	private int pendingCountCustomerStatus = 0;
	private int pendingCountCustomerBill = 0;
	private int pendingCountCustomerWater = 0;
	
	private DataCache() {
		
	}
	
	public static DataCache getInstance() {
		if(instance == null)
			instance = new DataCache();
		return instance;
	}

	public int getPendingCountCustomerStatus() {
		return pendingCountCustomerStatus;
	}

	public void setPendingCountCustomerStatus(int pendingCountCustomerStatus) {
		this.pendingCountCustomerStatus = pendingCountCustomerStatus;
	}

	public int getPendingCountCustomerBill() {
		return pendingCountCustomerBill;
	}

	public void setPendingCountCustomerBill(int pendingCountCustomerBill) {
		this.pendingCountCustomerBill = pendingCountCustomerBill;
	}

	public int getPendingCountCustomerWater() {
		return pendingCountCustomerWater;
	}

	public void setPendingCountCustomerWater(int pendingCountCustomerWater) {
		this.pendingCountCustomerWater = pendingCountCustomerWater;
	}
	
}
