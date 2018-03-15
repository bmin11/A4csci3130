package com.acme.a3csci3130;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Main activity with a list of all data entries
 */
public class MainActivity extends Activity {
    private ListView lvList;
    private FirebaseListAdapter<Business> firebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the app wide shared variables
        MyApplicationData appData = (MyApplicationData)getApplication();

        //Set-up Firebase
        appData.firebaseDBInstance = FirebaseDatabase.getInstance();
        appData.firebaseReference = appData.firebaseDBInstance.getReference("businesses");

        //Get the reference to the UI contents
        lvList = (ListView) findViewById(R.id.lvList);

        //Set up the List View
       firebaseAdapter = new FirebaseListAdapter<Business>(this, Business.class,
                android.R.layout.simple_list_item_1, appData.firebaseReference) {
            @Override
            protected void populateView(View v, Business model, int position) {
                TextView contactName = (TextView)v.findViewById(android.R.id.text1);
                contactName.setText(model.name);
            }
        };
        lvList.setAdapter(firebaseAdapter);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        // onItemClick method is called everytime a user clicks an item on the list
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Business business = firebaseAdapter.getItem(position);
            showDetailView(business);
            }
        });
    }

    /**
     * Method called on a click event of button btCreate
     * @param v
     */
    public void createBusiness(View v) {
        Intent intent=new Intent(this, CreateBusinessActivity.class);
        startActivity(intent);
    }

    /**
     * Methods called on a click even of a list item
     * @param business
     */
    private void showDetailView(Business business) {
        Intent intent = new Intent(this, DetailViewActivity.class);
        intent.putExtra("Business", business);
        startActivity(intent);
    }



}
