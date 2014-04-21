package com.medixpress.commercial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.medixpress.medixpress_commercial.R;
import com.medixpress.sqlite.DatabaseHelper;
import com.medixpress.sqlite.Product;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

public class ProductFragment extends PreferenceFragment {
	private ExpandableListView rootView = null;
	private ProductListAdapter adapter = null;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View frame = inflater.inflate(R.layout.layout_list_exp, container, false);
        this.rootView = (ExpandableListView) frame.findViewById(R.id.list_content_exp);
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
    	// TODO: put these header labels in xml
    	String[] labels = {"Flowers", "Edibles", "Tinctures", "Shatter"};
    	List<String> headerLabels = new ArrayList<String>(Arrays.asList(labels));
    	this.adapter = new ProductListAdapter(this.getActivity(), headerLabels, products);
    	this.rootView.setAdapter(this.adapter);
    }
    
    public void setProductImages(Map<Long, Bitmap> productImages) {
    	adapter.setProductImages(productImages);
    }
}
