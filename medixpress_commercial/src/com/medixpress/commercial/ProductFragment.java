package com.medixpress.commercial;

import java.util.List;

import com.medixpress.medixpress_commercial.R;
import com.medixpress.sqlite.DatabaseHelper;
import com.medixpress.sqlite.Product;

import android.app.Activity;
import android.content.Context;
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
        this.rootView = (ListView)inflater.inflate(R.layout.activity_products, container, false);
        	
        return this.rootView;
    }
    
    public void setProducts(List<Product> products) {
    	this.adapter = new ProductListAdapter(this.getActivity(), products);
    	this.rootView.setAdapter(this.adapter);
    }
    
}
