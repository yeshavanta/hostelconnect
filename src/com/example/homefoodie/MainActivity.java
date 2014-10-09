package com.example.homefoodie;

import com.example.homefoodie.R;
import com.facebook.*;
import com.facebook.model.*;
import com.example.homefoodie.details_entry_page;
import com.facebook.widget.*;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends FragmentActivity {
	
	 private static final int LOGINPAGE = 0;
	 private static final int DETAILPAGE = 1;
	 private static final int CHOOSEDISHES = 2;
	 private static final int FRAGMENT_COUNT = CHOOSEDISHES +1;
	 private boolean isResumed = false;
     private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
	
     private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	
	
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    // Only make changes if the activity is visible
	    if (isResumed) {
	        FragmentManager manager = getSupportFragmentManager();
	        // Get the number of entries in the back stack
	        int backStackSize = manager.getBackStackEntryCount();
	        // Clear the back stack
	        for (int i = 0; i < backStackSize; i++) {
	            manager.popBackStack();
	        }
	        if (state.isOpened()) {
	            // If the session state is open:
	            // Show the authenticated fragment
	            showFragment(DETAILPAGE, false);
	        } else if (state.isClosed()) {
	            // If the session state is closed:
	            // Show the login fragment
	            showFragment(LOGINPAGE, false);
	        }
	    }
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	 super.onCreate(savedInstanceState);
    	 uiHelper = new UiLifecycleHelper(this, callback);
    	 uiHelper.onCreate(savedInstanceState);
    	 setContentView(R.layout.activity_main);
         FragmentManager fm = getSupportFragmentManager();
    	 fragments[LOGINPAGE] = fm.findFragmentById(R.id.loginpage);
    	 fragments[DETAILPAGE] = fm.findFragmentById(R.id.detailspage);
    	 fragments[CHOOSEDISHES] = fm.findFragmentById(R.id.choosedishes);
    	 FragmentTransaction transaction = fm.beginTransaction();
    	 for(int i = 0; i < fragments.length; i++) {
    	     transaction.hide(fragments[i]);
    	 }
    	 transaction.commit();
    	 details_entry_page detailentrypagefragment = (details_entry_page) fm.findFragmentById(R.id.detailspage);
         detailentrypagefragment.setDetailsPageNextButtonCallback(new details_entry_page.DetailsPageNextButtonCallback() {
			
			@Override
			public void onNextButtonPressed() {
				showFragment(CHOOSEDISHES, false);
			}
		});
    	 
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
      
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void showFragment(int fragmentIndex, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            if (i == fragmentIndex) {
                transaction.show(fragments[i]);
            } else {
                transaction.hide(fragments[i]);
            }
        }
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
    
    @Override
    public void onResume() {
        super.onResume();
        isResumed = true;
    }
   
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
    
    @Override
    public void onPause() {
        super.onPause();
        isResumed = false;
    }
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        Session session = Session.getActiveSession();
        
        if (session != null && session.isOpened()) {
            // if the session is already open,
            // try to show the selection fragment
            showFragment(DETAILPAGE, false);
        } else {
            // otherwise present the splash screen
            // and ask the person to login.
            showFragment(LOGINPAGE, false);
        }
    }
    
}
