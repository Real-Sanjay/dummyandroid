package com.codeknight.sjapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class  message_activity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_SEND_SMS = 1;
    private static final int PERMISSION_REQUEST_RECEIVE_SMS = 2;

    private EditText editTextPhoneNumber, editTextMessage;
    private TextView textViewReceivedSMS;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextMessage = findViewById(R.id.editTextMessage);
        textViewReceivedSMS = findViewById(R.id.textViewReceivedSMS);

        // Request permissions if not granted
        checkAndRequestPermission(Manifest.permission.SEND_SMS, PERMISSION_REQUEST_SEND_SMS);
        checkAndRequestPermission(Manifest.permission.RECEIVE_SMS, PERMISSION_REQUEST_RECEIVE_SMS);

        // Register SMS receiver
        registerReceiver(smsReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
    }

    // Check and request runtime permissions
    private void checkAndRequestPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_SEND_SMS || requestCode == PERMISSION_REQUEST_RECEIVE_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Send SMS button click handler
    public void sendSMS(View view) {
        String phoneNumber = editTextPhoneNumber.getText().toString();
        String message = editTextMessage.getText().toString();

        if (!phoneNumber.isEmpty() && !message.isEmpty()) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                Toast.makeText(this, "SMS sent", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission to send SMS not granted", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter phone number and message", Toast.LENGTH_SHORT).show();
        }
    }

    // SMS BroadcastReceiver
    private final BroadcastReceiver smsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Object[] pduObjects = (Object[]) intent.getExtras().get("pdus");
                if (pduObjects != null) {
                    for (Object pdu : pduObjects) {
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                        String senderPhoneNumber = smsMessage.getDisplayOriginatingAddress();
                        String messageBody = smsMessage.getMessageBody();

                        String receivedSMS = "From: " + senderPhoneNumber + "\nMessage: " + messageBody;
                        textViewReceivedSMS.setText(receivedSMS);
                    }
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister SMS receiver
        unregisterReceiver(smsReceiver);
    }
}