package com.medixpress.commercial;

import com.medixpress.medixpress_commercial.R;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginFragment extends Fragment {
	   private EditText  username=null;
	   private EditText  password=null;
	   private TextView attempts;
	   private Button login;
	   int counter = 3;
	   
	   @Override
	   public View onCreateView(LayoutInflater inflater, ViewGroup container,
		        Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      View rootView = inflater.inflate(R.layout.activity_main, container);
	      username = (EditText)rootView.findViewById(R.id.editText1);
	      password = (EditText)rootView.findViewById(R.id.editText2);
	      attempts = (TextView)rootView.findViewById(R.id.textView5);
	      
	      attempts.setText(Integer.toString(counter));
	      login = (Button)rootView.findViewById(R.id.button1);
	      
	      return rootView;
	   }

	   public void login(View view){
	      if(username.getText().toString().equals("admin") && 
		      password.getText().toString().equals("admin")){
		      Toast.makeText(getActivity(), "Redirecting...", 
		      Toast.LENGTH_SHORT).show();
		   } else {
		      Toast.makeText(getActivity(), "Wrong Credentials",
		    		  Toast.LENGTH_SHORT).show();
		      attempts.setBackgroundColor(Color.RED);	
		      counter--;
		      attempts.setText(Integer.toString(counter));
		      if(counter==0){
		         login.setEnabled(false);
		      }
		   }
	   }
}