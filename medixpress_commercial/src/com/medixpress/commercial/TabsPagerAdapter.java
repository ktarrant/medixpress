package com.medixpress.commercial;

import com.medixpress.medixpress_commercial.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	
	FragmentActivity parentActivity = null;

	public TabsPagerAdapter(FragmentActivity parentActivity) {
		super(parentActivity.getSupportFragmentManager());
		
		this.parentActivity = parentActivity;
	}

	@Override
	public Fragment getItem(int index) {
        switch (index) {
        case 0:
            return new ProductFragment();
        case 1:
            return new SearchFragment();
        }
 
        return null;
	}

	@Override
	public int getCount() {
		return 2;
	}

	public String[] getTabLabels() {
		String[] rval = new String[getCount()];
		
		rval[0] = parentActivity.getString(R.string.products);
		rval[1] = parentActivity.getString(R.string.search);
				
		return rval;
	}
	
}
