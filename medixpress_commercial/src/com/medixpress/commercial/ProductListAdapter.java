package com.medixpress.commercial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.medixpress.medixpress_commercial.R;
import com.medixpress.sqlite.Product;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductListAdapter extends BaseAdapter {

	private LayoutInflater inflater = null;
    private Map<Long, Bitmap> productImages = null;
    private Map<Long, ProductListItem> productItems = null;
	private List<Product> products;
	private Context context;
	
	private View.OnClickListener productPressListener
		= new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
		}
	};
	
	public ProductListAdapter(Context context, List<Product> products) {
	    this.products = products;
	    this.context = context;
	    this.productImages = new HashMap<Long, Bitmap>();
	    this.productItems = new HashMap<Long, ProductListItem>();
	    inflater = (LayoutInflater) 
	    		context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() { 
		return products.size();
	}
	
	@Override
	public Product getItem(int position) {
		return products.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			ProductListItem productView = new ProductListItem(context);
			productView.setProduct(products.get(position));
			//productView.setOnClickListener(productPressListener);
			productItems.put(products.get(position).getProductId(), productView);
			return productView;
		} else return convertView;
	}
	
	@Override
	public long getItemId(int position) {
		return products.get(position).getProductId();
	}
	
	public void setProductImages(Map<Long, Bitmap> productImages) {
		this.productImages = productImages;
		for (Product product : products) {
			Bitmap pI = productImages.get(product.getProductId());
			if (pI != null) {
				ProductListItem I = productItems.get(product.getProductId());
				if (I != null) {
					I.setImage(pI);
				}
			}
		}
	}

	
	public class ProductListItem extends FrameLayout {
		
		private View rootView = null;
		
		public ProductListItem(Context context, AttributeSet attrs, int defStyle) {
			super (context, attrs, defStyle);
			
			initView();
		}

		public ProductListItem(Context context, AttributeSet attrs) {
			super(context, attrs);
			initView();
		}
		
		
		public ProductListItem(Context context) {
			super(context);
			
			initView();
		}
		
		private void initView() {
			rootView = inflate(getContext(), R.layout.layout_item, null);
			this.addView(rootView);
		}
		
		public void setImage(Bitmap img) {
			ImageView imageView = (ImageView) rootView.findViewById(R.id.productImage);
			imageView.setImageBitmap(img);
		}
		
		public void setProduct(Product product) {
			ImageView imageView = (ImageView) rootView.findViewById(R.id.productImage);
			TextView titleView = (TextView) rootView.findViewById(R.id.productTitle);
			TextView summaryView = (TextView) rootView.findViewById(R.id.productSummary);
			TextView descriptionView = (TextView) rootView.findViewById(R.id.productDescription);
			
			// Get image from HashMap
			if (productImages.containsKey(product.getProductId())) {
				imageView.setImageBitmap(productImages.get(product.getProductId()));
			}
			
			// titleView has the name
			titleView.setText(product.getName());
			
			// The summary has the stock, value, and total value
			summaryView.setText(Html.fromHtml(
					String.format("Stock: <b>%.2f g</b> @ "+
							"<b>$%.2f per g</b> ($%.2f value)",
					product.getStock(), product.getValue(), 
					product.getStock()*product.getValue())));
			
			// The description has symptoms and keywords is they are not null
			String description = "";
			if (product.getKeywords() != null) {
				description += "<b>Keywords:</b>"+product.getKeywords();
			}
			if (product.getSymptoms() != null) {
				if (description.equals("")) {
					description += "<br />";
				}
				description += "<b>Symptoms Treated:</b>"+product.getSymptoms();
			}
			descriptionView.setText(description);
		}

	}

}
