package com.acme.a3csci3130;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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

        if(receivedBusinessInfo != null){
            etName.setText(receivedBusinessInfo.name);
            etNumber.setText(receivedBusinessInfo.number);
            etAddress.setText(receivedBusinessInfo.address);
            etProvince.setText(receivedBusinessInfo.province);

            mUid = receivedBusinessInfo.uid;

            int pbID = adapter.getPosition(receivedBusinessInfo.primaryBusiness);
            spiPrimaryBusiness.setSelection(pbID);
        }
    }

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

    public void eraseContact(View v)
    {
        appState.firebaseReference.child(mUid).setValue(null);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
