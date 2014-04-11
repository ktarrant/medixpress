package com.medixpress.commercial;

import com.medixpress.commercial.sqlite.DatabaseHelper;
import com.medixpress.commercial.sqlite.DemoDatabase;
import com.medixpress.commercial.sqlite.Vendor;
import com.medixpress.medixpress_commercial.R;

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

public class MainActivity extends FragmentActivity implements TabListener {
	
	private static final String ACTION_SEARCH = 
			"android.intent.action.SEARCH";
	
	private Vendor vendor = null;
	private DatabaseHelper helper = null;
	
	private ViewPager viewPager = null;
	private TabsPagerAdapter tabsAdapter = null;
	
	@Override
	protected void onNewIntent(Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_SEARCH)) {
			doSearch(intent.getStringExtra(SearchManager.QUERY));
		}
	}
	

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
	private void doSearch(String query) {
		PreferenceHelper.setCurrentSearch(query);
		
		
		Log.i("medixpress", "Search caught!");
		
		viewPager.setCurrentItem(2);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Set up PreferenceHelper
		PreferenceHelper.setContext(this);
		
		helper = new DatabaseHelper(this);
		//DemoDatabase.createDemoDatabase(helper);
		
		// Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        final ActionBar actionBar = getActionBar();
        //actionBar.setDisplayShowTitleEnabled(false);
        
        tabsAdapter = new TabsPagerAdapter(this);
        
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
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (vendor == null) {
		    Intent intent = new Intent(this, LoginActivity.class);
		    startActivityForResult(intent, 1);
		}
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
}
