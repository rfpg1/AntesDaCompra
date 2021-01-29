package com.example.beforeyoubuy;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

public class ButtonHandler {

    private Button button;

    public ButtonHandler(Button button){
        this.button = button;
        button.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("ResourceAsColor")
    public void newProduct(String name) {
        button.setBackgroundColor(Color.WHITE);
        button.setText(name);
        button.setVisibility(View.VISIBLE);
    }

    public void invisible() {
        button.setVisibility(View.INVISIBLE);
    }
}
