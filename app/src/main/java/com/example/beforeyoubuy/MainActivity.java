package com.example.beforeyoubuy;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    private ImageView imageView;

    private final int REQUEST_EXTERNAL_STORAGE = 1;
    private String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private final String[] PERMISSIONS_CAMERA = {Manifest.permission.CAMERA};
    private final int REQUEST_CAMERA = 2;

    private DataBaseHandler dataBaseHandler;
    private final long ONE_MEGABYTE = 1024 * 1024;


    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /******************************Verifica Permissões***********************************/
        verificaPermissoes();
        verifyStoragePermissions(this);

        /******************************Verifica Permissões***********************************/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        this.mCodeScanner = new CodeScanner(this, scannerView);
        this.dataBaseHandler = new DataBaseHandler(getResources());
        //Caso queira dar scan a mais que um objeto continuamente
        //mCodeScanner.setScanMode(ScanMode.CONTINUOUS);
        imageView = findViewById(R.id.imageView);
        //imageView.setVisibility(View.INVISIBLE);

        this.mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //TODO
                        // Favoritos
                        // Eventualmente ter mais exemplos
                        // Criar um botão por baixo da imagem do produto que leva a uma outra imagem com mais detalhes
                        // Botão canto superior esquerdo tipo definições

                        Log.e("Novo Produto: ", result.getText());
                        getProduto(result.getText());
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
                imageView.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void getProduto(String nome) {
        DatabaseReference db = dataBaseHandler.getDatabase(nome);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, String> p = (HashMap) snapshot.getValue();
                if(p != null){
                    Log.e("FORA DO FOR SNAPSHOT", snapshot.getValue().toString());
                    imageView.setImageResource(Integer.parseInt(p.get("id")));
                    imageView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Novo Produto: ",  "Cancelado");
            }
        });
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

    //Adoro este metodo vao para o caralho!
    //Abre a galeria toda e permite escolher uma imagem
    //Uso isto para descobrir o path dela
    private void escolheFoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("@@@@@@@@@@@@@@@@@",data.getDataString());

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

    //Acho que não preciso!
    private void checkFilePermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = MainActivity.this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += MainActivity.this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {
                this.requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        }else{
            Log.d("MainActivity", "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

}