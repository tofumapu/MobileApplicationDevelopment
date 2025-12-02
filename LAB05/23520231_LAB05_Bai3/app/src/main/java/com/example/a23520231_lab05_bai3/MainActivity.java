package com.example.a23520231_lab05_bai3;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_SMS = 1;
    private ReentrantLock reentrantLock;
    private Switch swAutoResponse;
    private LinearLayout llButtons;
    private Button btnSafe, btnMayday;
    private ArrayList<String> requesters;
    private ArrayAdapter<String> adapter;
    private ListView lvMessages;
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter smsFilter;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private final String AUTO_RESPONSE = "auto_response";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewsByIds();
        initVariables();
        handleOnClickListeners();

        requestSmsPermission();
        initSmsReceiver();
    }

    private void findViewsByIds() {
        swAutoResponse = findViewById(R.id.sw_auto_response);
        llButtons = findViewById(R.id.ll_buttons);
        lvMessages = findViewById(R.id.lv_messages);
        btnSafe = findViewById(R.id.btn_safe);
        btnMayday = findViewById(R.id.btn_mayday);
    }

    private void initVariables() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();
        reentrantLock = new ReentrantLock();
        requesters = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, requesters);
        lvMessages.setAdapter(adapter);

        boolean autoResponse = sharedPreferences.getBoolean(AUTO_RESPONSE, false);
        swAutoResponse.setChecked(autoResponse);
        if (autoResponse) {
            llButtons.setVisibility(View.GONE);
        }
    }

    private void handleOnClickListeners() {
        btnSafe.setOnClickListener(v -> respond(true));
        btnMayday.setOnClickListener(v -> respond(false));

        swAutoResponse.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                llButtons.setVisibility(View.GONE);
            } else {
                llButtons.setVisibility(View.VISIBLE);
            }
            editor.putBoolean(AUTO_RESPONSE, isChecked);
            editor.apply();
        });
    }

    private void respond(String to, String response) {
        reentrantLock.lock();
        try {
            requesters.remove(to);
            adapter.notifyDataSetChanged();
        } finally {
            reentrantLock.unlock();
        }

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(to, null, response, null, null);
    }

    public void respond(boolean ok) {
        String okString = getString(R.string.i_am_safe_and_well_worry_not);
        String notOkString = getString(R.string.tell_my_mother_i_love_her);
        String outputString = ok ? okString : notOkString;
        ArrayList<String> requestersCopy = new ArrayList<>(requesters);
        for (String to : requestersCopy) {
            respond(to, outputString);
        }
    }

    public void processSms(Intent intent) {
        String queryString = "Are you OK?".toLowerCase();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus == null) return;

            HashMap<String, String> messages = new HashMap<>();
            for (Object pdu : pdus) {
                SmsMessage smsMessage;
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    String format = bundle.getString("format");
                    smsMessage = SmsMessage.createFromPdu((byte[]) pdu, format);
                } else {
                    smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                }
                String sender = smsMessage.getOriginatingAddress();
                if (sender == null) continue;
                String messageBody = messages.get(sender);
                if (messageBody == null) messageBody = "";
                messages.put(sender, messageBody + smsMessage.getMessageBody());
            }

            ArrayList<String> addresses = new ArrayList<>();
            for (String sender : messages.keySet()) {
                if (messages.get(sender).toLowerCase().contains(queryString)) {
                    addresses.add(sender);
                }
            }

            if (!addresses.isEmpty()) {
                reentrantLock.lock();
                try {
                    boolean changed = false;
                    for (String address : addresses) {
                        if (!requesters.contains(address)) {
                            requesters.add(address);
                            changed = true;
                        }
                    }
                    if (changed) {
                        runOnUiThread(() -> adapter.notifyDataSetChanged());
                    }
                } finally {
                    reentrantLock.unlock();
                }

                if (swAutoResponse.isChecked()) {
                    respond(true);
                }
            }
        }
    }

    private void initSmsReceiver() {
        smsFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                processSms(intent);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) {
            if (broadcastReceiver == null) initSmsReceiver();
            registerReceiver(broadcastReceiver, smsFilter);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (broadcastReceiver != null) {
            try {
                unregisterReceiver(broadcastReceiver);
            } catch (IllegalArgumentException e) {
            }
        }
    }

    private void requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS}, PERMISSIONS_REQUEST_SMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Đã cấp quyền nhận SMS!", Toast.LENGTH_SHORT).show();
                if (broadcastReceiver == null) initSmsReceiver();
                registerReceiver(broadcastReceiver, smsFilter);
            } else {
                Toast.makeText(this, "Quyền SMS là cần thiết để ứng dụng hoạt động!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
