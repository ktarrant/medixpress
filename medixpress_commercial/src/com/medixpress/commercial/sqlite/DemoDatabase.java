package com.medixpress.commercial.sqlite;

import java.io.IOException;

import android.content.Context;

public class DemoDatabase {
	// Creates a demonstration database
	
	private void loadDictionary(final DatabaseHelper helper) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    createDemoDatabase(helper);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

	private void createDemoDatabase(DatabaseHelper helper) throws IOException {
		
		Vendor v1 = helper.addVendor(new Vendor("Danktown Express", "Boulder, Colorado", "8am - 7pm"));
		Product v1_p1 = helper.addProduct(v1, new Product("Sloppy Kush", 7.0f, 60.0f));
		Product v1_p2 = helper.addProduct(v1, new Product("OG Banana", 3.5f, 75.0f));
		
		Vendor v2 = helper.addVendor(new Vendor("", "Boulder, Colorado", "8am - 7pm"));
		Product v2_p1 = helper.addProduct(v2, new Product("Buddha Blaster", 70.0f, 60.0f));
		Product v2_p2 = helper.addProduct(v2, new Product("Diamond Sack", 60.f, 75.0f));
		Product v2_p3 = helper.addProduct(v2, new Product("Saggy Skunk", 44.0f, 56.0f));
	}
}
