package com.medixpress.commercial;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.medixpress.medixpress_commercial.R;
import com.medixpress.sqlite.DatabaseHelper;
import com.medixpress.sqlite.Order;
import com.medixpress.sqlite.Product;

import android.graphics.Bitmap;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	
	private MainActivity parentActivity = null;
	private DatabaseHelper helper = null;
	
	private ProductFragment productFragment = null;
	private ReportFragment reportFragment = null;
	private OrderFragment orderFragment = null;
	
	private List<Product> products = null;
	private List<Order> orders = null;
	private Map<Long, Bitmap> productImages = null;

	public TabsPagerAdapter(MainActivity parentActivity) {
		super(parentActivity.getFragmentManager());
		
		this.parentActivity = parentActivity;
	}

	@Override
	public Fragment getItem(int index) {
        switch (index) {
        case 0:
        	//if (orderFragment == null) {
        		orderFragment = new OrderFragment();
	        	if (orders != null) {
	        		orderFragment.setOrders(orders);
	        	}	
        	//}
        	return orderFragment;
        case 1:
            //if (productFragment == null) {
            	productFragment = new ProductFragment();
            	if (products != null) {
            		productFragment.setProducts(products);
            		if (productImages != null)
            			productFragment.setProductImages(productImages);
            	}
            //} else {
            	
            //}
            return productFragment;
        case 2:
        	//if (reportFragment == null)
        		reportFragment = new ReportFragment();
            return reportFragment;
        }
        return null;
	}

	@Override
	public int getCount() {
		return 3;
	}

	public String[] getTabLabels() {
		String[] rval = new String[getCount()];
		//
		rval[0] = parentActivity.getString(R.string.orders);
		rval[1] = parentActivity.getString(R.string.products);
		rval[2] = parentActivity.getString(R.string.reports);
				
		return rval;
	}
	
	public void setProducts(List<Product> products) {
		this.products = products;
		if (productFragment != null) {
			productFragment.setProducts(products);
		}
	}
	
	public void setOrders(List<Order> orders) {
		this.orders = orders;
		if (orderFragment != null) {
		     orderFragment.setOrders(orders);
		}
	}
	
	public void setProductImages(Map<Long, Bitmap> productImages) {
		this.productImages = productImages;
		if (productFragment != null) {
			productFragment.setProductImages(productImages);
		}
	}
}
