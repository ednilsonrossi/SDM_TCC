package br.pro.ednilsonrossi.visitalocal.view;

import android.app.PendingIntent;
import android.content.Intent;
import android.location.Location;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import br.pro.ednilsonrossi.visitalocal.R;
import br.pro.ednilsonrossi.visitalocal.dao.BDLocalController;
import br.pro.ednilsonrossi.visitalocal.model.GeofenceSimples;
import br.pro.ednilsonrossi.visitalocal.model.Local;
import br.pro.ednilsonrossi.visitalocal.model.PontoInteresse;

public class MonitoraLocalActivity extends AppCompatActivity {
    private static final String TAG = "MonitoraLocalActivity";
    private Local mLocal;
    private List<Geofence> mGeofenceList;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GeofencingClient mGeofencingClient;

    private PendingIntent mPendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitora_local);

        recuperaLocal();
        mGeofenceList = new ArrayList<>(mLocal.getPontosInteresse().size());
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mGeofencingClient = LocationServices.getGeofencingClient(this);
        monitorarGeofence();
    }

    private void monitorarGeofence(){
        GeofenceSimples geofenceSimples;

        //Para cada Ponto de Interesse gera-se uma Geofence.
        for(PontoInteresse p : mLocal.getPontosInteresse()){
            geofenceSimples = new GeofenceSimples(
                    p.getNome(),
                    p.getCoordenada().getLatitude(),
                    p.getCoordenada().getLongitude(),
                    GeofenceSimples.DEFAULT_RADIUS_IN_METERS,
                    GeofenceSimples.DEFAULT_GEOFENCE_EXPITARION_TIME,
                    Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT
            );
            mGeofenceList.add(geofenceSimples.toGeofence());
        }

        //Inicia o monitoramento
        mGeofencingClient.addGeofences(getGeofenceRequest(), getPendingIntent())
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "Geofence Adicionada");
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "Erro ao adicionar geofence");
                    }
                });
    }

    private GeofencingRequest getGeofenceRequest(){
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    private PendingIntent getPendingIntent(){
        if(mPendingIntent != null){
            return mPendingIntent;
        }
        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
        mPendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return mPendingIntent;
    }

    private void recuperaLocal(){
        int id;
        Intent in = getIntent();
        id = in.getIntExtra("localId", -1);
        if(id != -1){
            mLocal = new BDLocalController(this).recuperar(id);
            if(mLocal == null){
                finish();
            }
        }else{
            finish();
        }
    }
}
