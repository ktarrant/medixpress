package com.medixpress.commercial;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.medixpress.medixpress_commercial.R;
import com.medixpress.sqlite.DatabaseHelper;
import com.medixpress.sqlite.Order;
import com.medixpress.sqlite.Product;

import android.content.Context;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderListAdapter extends BaseAdapter {
	private LayoutInflater inflater = null;
	private List<Order> orders;
	private Context context;
	private DatabaseHelper helper = null;
	
	private View.OnClickListener orderPressListener
		= new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
		}
	};
	
	public OrderListAdapter(Context context, List<Order> orders) {
	    this.orders = orders;
	    this.context = context;
	    inflater = (LayoutInflater) 
	    		context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    helper = new DatabaseHelper(context);
	}
	
	@Override
	public int getCount() { 
		return orders.size();
	}
	
	@Override
	public Order getItem(int position) {
		return orders.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			OrderListItem orderView = new OrderListItem(context);
			orderView.setOrder(orders.get(position));
			//orderView.setOnClickListener(orderPressListener);
			return orderView;
		} else return convertView;
	}
	
	@Override
	public long getItemId(int position) {
		return orders.get(position).getOrderId();
	}

	
	public class OrderListItem extends FrameLayout {
		
		private View rootView = null;
		
		public OrderListItem(Context context, AttributeSet attrs, int defStyle) {
			super (context, attrs, defStyle);
			
			initView();
		}

		public OrderListItem(Context context, AttributeSet attrs) {
			super(context, attrs);
			
			initView();
		}
		
		public OrderListItem(Context context) {
			super(context);
			
			initView();
		}
		
		private void initView() {
			rootView = inflate(getContext(), R.layout.layout_textitem, null);
			this.addView(rootView);
		}
		
		public void setOrder(Order order) {
			TextView titleView = (TextView) rootView.findViewById(R.id.productTitle);
			TextView descriptionView = (TextView) rootView.findViewById(R.id.productDescription);
			
			Product product = helper.getProduct(order.getProductId());
			
			float saleValue = order.getAmount() * product.getValue();
			// titleView has the name
			titleView.setText(Html.fromHtml(
					String.format("<b>$%.2f</b> - <b>%.2f g</b> of "+
							"<b>%s</b>",
							saleValue, order.getAmount(),
							product.getName())));
			
			// The description has symptoms and keywords is they are not null
			SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss MM/dd");
			
			descriptionView.setText(Html.fromHtml(String.format(
					"Ordered from <i>%s</i> at <i>%s</i><br>"+
					"%.2f g @ <b>$%.2f per g</b> ($%.2f value).<br>" + 
					"<b>%.2f g</b> remaining in stock.",
					order.getConsumerId(), f.format(new Date(order.getTime())),
					order.getAmount(), product.getValue(),
					saleValue, product.getStock())));
			// TODO: replace consumerId with a Consumer object
					
		}

	}

}
