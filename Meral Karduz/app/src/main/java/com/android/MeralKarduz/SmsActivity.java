package com.android.MeralKarduz;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.MeralKarduz.baglanti.kisilerBrief;
import com.android.MeralKarduz.model.kisilerGet;
import com.android.MeralKarduz.database.kisilerDB;

import java.util.ArrayList;
import java.util.List;

public class SmsActivity extends Activity {

    private static final int PERMISSIONS_REQUEST_SEND_SMS = 200;

    List<kisilerGet> contactListToView;
    String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        EditText mesajBasligi = (EditText) findViewById(R.id.editTextMesajBasligi);
        EditText mesajIcerigi = (EditText) findViewById(R.id.editTextMesajIcerigi);
        Button buttonMesajGonder = (Button) findViewById(R.id.buttonMesajGonder);
        RadioGroup rg = (RadioGroup) findViewById(R.id.radio_group);
        groupName = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();

        ListView listView = (ListView) findViewById(R.id.briefContactsListView);
        kisilerDB dbService = new kisilerDB(getApplicationContext());
        List<kisilerGet> contactList = dbService.getContactList();
        setAdapter(listView, contactList);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                groupName = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                setAdapter(listView, contactList);
            }
        });

        buttonMesajGonder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSIONS_REQUEST_SEND_SMS);
                } else {
                    SmsManager smsManager = SmsManager.getDefault();
                    for (kisilerGet contact : contactListToView) {
                        smsManager.sendTextMessage(contact.getPhoneNumber(), null, mesajBasligi.getText().toString() + "\n" + mesajIcerigi.getText().toString(), null, null);
                    }
                    Toast.makeText(getApplicationContext(), "SMS " + groupName + " grubuna gönderildi.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setAdapter(ListView listView, List<kisilerGet> contactList) {
        contactListToView = getContactsByGroupName(contactList, groupName);
        kisilerBrief contactAdaptor = new kisilerBrief(this, contactListToView);
        listView.setAdapter(contactAdaptor);
    }

    private List<kisilerGet> getContactsByGroupName(List<kisilerGet> contacts, String groupname) {
        List<kisilerGet> list = new ArrayList<>();
        for (kisilerGet contact : contacts) {
            if (groupname.equalsIgnoreCase(contact.getGroupName())) {
                list.add(contact);
            }
        }
        return list;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        startActivity(new Intent(this, this.getClass()));
    }
}
