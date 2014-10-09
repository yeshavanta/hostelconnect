package com.example.homefoodie;

import java.util.Arrays;

import com.facebook.widget.LoginButton;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;
import android.widget.ImageView;

public class Choosedishes extends Fragment {
   
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.activity_choosedishes, 
	            container, false);
	    ExpandableListView choosedishesexpandableList = (ExpandableListView) view.findViewById(R.id.choosedishes);
	    choosedishesexpandableList.setAdapter(new ChooseDishesListAdapter());
	    //choosedishesexpandableList.setOnGroupClickListener(ondishheaderclicklistner);
	    return view;
	}
    public class ChooseDishesListAdapter extends BaseExpandableListAdapter {
    	 
        private String[] groups = { "People Names", "Dog Names", "Cat Names", "Fish Names" };
 
        private String[][] children = {
            { "Arnold", "Barry", "Chuck", "David" },
            { "Ace", "Bandit", "Cha-Cha", "Deuce" },
            { "Fluffy", "Snuggles" },
            { "Goldy", "Bubbles" }
        };
 
        @Override
        public int getGroupCount() {
            return groups.length;
        }
 
        @Override
        public int getChildrenCount(int i) {
            return children[i].length;
        }
 
        @Override
        public Object getGroup(int i) {
            return groups[i];
        }
 
        @Override
        public Object getChild(int i, int i1) {
            return children[i][i1];
        }
 
        @Override
        public long getGroupId(int i) {
            return i;
        }
 
        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }
 
        @Override
        public boolean hasStableIds() {
            return true;
        }
 
        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        	if(view == null)
        	{
        	  Context con =Choosedishes.this.getActivity();
          	  LayoutInflater infalInflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
              view = infalInflater.inflate(R.layout.dishes_style_headers, null);
        	}
        
        	TextView textView = (TextView) view.findViewById(R.id.statesdishheaders);
            textView.setText(getGroup(i).toString());
            if(b)
            {
            	ImageView imageview = (ImageView) view.findViewById(R.id.arrowplaceholder);
                imageview.setImageResource(R.drawable.downarrow);
            }
            else
            {
            	ImageView imageview = (ImageView) view.findViewById(R.id.arrowplaceholder);
                imageview.setImageResource(R.drawable.rightarrow);
            }
            return view;
        }
 
        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        	if(view == null)
        	{	
        	  Context con =Choosedishes.this.getActivity();
        	  LayoutInflater infalInflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
              view = infalInflater.inflate(R.layout.dishes_list, null);
        	}
        	CheckBox textView = (CheckBox)view.findViewById(R.id.dishListItems);
            textView.setText(getChild(i, i1).toString());
            return view;
        }
 
        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
 
    }

}

