package com.medixpress.sqlite;

import java.io.IOException;
import java.util.ArrayList;

public class DemoDatabase {
	// Creates a demonstration database

	// Creates a fake database for a vendor and returns the vendorId
	public static long createDemoDatabase(DatabaseHelper helper) {

		Vendor v1 = helper.addVendor(new Vendor("Danktown Express", "Boulder, Colorado", "8am - 7pm"));
		// Create fake products
		Product v1_p1 = helper.addProduct(v1, new Product("Sloppy Kush", 7.0f, 60.0f));
		Product v1_p2 = helper.addProduct(v1, new Product("OG Banana", 3.5f, 75.0f));
		Product v1_p3 = helper.addProduct(v1, new Product("Buddha Blaster", 70.0f, 60.0f));
		Product v1_p4 = helper.addProduct(v1, new Product("Diamond Sack", 60.f, 75.0f));
		Product v1_p5 = helper.addProduct(v1, new Product("Saggy Skunk", 44.0f, 56.0f));
		// Create fake orders
		Order v1_o1 = helper.addOrder(new Order(v1_p1.getProductId(), 1, v1.getVendorId(), 1.0f));
		Order v1_o2 = helper.addOrder(new Order(v1_p2.getProductId(), 3, v1.getVendorId(), 0.5f));
		Order v1_o3 = helper.addOrder(new Order(v1_p3.getProductId(), 4, v1.getVendorId(), 10.0f));
		Order v1_o4 = helper.addOrder(new Order(v1_p5.getProductId(), 10, v1.getVendorId(), 7.3f));
		// return vendorId
		return v1.getVendorId();
	}
}
