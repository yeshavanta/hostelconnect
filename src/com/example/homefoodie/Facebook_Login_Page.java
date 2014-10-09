package com.example.homefoodie;


import com.facebook.widget.ProfilePictureView;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Arrays;

import com.facebook.*;
import com.facebook.model.*;
import com.facebook.widget.*;

public class Facebook_Login_Page extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.activity_facebook__login__page, 
	            container, false);
	    LoginButton authButton = (LoginButton)view.findViewById(R.id.facebook_button);
	    authButton.setReadPermissions(Arrays.asList("public_profile","email","user_birthday"));
	    return view;
	}

}
