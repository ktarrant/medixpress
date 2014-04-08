package com.medixpress.commercial;

import com.medixpress.medixpress_commercial.R;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.app.Activity;
import android.app.ListFragment;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

public class MainActivity extends Activity {
	
	private static final String ACTION_SEARCH = 
			"android.intent.action.SEARCH";
	
	BroadcastReceiver searchReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Intent.ACTION_SEARCH)) {
				doSearch(intent.getStringExtra(SearchManager.QUERY));
			}
		}
	};
	
	// 
	private void doSearch(String query) {
		PreferenceHelper.setCurrentSearch(query);
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Set up PreferenceHelper
		PreferenceHelper.setContext(this);
	}

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
