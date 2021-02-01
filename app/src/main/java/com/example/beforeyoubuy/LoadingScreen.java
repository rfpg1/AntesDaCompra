package com.example.beforeyoubuy;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LoadingScreen extends AppCompatActivity {
    private final int REQUEST_EXTERNAL_STORAGE = 1;
    private String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private final String[] PERMISSIONS_CAMERA = {Manifest.permission.CAMERA};
    private final int REQUEST_CAMERA = 2;

    private final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /******************************Verifica Permissões***********************************/
        verificaPermissoes();
        verifyStoragePermissions(this);

        /******************************Verifica Permissões***********************************/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        /******************************Loading screen****************************************/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loadingScreen = new Intent(LoadingScreen.this, NavigationDrawer.class);
                startActivity(loadingScreen);
                finish();
            }
        }, SPLASH_TIME_OUT);
        /******************************Loading screen****************************************/
    }
    private void verificaPermissoes() {
        verifyStoragePermissions(this);
        verifyCameraPeermissions();
    }

    private void verifyCameraPeermissions() {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, PERMISSIONS_CAMERA, REQUEST_CAMERA);
        }
    }

    private void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}