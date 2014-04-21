package com.medixpress.sqlite;

import android.database.Cursor;

public class Product {

	long productId;
	long vendorId;
	String name;
	float stock;
	float value;
	String symptoms;
	String keywords;
	String date;
	int typeId;
	
	// constructors
	public Product(String name, float stock, float value, int typeId) {
		this.productId = 0;
		this.vendorId = 0;
		this.name = name;
		this.stock = stock;
		this.value = value;
		this.symptoms = null;
		this.keywords = null;
		this.date = null;
		this.typeId = typeId;
	}
	
	public Product() {
	}
	
	// setters
	public void setProductId(long productId) { this.productId = productId; }
	public void setVendorId(long vendorId) { this.vendorId = vendorId; }
	public void setName(String name) { this.name = name; }
	public void setStock(float stock) { this.stock = stock; }
	public void setValue(float value) { this.value = value; }
	public void setSymptoms(String symptoms) { this.symptoms = symptoms; }
	public void setKeywords(String keywords) { this.keywords = keywords; }
	public void setDate(String date) { this.date = date; }
	public void setTypeId(int typeId) { this.typeId = typeId; }
	
	// getters
	public long getProductId() { return this.productId; }
	public long getVendorId() { return this.vendorId; }
	public String getName() { return this.name; }
	public float getStock() { return this.stock; }
	public float getValue() { return this.value; }
	public String getSymptoms()  { return this.symptoms; }
	public String getKeywords() { return this.keywords; }
	public String getDate() { return this.date; }
	public int getTypeId() { return this.typeId; }
}
