package com.medixpress.commercial;

import java.util.List;

import com.medixpress.medixpress_commercial.R;
import com.medixpress.sqlite.Order;
import com.medixpress.sqlite.Product;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

public class OrderFragment extends Fragment {
	
	private ListView rootView = null;
	private OrderListAdapter adapter = null;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View frame = inflater.inflate(R.layout.layout_list, container, false);
        this.rootView = (ListView) frame.findViewById(R.id.list_content);
        if (adapter != null) {
        	this.rootView.setAdapter(adapter);
        }
        return this.rootView;
    }
    
    public void setOrders(List<Order> orders) {
    	this.adapter = new OrderListAdapter(this.getActivity(), orders);
    	this.rootView.setAdapter(this.adapter);
    }
}
