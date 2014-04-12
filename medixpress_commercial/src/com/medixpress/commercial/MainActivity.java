package com.medixpress.commercial;

import java.util.List;

import com.medixpress.sqlite.DatabaseHelper;
import com.medixpress.sqlite.DemoDatabase;
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
	private DatabaseHelper helper = null;
	
	private ViewPager viewPager = null;
	private TabsPagerAdapter tabsAdapter = null;
	
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
//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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
	protected void onResume() {
		super.onResume();
		/*
		if (vendor == null) {
		    Intent intent = new Intent(this, LoginActivity.class);
		    startActivityForResult(intent, 1);
		}*/
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
				long vendorId = DemoDatabase.createDemoDatabase(helper);
				
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
		  }
		}
}
