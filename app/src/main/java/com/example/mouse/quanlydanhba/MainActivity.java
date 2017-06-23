package com.example.mouse.quanlydanhba;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    private ContactAdapter adapter;
    private ArrayList<Contact> arrayContact;
    private EditText edtName;
    private EditText edtPhoneNumber;
    private Button btnAddContact;
    private RadioButton rbtnMale;
    private RadioButton rbtnFemale;
    private ListView lvContact;
    private Button btnCall;
    private Button btnSendmess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWidget();
        arrayContact = new ArrayList<>();
        adapter = new ContactAdapter(this,R.layout.item_contact,arrayContact);
        lvContact.setAdapter(adapter);
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialogConfirm(position);
            }
        });
    }

    public void setWidget(){
        edtName = (EditText) findViewById(R.id.edt_name);
        edtPhoneNumber = (EditText) findViewById(R.id.edt_phoneNumber);
        lvContact = (ListView) findViewById(R.id.lv_contact);
        btnAddContact = (Button) findViewById(R.id.btn_addContact);
        rbtnFemale = (RadioButton) findViewById(R.id.rbtn_female);
        rbtnMale = (RadioButton) findViewById(R.id.rbtn_male);
    }

    public void addContact(View view){
        if(view.getId() == R.id.btn_addContact){
            String name = edtName.getText().toString().trim();
            String number = edtPhoneNumber.getText().toString().trim();
            boolean isMale =true;
            if(rbtnMale.isChecked()){
                isMale =true;
            }else{
                isMale = false;
            }
            if(TextUtils.isEmpty(name)|| TextUtils.isEmpty(number)){
                Toast.makeText(this,  "Please Input Number or Name",Toast.LENGTH_LONG).show();
            }else{
                Contact contact = new Contact(isMale,name,number);
                arrayContact.add(contact);
            }
            adapter.notifyDataSetChanged();
        }
    }

//    private void checkAndRequestPermission(){
//        String [] permissions = new String[]{
//                android.Manifest.permission.CALL_PHONE,
//                android.Manifest.permission.SEND_SMS
//        };
//        List<String> listPermissionNeeded = new ArrayList<>();
//        for(String permission : permissions){
//            if(ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
//                listPermissionNeeded.add(permission);
//            }
//        }
//        if(!listPermissionNeeded.isEmpty()){
//            ActivityCompat.requestPermissions(this,listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]),1);
//        }
//    }

    public void showDialogConfirm(final int position){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_layout);
        btnCall = (Button) dialog.findViewById(R.id.btn_call);
        btnSendmess = (Button) dialog.findViewById(R.id.btn_sendmess);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentCall(position);
            }
        });

        btnSendmess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentSendMess(position);
            }
        });
        dialog.show();
    }

    private void intentCall(int position){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+arrayContact.get(position).getmNumber()));
        startActivity(intent);
    }

    private void intentSendMess(int position){
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("sms:"+arrayContact.get(position).getmNumber()));
        startActivity(intent);
    }
}
