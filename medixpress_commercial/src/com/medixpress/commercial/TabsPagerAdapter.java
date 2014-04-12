package com.medixpress.commercial;

import java.util.ArrayList;
import java.util.List;

import com.medixpress.medixpress_commercial.R;
import com.medixpress.sqlite.DatabaseHelper;
import com.medixpress.sqlite.Product;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	
	private MainActivity parentActivity = null;
	private DatabaseHelper helper = null;
	
	private ProductFragment productFragment = null;
	private SearchFragment searchFragment = null;
	
	private List<Product> products = null;

	public TabsPagerAdapter(MainActivity parentActivity) {
		super(parentActivity.getSupportFragmentManager());
		
		this.parentActivity = parentActivity;
	}

	@Override
	public Fragment getItem(int index) {
        switch (index) {
        case 0:
            if (productFragment == null) {
            	productFragment = new ProductFragment();
            	if (products != null) {
            		productFragment.setProducts(products);
            	}
            } else {
            	
            }
            return productFragment;
        case 1:
        	if (searchFragment == null)
        		searchFragment = new SearchFragment();
            return searchFragment;
        }
 
        return null;
	}

	@Override
	public int getCount() {
		return 2;
	}

	public String[] getTabLabels() {
		String[] rval = new String[getCount()];
		
		rval[0] = parentActivity.getString(R.string.products);
		rval[1] = parentActivity.getString(R.string.search);
				
		return rval;
	}
	
	public void setProducts(List<Product> products) {
		this.products = products;
		if (productFragment != null) {
			productFragment.setProducts(products);
		}
	}
}
