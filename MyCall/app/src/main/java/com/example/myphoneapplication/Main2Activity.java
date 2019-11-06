package com.example.myphoneapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private EditText txtPhone;
    private EditText txtMessage;
    private Button callBtn;
    private Button sendTxtBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txtPhone = (EditText)findViewById(R.id.phoneInput);
        txtMessage = (EditText)findViewById(R.id.messageText);
        callBtn = (Button)findViewById(R.id.callButton);
        sendTxtBtn = (Button)findViewById(R.id.textButton);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhoneNumber();
            }
        });
        sendTxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendText();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber();
            }
        }
    }

    public void callPhoneNumber() {
        try {
            if(Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Main2Activity.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }
            }
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + txtPhone.getText().toString()));
            startActivity(callIntent);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendText() {
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + txtPhone.getText().toString()));
            intent.putExtra("sms_body", txtMessage.getText().toString());
            startActivity(intent);
        }
        catch (Exception e){
            Toast.makeText(Main2Activity.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
        }
    }

}
