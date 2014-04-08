package com.medixpress.commercial;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceHelper {
	private static SharedPreferences prefs = null;
	
	public static void setContext(Context context) {
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	public static String getCurrentSearch() {
		return prefs.getString("search_query", "");
	}
	public static void setCurrentSearch(String query) {
		prefs.edit().putString("search_query", query).commit();
	}
	
	public static String join(String[] entries, String seperator) {
		String rval = "";
		
		for (int i = 0;i < entries.length;i++) {
			rval += entries[i];
			if (i < entries.length-1) rval += seperator;
		}
		
		return rval;
	}
}
