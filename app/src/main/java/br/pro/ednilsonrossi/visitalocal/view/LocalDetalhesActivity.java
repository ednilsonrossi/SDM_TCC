package br.pro.ednilsonrossi.visitalocal.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.FileOutputStream;

import br.pro.ednilsonrossi.visitalocal.BuildConfig;
import br.pro.ednilsonrossi.visitalocal.R;
import br.pro.ednilsonrossi.visitalocal.dao.BDLocalController;
import br.pro.ednilsonrossi.visitalocal.model.Coordenada;
import br.pro.ednilsonrossi.visitalocal.model.Local;

public class LocalDetalhesActivity extends AppCompatActivity {
    //Atributos para foto----------------------------
    private final int REQUEST_CODE_FOTO = 256;
    private Bitmap fotoBitmap;

    //Atributos para audio---------------------------
    private final int REQUEST_CODE_AUDIO = 512;
    private Uri audioUri;

    //Atributos de tela------------------------------
    private EditText nomeLocalEditText;
    private TextView latitudeTextView;
    private TextView longitudeTextView;
    private Button obterCoordenadaButton;
    private Button salvarLocalButton;
    private Button tirarFotoButton;
    private Button gravarAudioButton;
    private ImageView fotoImageView;

    //Atributos de localização-----------------------
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 128;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location lastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_detalhes);

        //Inicialização de atributos-------------------------------------------------------
        fotoBitmap = null;
        audioUri = null;


        //Tela-----------------------------------------------------------------------------
        nomeLocalEditText = (EditText) findViewById(R.id.edittext_nome_local);
        latitudeTextView = (TextView) findViewById(R.id.textview_latitude_local);
        longitudeTextView = (TextView) findViewById(R.id.textview_longitude_local);
        obterCoordenadaButton = (Button) findViewById(R.id.button_obtem_coordenadas_local);
        obterCoordenadaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obterCoordenada();
            }
        });
        salvarLocalButton = (Button) findViewById(R.id.button_salvar_local);
        salvarLocalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarLocal();
            }
        });
        fotoImageView = (ImageView) findViewById(R.id.imageview_foto_espaco);
        tirarFotoButton = (Button) findViewById(R.id.button_tirar_foto_local);
        tirarFotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto();
            }
        });
        gravarAudioButton = (Button) findViewById(R.id.button_gravar_audio_local);
        gravarAudioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gravarAudio();
            }
        });

        //Localização----------------------------------------------------------------------
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }


    private void salvarLocal(){
        if(!nomeLocalEditText.getText().toString().isEmpty()){
            String pathFoto = gravarArquivoFoto(nomeLocalEditText.getText().toString());
            String pathAudio = audioUri.toString();
            Local local = new Local(
                    nomeLocalEditText.getText().toString(),
                    new Coordenada(lastLocation.getLatitude(), lastLocation.getLongitude()),
                    pathFoto,
                    pathAudio);

            if(new BDLocalController(this).criar(local)){
                showSnackbar(getString(R.string.local_salvo));
            }else{
                showSnackbar(getString(R.string.local_erro_salvar));
            }
            finish();
        }else{
            showSnackbar(getString(R.string.local_sem_nome));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_FOTO:
                if(data != null){
                    Bundle bundle = data.getExtras();
                    if(bundle != null){
                        fotoBitmap = (Bitmap) bundle.get("data");
                        fotoImageView.setImageBitmap(fotoBitmap);
                    }
                }
                break;
            case REQUEST_CODE_AUDIO:
                if(resultCode == RESULT_OK){
                    audioUri = data.getData();
                    MediaPlayer mediaPlayer = MediaPlayer.create(this, audioUri);
                    mediaPlayer.start();
                }
                break;
        }
    }


    //Métodos para trabalhar com a foto----------------------------------------------------
    private void tirarFoto(){
        Intent chamaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(chamaCamera, REQUEST_CODE_FOTO);
    }



    private String gravarArquivoFoto(String localNome){
        final String thePath = "/imgsApp";
        File fotosDiretorio = new File(Environment.getExternalStorageDirectory() + thePath);
        fotosDiretorio.mkdir();

        File fotoArquivo = new File(Environment.getExternalStorageDirectory() + thePath + "/" + localNome + ".png");
        try {
            FileOutputStream outputStream = new FileOutputStream(fotoArquivo);
            fotoBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();

        }catch (Exception e){

        }
        return fotoArquivo.getAbsolutePath();
    }


    //Métodos para trabalhar com o audio       ----------------------------------------------------
    private void gravarAudio(){
        Intent chamarGravador = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(chamarGravador, REQUEST_CODE_AUDIO);
    }




    //Métodos para trabalhar com localização ------------------------------------------------------

    @SuppressLint("MissingPermission")
    private void obterCoordenada(){
        if(!verificaPermissao()){
            solicitaPermissao();
            return;
        }

        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null){
                            lastLocation = location;
                            coordenadaObtida();
                        }
                    }
                });
    }

    private void coordenadaObtida(){
        if(lastLocation != null) {
            latitudeTextView.setText(String.valueOf(lastLocation.getLatitude()));
            longitudeTextView.setText(String.valueOf(lastLocation.getLongitude()));
            salvarLocalButton.setEnabled(true);
        }
    }



    //Métodos para trabalhar com as permissões de localização --------------------------------------

    private boolean verificaPermissao(){
        int status = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        return status == PackageManager.PERMISSION_GRANTED;
    }

    private void solicitaPermissao(){
        boolean shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if(shouldProvideRationale){
            showSnackbar(R.string.permission_rationale,
                    android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(LocalDetalhesActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        }else{
            ActivityCompat.requestPermissions(LocalDetalhesActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_PERMISSIONS_REQUEST_CODE){
            if(grantResults.length > 0){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Permissao forncecida
                }else{
                    //Permissao negada
                    showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Build intent that displays the App settings screen.
                                    Intent intent = new Intent();
                                    intent.setAction(
                                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package",
                                            BuildConfig.APPLICATION_ID, null);
                                    intent.setData(uri);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            });
                }
            }
        }
    }

    private void showSnackbar(final String text) {
        View container = findViewById(android.R.id.content);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

}
