package com.medixpress.commercial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.medixpress.medixpress_commercial.R;
import com.medixpress.sqlite.Product;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class ReportFragment extends PreferenceFragment {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.report_prefs);
    }
}