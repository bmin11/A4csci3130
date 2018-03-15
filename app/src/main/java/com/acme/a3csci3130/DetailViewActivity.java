package com.acme.a3csci3130;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Activity for editing or deleting business entry
 */
public class DetailViewActivity extends Activity {

    private EditText etName, etNumber, etAddress, etProvince;
    private Spinner spiPrimaryBusiness;
    Business receivedBusinessInfo;
    private MyApplicationData appState;
    private String mUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        receivedBusinessInfo = (Business)getIntent().getSerializableExtra("Business");

        appState = ((MyApplicationData) getApplicationContext());

        etName = (EditText) findViewById(R.id.etName);
        etNumber = (EditText) findViewById(R.id.etNumber);
        spiPrimaryBusiness = (Spinner) findViewById(R.id.spiPrimaryBusiness);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etProvince = (EditText) findViewById(R.id.etProvince);

        spiPrimaryBusiness = (Spinner) findViewById(R.id.spiPrimaryBusiness);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.primary_business_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiPrimaryBusiness.setAdapter(adapter);

        //If a business object was passed down by intent from the main activity, populate all fields with given info
        if(receivedBusinessInfo != null){
            etName.setText(receivedBusinessInfo.name);
            etNumber.setText(receivedBusinessInfo.number);
            etAddress.setText(receivedBusinessInfo.address);
            etProvince.setText(receivedBusinessInfo.province);

            mUid = receivedBusinessInfo.uid;

            spiPrimaryBusiness.setSelection(adapter.getPosition(receivedBusinessInfo.primaryBusiness));
        }
    }

    /**
     * Called on click listener for button btSubmit
     * Takes string value from each text views and create a new Business object to post it to the firebase
     * Return back to main activity at the end of the method
     * @param v
     */
    public void updateContact(View v){
        String uid = mUid;
        String name = etName.getText().toString();
        String number = etNumber.getText().toString();
        String primaryBusiness = spiPrimaryBusiness.getSelectedItem().toString();
        String address = etAddress.getText().toString();
        String province = etProvince.getText().toString();
        Business business = new Business(uid, name, number, primaryBusiness, address, province);

        appState.firebaseReference.child(uid).setValue(business);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Called on click listener for button btDelete
     * Set the value to null at the given id
     * Return back to main activity at the end of the method
     * @param v
     */
    public void eraseContact(View v)
    {
        appState.firebaseReference.child(mUid).setValue(null);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
