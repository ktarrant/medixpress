package com.medixpress.sqlite;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class DemoDatabase {
	// Creates a demonstration database

	private final static String TAG = "DemoDatabase";
	// Creates a fake database for a vendor and returns the vendorId
	public static long createDemoDatabase(DatabaseHelper helper) {

		Vendor v1 = helper.addVendor(new Vendor("Danktown Express", "Boulder, Colorado", "8am - 7pm"));
		// Create fake products
		// Flowers
		Product v1_p1 = helper.addProduct(v1, new Product("Sloppy Kush", 7.0f, 60.0f, 0));
		Product v1_p2 = helper.addProduct(v1, new Product("OG Banana", 3.5f, 75.0f, 0));
		Product v1_p3 = helper.addProduct(v1, new Product("Buddha Blaster", 70.0f, 60.0f, 0));
		Product v1_p4 = helper.addProduct(v1, new Product("Diamond Sack", 60.f, 75.0f, 0));
		Product v1_p5 = helper.addProduct(v1, new Product("Saggy Skunk", 44.0f, 56.0f, 0));
		// Edibles
		Product v1_p6 = helper.addProduct(v1, new Product("Pot Brownies", 1.0f, 10.0f, 1));
		Product v1_p7 = helper.addProduct(v1, new Product("Pot-Pop", 1.0f, 3.0f, 1));
		Product v1_p8 = helper.addProduct(v1, new Product("Smack n' Cheese", 1.0f, 13.0f, 1));
		// Create fake orders
		Date now = new Date();
		Order v1_o1 = helper.addOrder(new Order(v1_p1.getProductId(), 1, v1.getVendorId(), 1.0f, 
				now.getTime()-100000));
		Order v1_o2 = helper.addOrder(new Order(v1_p2.getProductId(), 3, v1.getVendorId(), 0.5f, 
				now.getTime()-1000000));
		Order v1_o3 = helper.addOrder(new Order(v1_p3.getProductId(), 4, v1.getVendorId(), 10.0f, 
				now.getTime()-3000000));
		Order v1_o4 = helper.addOrder(new Order(v1_p5.getProductId(), 10, v1.getVendorId(), 7.3f, 
				now.getTime()-10000000));
		// return vendorId
		return v1.getVendorId();
	}
	
	private static HashMap<Integer,ArrayList<Long>> tracker = new HashMap<Integer, ArrayList<Long>>();
	
	public static Bitmap createDemoBitmap(Context context, Product product) {
	    AssetManager assetManager = context.getAssets();
	    
	    long productId = product.getProductId();
	    int typeId = product.getTypeId();
//
	    InputStream istr;
	    Bitmap bitmap = null;
    	if (tracker.get(typeId) == null) {
    		tracker.put(typeId, new ArrayList<Long>());
    	}
    	List<Long> prd = tracker.get(typeId);
    	if (!prd.contains(productId)) {
    		String fn = String.format("demo%d_%d.jpg", typeId, prd.size());
    		//Log.i(TAG, fn);
    		prd.add(productId);
		    try {
		        istr = assetManager.open(fn);
		        bitmap = BitmapFactory.decodeStream(istr);
		    } catch (IOException e) {
		        return null;
		    }
    	}
	    return bitmap;
	}
}
