package com.example.a23520231_lab05_bai2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class PowerStateChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (context == null) return;
        String action = intent.getAction();
        if (action == null) return;

        if (action.equals(Intent.ACTION_POWER_CONNECTED)) {
            Toast.makeText(context, R.string.power_connected, Toast.LENGTH_LONG).show();
        } else if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
            Toast.makeText(context, R.string.power_disconnected, Toast.LENGTH_LONG).show();
        }
    }
}
