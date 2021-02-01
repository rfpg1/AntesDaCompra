package com.example.beforeyoubuy;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ButtonHandler {

    private Button button;
    private ImageButton imageButton;
    private DataBaseHandler dataBaseHandler;
    private String produto;
    private int id;
    private int pegadaEcologica;

    public ButtonHandler(Button button, ImageButton imageButton, DataBaseHandler dataBaseHandler){
        this.button = button;
        button.setVisibility(View.INVISIBLE);
        this.imageButton = imageButton;
        this.dataBaseHandler = dataBaseHandler;
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataBaseHandler.isFavorite(produto)){
                    dataBaseHandler.removeFavorite(produto);
                    imageButton.setImageResource(R.drawable.pre_favorite);
                } else {
                    dataBaseHandler.addFavorite(produto, id, pegadaEcologica);
                    imageButton.setImageResource(R.drawable.favorite);
                }
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    public void newProduct(String produto, int id, int pegadaEcologica) {
        button.setBackgroundColor(Color.WHITE);
        button.setText(produto);
        button.setVisibility(View.VISIBLE);
        this.produto = produto;
        this.id = id;
        this.pegadaEcologica = pegadaEcologica;
    }

    public void invisible() {
        this.button.setVisibility(View.INVISIBLE);
        this.imageButton.setVisibility(View.INVISIBLE);
    }
}
