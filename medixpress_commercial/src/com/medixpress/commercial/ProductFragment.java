package com.medixpress.commercial;

import java.util.List;
import java.util.Map;

import com.medixpress.medixpress_commercial.R;
import com.medixpress.sqlite.DatabaseHelper;
import com.medixpress.sqlite.Product;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class ProductFragment extends Fragment {
	
	private ListView rootView = null;
	private ProductListAdapter adapter = null;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = (ListView)inflater.inflate(R.layout.layout_list, container, false);
        if (adapter != null) {
        	this.rootView.setAdapter(adapter);
        }
        return this.rootView;
    }
    
    @Override
    public void onResume() {
    	super.onResume(); //
        if (adapter != null) {
        	this.rootView.setAdapter(adapter);
        }
    }
    
    public void setProducts(List<Product> products) {
    	this.adapter = new ProductListAdapter(this.getActivity(), products);
    	this.rootView.setAdapter(this.adapter);
    }
    
    public void setProductImages(Map<Long, Bitmap> productImages) {
    	adapter.setProductImages(productImages);
    }
}
