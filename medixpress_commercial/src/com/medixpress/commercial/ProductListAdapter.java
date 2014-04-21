package com.medixpress.commercial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.medixpress.medixpress_commercial.R;
import com.medixpress.sqlite.Product;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductListAdapter extends BaseExpandableListAdapter {

	private final static String TAG = "ProductListAdapter"; 
	
	private LayoutInflater inflater = null;
    private Map<Long, Bitmap> productImages = null;
    private Map<Long, ProductListItem> productItems = null;
    private List<String> headerLabels = null;
    private Map<String, List<Product>> headerChildren = null;
	private List<Product> products;
	private Context context;
	
	private View.OnClickListener productPressListener
		= new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
		}
	};
	
	public ProductListAdapter(Context context, List<String> headerLabels, List<Product> products) {
	    this.products = products;
	    this.context = context;
	    this.productImages = new HashMap<Long, Bitmap>();
	    this.productItems = new HashMap<Long, ProductListItem>();
	    inflater = (LayoutInflater) 
	    		context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    this.headerLabels = headerLabels;
	    this.headerChildren = new HashMap<String, List<Product>>();
	    for (String headerLabel : headerLabels) {
	    	this.headerChildren.put(headerLabel, new ArrayList<Product>());
	    }
	    for (Product product : products) {
	    	List<Product> children = this.headerChildren.get(headerLabels.get(product.getTypeId()));
	    	children.add(product);
	    }
	    this.notifyDataSetChanged();
	}
	
	@Override
    public Product getChild(int groupPosition, int childPositon) {
        return this.headerChildren.get(this.headerLabels.get(groupPosition))
                .get(childPositon);//
    }
	
	@Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
 
        final Product product = getChild(groupPosition, childPosition);
        
        //Log.i(TAG, String.format("(%d,%d}: %s", groupPosition, childPosition, product.getName()));
        
        if (convertView == null) {
			convertView = new ProductListItem(context);
        }
		((ProductListItem)convertView).setProduct(product);
		//productView.setOnClickListener(productPressListener);
		Bitmap productImage = productImages.get(product.getProductId());
		if (productImage != null) {
			((ProductListItem)convertView).setImage(productImage);
		}
		productItems.put(product.getProductId(), (ProductListItem) convertView);

        return convertView;
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getChild(groupPosition, childPosition).getProductId();
    }
    
    @Override
    public int getChildrenCount(int groupPosition) {
        return this.headerChildren.get(this.headerLabels.get(groupPosition))
                .size();
    }
 
    @Override
    public String getGroup(int groupPosition) {
        return this.headerLabels.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this.headerLabels.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_list_header, null);
        }
 
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.text_header);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
 
        return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
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
