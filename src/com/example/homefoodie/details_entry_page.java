package com.example.homefoodie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.widget.Button;
import android.view.View.OnClickListener;

import com.facebook.*;
import com.facebook.model.*;

import android.content.Intent;

import com.facebook.widget.*;

public class details_entry_page extends Fragment {
	
	static final int DATE_DIALOG_ID = 0;
	private UiLifecycleHelper uiHelper;
	private static final int REAUTH_ACTIVITY_CODE = 100;
	private ProfilePictureView profilePictureView;
	private TextView userNameView;
	private EditText emailIdView;
	private EditText dateOfBirth;
	private Button nextButton;
	private int mYear,mMonth,mDay;
	
	
	private DetailsPageNextButtonCallback detailsPageNextButtonCallback;

    public interface DetailsPageNextButtonCallback {
        void onNextButtonPressed();
    }
 
    
    public void setDetailsPageNextButtonCallback(DetailsPageNextButtonCallback callback) {
    	detailsPageNextButtonCallback = callback;
    }	
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(final Session session, final SessionState state, final Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	private void makeMeRequest(final Session session) {
	    // Make an API call to get user data and define a 
	    // new callback to handle the response.
	    Request request = Request.newMeRequest(session, 
	            new Request.GraphUserCallback() {
	        @Override
	        public void onCompleted(GraphUser user, Response response) {
	            // If the response is successful
	            if (session == Session.getActiveSession()) {
	                if (user != null) {
	                    // Set the id for the ProfilePictureView
	                    // view that in turn displays the profile picture.
	                    profilePictureView.setProfileId(user.getId());
	                    // Set the Textview's text to the user's name.
	                    userNameView.setText(user.getName());
	                    emailIdView.setText(user.asMap().get("email").toString());
	                    dateOfBirth.setText(user.getBirthday());
	                }
	            }
	            if (response.getError() != null) {
	                // Handle errors, will do so later.
	            }
	        }
	    });
	    request.executeAsync();
	} 
	
	private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
	    if (session != null && session.isOpened()) {
	        // Get the user's data.
	        makeMeRequest(session);
	    }
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    if (requestCode == REAUTH_ACTIVITY_CODE) {
	        uiHelper.onActivityResult(requestCode, resultCode, data);
	    }
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.details_page_fragment, 
	            container, false);
	    profilePictureView = (ProfilePictureView) view.findViewById(R.id.profilePicture);
	    userNameView = (TextView) view.findViewById(R.id.name_text_id);
	    emailIdView = (EditText) view.findViewById(R.id.editText_emailId);
	    dateOfBirth = (EditText)view.findViewById(R.id.date_of_birth);
	    nextButton = (Button)view.findViewById(R.id.Next_button_detail_page);
	    nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (detailsPageNextButtonCallback != null) {
                     detailsPageNextButtonCallback.onNextButtonPressed();
                }
            }
        });

	    
	    return view;
	}

	@Override
	public void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle bundle) {
	    super.onSaveInstanceState(bundle);
	    uiHelper.onSaveInstanceState(bundle);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

}
