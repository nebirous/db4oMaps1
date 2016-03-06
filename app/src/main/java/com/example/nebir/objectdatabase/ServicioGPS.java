package com.example.nebir.objectdatabase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Date;


public class ServicioGPS extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{
    private GoogleApiClient cliente;
    private LocationRequest peticionLocalizaciones;
    private Posicion position;
    private Date d;
    private Db4O bd;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d( "", "Servicio creado...");
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        position=new Posicion();
        if (status == ConnectionResult.SUCCESS) {
            cliente = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            cliente.connect();
            Toast.makeText(this, "Conecta", Toast.LENGTH_LONG).show();
          }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Servicio iniciado");
        Intent i=new Intent(ServicioGPS.this, ServicioGPS.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Notification.Builder constructorNotificacion = new Notification.Builder(ServicioGPS.this).setSmallIcon(R.drawable.android).setContentTitle("notificación servicio").setContentText("texto servicio").setContentIntent(
                PendingIntent.getActivity(ServicioGPS.this, 0, i, 0));
        NotificationManager gestorNotificacion = (NotificationManager)getSystemService(ServicioGPS.this.NOTIFICATION_SERVICE);
        startForeground(1, constructorNotificacion.build());

        d=new Date();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        Log.d("", "Servicio destruido...");
        System.out.println("SERVICIO DESTRUIDO");
    }

    @Override
    public void onConnected(Bundle bundle) {
        peticionLocalizaciones = new LocationRequest();
        peticionLocalizaciones.setFastestInterval(5000);
        peticionLocalizaciones.setSmallestDisplacement(1);
        peticionLocalizaciones.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(cliente, peticionLocalizaciones, (com.google.android.gms.location.LocationListener) this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        bd = new Db4O(this);
        Posicion position1=new Posicion();
        position1.setLatitud((float) location.getLatitude());
        position1.setLongitud((float) location.getLongitude());
        d.getTime();
        position1.setFecha(d);
        bd.insertar(position1);
        System.out.println("Cambio" +"Latitud:"+position1.getLatitud()+"Longitud:"+position1.getLongitud());
        Toast toast = Toast.makeText(this,"Latitud:"+position1.getLatitud()+"Longitud:"+position1.getLongitud(), Toast.LENGTH_LONG);
        toast.show();
        bd.close();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        cliente = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener
                (this).addApi(LocationServices.API).build();
        cliente.connect();
    }
}
