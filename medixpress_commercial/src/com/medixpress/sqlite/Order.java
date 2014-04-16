package com.medixpress.sqlite;

import android.database.Cursor;

public class Order {
	
	long productId;
	long vendorId;
	long consumerId;
	long orderId;
	float amount;
	
	// constructors
	public Order(long productId, long consumerId, long vendorId, float amount) {
		this.productId = productId;
		this.vendorId = vendorId;
		this.consumerId = consumerId;
		this.amount = amount;
		this.orderId = 0;
	}
	
	public Order() {
	}
	
	public Order(Cursor c) {
		this.fromCursor(c);
	}
	
	// setters
	public void setProductId(long productId) { this.productId = productId; }
	public void setVendorId(long vendorId) { this.vendorId = vendorId; }
	public void setConsumerId(long consumerId) { this.consumerId = consumerId; }
	public void setOrderId(long orderId) { this.orderId = orderId; }
	public void setAmount(float amount) { this.amount = amount; }
	public void fromCursor(Cursor c) {
		
	}
	
	// getters
	public long getProductId() { return this.productId; }
	public long getVendorId() { return this.vendorId; }
	public long getConsumerId() { return this.consumerId; }
	public long getOrderId() { return this.orderId; }
	public float getAmount() { return this.amount; }

}
