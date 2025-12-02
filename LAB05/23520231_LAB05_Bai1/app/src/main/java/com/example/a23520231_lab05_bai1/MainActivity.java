package com.example.a23520231_lab05_bai1;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter filter;
    private static final int SMS_PERMISSION_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initBroadcastReceiver();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, SMS_PERMISSION_REQUEST_CODE);
        }
    }

    public void processReceive(Context context, Intent intent) {
        Toast.makeText(context, getString(R.string.you_have_a_new_message),
                Toast.LENGTH_LONG).show();
        TextView tvContent = findViewById(R.id.tv_content);

        final String SMS_EXTRA = "pdus";
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] messages = (Object[]) bundle.get(SMS_EXTRA);
            StringBuilder sms = new StringBuilder();
            if (messages != null) {
                SmsMessage smsMsg;
                for (int i = 0; i < messages.length; i++) {
                    if (android.os.Build.VERSION.SDK_INT >= 23) {
                        String format = bundle.getString("format");
                        smsMsg = SmsMessage.createFromPdu((byte[]) messages[i], format);
                    } else {
                        smsMsg = SmsMessage.createFromPdu((byte[]) messages[i]);
                    }
                    
                    if (smsMsg != null) {
                        String msgBody = smsMsg.getMessageBody();
                        String address = smsMsg.getDisplayOriginatingAddress();
                        sms.append(address).append(":\n").append(msgBody).append("\n");
                    }
                }
                tvContent.setText(sms.toString());
            }
        }
    }

    private void initBroadcastReceiver() {
        filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                processReceive(context, intent);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) {
            if (broadcastReceiver == null) initBroadcastReceiver();
            registerReceiver(broadcastReceiver, filter);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Đã cấp quyền nhận SMS!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Đã từ chối quyền nhận SMS!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}