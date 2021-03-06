package com.medixpress.commercial;

import com.medixpress.medixpress_commercial.R;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
   private EditText  username=null;
   private EditText  password=null;
   private TextView attempts;
   private Button login;
   int counter = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login);
		
	      username = (EditText)findViewById(R.id.editText1);
	      password = (EditText)findViewById(R.id.editText2);
	      attempts = (TextView)findViewById(R.id.textView5);
	      
	      attempts.setText(Integer.toString(counter));
	      login = (Button)findViewById(R.id.button1);
	}
	
	public void login(View view){
	      if(username.getText().toString().equals("admin") && 
		      password.getText().toString().equals("admin")){
		      Toast.makeText(this, "Welcome!", 
		    		  Toast.LENGTH_SHORT).show();
		      
		      // TODO: Get vendorId from credentials
		      long vendorId = 1;
		      
		      Intent returnIntent = new Intent();
		      returnIntent.putExtra("VENDORID", vendorId);
		      setResult(RESULT_OK, returnIntent);     
		      finish();
		   } else {
		      Toast.makeText(this, "Wrong Credentials",
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
