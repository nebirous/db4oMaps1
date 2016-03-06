package com.example.nebir.objectdatabase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class MechaServicio extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final Context c = context;
        Intent serviceIntent = new Intent(context,ServicioGPS.class);
        context.startService(serviceIntent);
    }
}