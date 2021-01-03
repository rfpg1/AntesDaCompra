package com.example.beforeyoubuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.imageView = (ImageView) findViewById(R.id.imageView);
        this.imageView.setVisibility(View.INVISIBLE);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        this.mCodeScanner = new CodeScanner(this, scannerView);
        //Caso queira dar scan a mais que um objeto continuamente
        //mCodeScanner.setScanMode(ScanMode.CONTINUOUS);
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

                        if(result.getText().equals("google")){
                            Toast.makeText(MainActivity.this, result.getText(), Toast.LENGTH_LONG).show();
                            imageView.setVisibility(View.VISIBLE);
                            imageView.setImageResource(R.drawable.araujo);
                        } else if(result.getText().equals("firefox")){
                            Toast.makeText(MainActivity.this, result.getText(), Toast.LENGTH_LONG).show();
                            imageView.setVisibility(View.VISIBLE);
                            imageView.setImageResource(R.drawable.dio);
                        }
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

    private byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
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