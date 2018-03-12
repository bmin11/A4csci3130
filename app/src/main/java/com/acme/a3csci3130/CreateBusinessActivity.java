package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateBusinessActivity extends Activity {

    private EditText etName, etNumber, etAddress, etProvince;
    private Spinner spiPrimaryBusiness;
    private MyApplicationData appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact_acitivity);
        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());

        etName = (EditText) findViewById(R.id.etName);
        etNumber = (EditText) findViewById(R.id.etNumber);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etProvince = (EditText) findViewById(R.id.etProvince);

        spiPrimaryBusiness = (Spinner) findViewById(R.id.spiPrimaryBusiness);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.primary_business_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiPrimaryBusiness.setAdapter(adapter);
    }

    public void submitInfoButton(View v) {
        //each entry needs a unique ID
        String uid = appState.firebaseReference.push().getKey();
        String name = etName.getText().toString();
        String number = etNumber.getText().toString();
        String primaryBusiness = spiPrimaryBusiness.getSelectedItem().toString();
        String address = etAddress.getText().toString();
        String province = etProvince.getText().toString();
        Business business = new Business(uid, name, number, primaryBusiness, address, province);

        appState.firebaseReference.child(uid).setValue(business);

        finish();

    }
}
