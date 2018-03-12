package com.acme.a3csci3130;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class DetailViewActivity extends Activity {

    private EditText etName, etNumber, etPrimaryBusiness, etAddress, etProvince;
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
        etPrimaryBusiness = (EditText) findViewById(R.id.etPrimaryBusiness);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etProvince = (EditText) findViewById(R.id.etProvince);

        if(receivedBusinessInfo != null){
            etName.setText(receivedBusinessInfo.name);
            etNumber.setText(receivedBusinessInfo.number);
            etPrimaryBusiness.setText(receivedBusinessInfo.primaryBusiness);
            etAddress.setText(receivedBusinessInfo.address);
            etProvince.setText(receivedBusinessInfo.province);
            mUid = receivedBusinessInfo.uid;
        }
    }

    public void updateContact(View v){
        String uid = mUid;
        String name = etName.getText().toString();
        String number = etNumber.getText().toString();
        String primaryBusiness = etPrimaryBusiness.getText().toString();
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
