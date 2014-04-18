package com.medixpress.commercial;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.medixpress.sqlite.DatabaseHelper;
import com.medixpress.sqlite.DemoDatabase;
import com.medixpress.sqlite.Order;
import com.medixpress.sqlite.Product;
import com.medixpress.sqlite.Vendor;

import com.medixpress.medixpress_commercial.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements TabListener {
	private static final String TAG = "MainActivity";
	
	private Vendor vendor = null;
	private List<Product> products = null;
	private List<Order> orders = null;
	private DatabaseHelper helper = null;
	
	private ViewPager viewPager = null;
	private TabsPagerAdapter tabsAdapter = null;
	Map<Long, Bitmap> productImages = new HashMap<Long, Bitmap>();
	
	private boolean loggedIn = true;
	

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // When the tab is selected, switch to the
        // corresponding page in the ViewPager.
        viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.i("MainActivity", "onResume");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.i("MainActivity", "onPause");
	}

//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.i("MainActivity", "onCreate");
		
		// Set up PreferenceHelper
		PreferenceHelper.setContext(this);
		
		//  Asynchronously loads the database
		new InitDatabase().execute(getBaseContext());
		
		// Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        final ActionBar actionBar = getActionBar();
        //actionBar.setDisplayShowTitleEnabled(false);
        
        tabsAdapter = new TabsPagerAdapter(this);
        if (products != null) {
        	tabsAdapter.setProducts(products);
        	Log.i(TAG, "tabsAdapter.setProducts @ onCreate : len(products) = "
        			+ products.size());
        }
        if (orders != null) {
        	tabsAdapter.setOrders(orders);
        	Log.i(TAG, "tabsAdapter.setProducts @ onCreate : len(orders) = "
        			+ orders.size());
        }
        
        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });
 
        viewPager.setAdapter(tabsAdapter);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
 
        // Adding Tabs
        for (String tab_name : tabsAdapter.getTabLabels()) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
	}
	
	public Vendor getVendor() {
		return vendor;
	}
	
	public DatabaseHelper getDatabaseHelper() {
		return helper;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		  if (requestCode == 1) {

		     if(resultCode == RESULT_OK){      
		         vendor = helper.getVendor(data.getLongExtra("VENDORID", 0));          
		     }
		     if (resultCode == RESULT_CANCELED) {    
		         finish();
		     }
		  }
    }//onActivityResult

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
		return true;
	}

	public static class ActionActivity extends PreferenceFragment {

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			// Load preferences from action_prefs.xml
		}
	}
	
	private class InitDatabase extends AsyncTask<Context, Void, DatabaseHelper> {
		
		protected DatabaseHelper doInBackground(Context...context) {
			return new DatabaseHelper(context[0]);
		}
		
		protected void onPostExecute(DatabaseHelper h) {
			helper = h;
			Log.i(TAG, "InitDatabase");
			if (helper == null) {
				  Toast.makeText(getBaseContext(), 
					"Could not reach MediXpress database. "+
					"Please try again later.", Toast.LENGTH_LONG).show();
			} else {
				// Create a fake database. In the future, a vendorId
				// will be obtained from login which will be used
				// throughout the session
				long vendorId;
				if (vendor == null) {
					vendorId = DemoDatabase.createDemoDatabase(helper);
				} else {
					vendorId = vendor.getVendorId();
				}
				// Load the vendor from the database asynchronously
				new InitVendor().execute(vendorId);
			}
		}
	}
	
	private class InitVendor extends AsyncTask<Long, Void, Vendor> {
		
	  protected Vendor doInBackground(Long... vendorId) {
	      helper = new DatabaseHelper(getBaseContext());
	      return helper.getVendor(vendorId[0]);
	  }
	
	  protected void onPostExecute(Vendor dv) {
		  vendor = dv;
		  Log.i(TAG, "InitVendor : " + dv.getName());
		  if (vendor == null) {
			  Toast.makeText(getBaseContext(), 
				"There was an error logging into MediXpress. "+
				"Please try again later.", Toast.LENGTH_LONG).show();
		  } else {
			  getActionBar().setTitle(dv.getName());
			  
			  new InitProducts().execute(vendor);
		  }
	  }
	}
	
	private class InitProducts extends AsyncTask<Vendor, Void, List<Product>> {
		
		  protected List<Product> doInBackground(Vendor... vendor) {
		      return helper.getAllProducts(vendor[0]);
		  }
		
		  protected void onPostExecute(List<Product> p) {
			  products = p;
			  if (products == null) {
				  Toast.makeText(getBaseContext(), 
					"Failed to load products from database. "+
					"Please try again later.", Toast.LENGTH_LONG).show();
			  } else if (tabsAdapter != null) {
		        	Log.i(TAG, "tabsAdapter.setProducts @ InitProducts : len(products) = "
		        			+ products.size());
				  tabsAdapter.setProducts(p);
			  }
			  new InitOrders().execute(vendor);
		  }
		}
	
	private class InitOrders extends AsyncTask<Vendor, Void, List<Order>> {
		
		  protected List<Order> doInBackground(Vendor... vendor) {
		      return helper.getAllOrders(vendor[0]);
		  }
		
		  protected void onPostExecute(List<Order> o) {
			  orders = o;
			  if (orders == null) {
				  Toast.makeText(getBaseContext(), 
					"Failed to load orders from database. "+
					"Please try again later.", Toast.LENGTH_LONG).show();
			  } else if (tabsAdapter != null) {
		        	Log.i(TAG, "tabsAdapter.setOrders @ InitOrders : len(orders) = "
		        			+ orders.size());
				  tabsAdapter.setOrders(o);
			  }
			  new LoadImages().execute(products);
		  }
		}
	
	private Bitmap loadImageFromCache(Long productId) {
		File cacheDir = new File(getCacheDir(), "products");
		cacheDir.mkdirs();
		final String fn = String.format("%016X", productId);
		File[] pFiles = cacheDir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if (pathname.getAbsolutePath().contains(fn)) {
					return true;
				}
				return false;
			}
		});
		if (pFiles.length == 0) {
			return null;
		} else {
			return BitmapFactory.decodeFile(pFiles[0].getAbsolutePath());
		}
	}
	
	public void saveImageToCache(Long productId, Bitmap img) {   
	    File cacheDir = new File(this.getCacheDir(), "products");
	    cacheDir.mkdirs();
	    final String fn = String.format("%016X", productId);
	    File cacheFile = new File(cacheDir, fn+".png");   
	    try {      
            cacheFile.createNewFile();       
            FileOutputStream fos = new FileOutputStream(cacheFile);    
            img.compress(CompressFormat.PNG, 100, fos);       
            fos.flush();       
            fos.close();    
          } catch (Exception e) {       
            Log.e("error", "Error when saving image to cache. ", e);    
          }
	}
	
	private class LoadImages extends AsyncTask<List<Product>, Void, Map<Long, Bitmap>> {
		
		@Override
		protected Map<Long, Bitmap> doInBackground(List<Product>... products) {
			HashMap<Long, Bitmap> rval = new HashMap<Long, Bitmap>();
			for (Product product : products[0]) {
				// First try to load from cache
				Bitmap img = loadImageFromCache(product.getProductId());
				// If not cache then query server for img
				if (img == null) {
					// TODO: Query server for image if it doesn't work
					img = DemoDatabase.createDemoBitmap(getBaseContext(), product.getProductId());
					if (img != null) {
						saveImageToCache(product.getProductId(), img);
					}
				}
				rval.put(product.getProductId(), img);
			}
			return rval;
		}
		
		@Override
		protected void onPostExecute(Map<Long, Bitmap> img_arr) {
			productImages = img_arr;
			if (tabsAdapter != null) {
				tabsAdapter.setProductImages(img_arr);
			}
		}
	}
	
	
}
