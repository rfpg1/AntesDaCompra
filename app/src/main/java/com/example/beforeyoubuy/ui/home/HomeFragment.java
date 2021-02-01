package com.example.beforeyoubuy.ui.home;
//TODO
// Base de dados dos favoritos
// Ir verificar quando se dá scan se ele ta nos favoritos, para ver qual a imagem que aparece
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.beforeyoubuy.ButtonHandler;
import com.example.beforeyoubuy.DataBaseHandler;
import com.example.beforeyoubuy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.util.HashMap;

public class HomeFragment extends Fragment {

    private final String TITULO = "";
    private CodeScanner mCodeScanner;
    private ImageView imageView;
    private DataBaseHandler dataBaseHandler;
    private ButtonHandler buttonHandler;
    private Button button;
    private CodeScannerView scannerView;
    private View root;

    private Activity activity;
    private ImageButton favorite;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);
        activity = getActivity();

        setUpToolbar();

        setUpScanner();

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(" New product scanned: ",result.getText());
                        getProduto(result.getText());
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setVisibility(View.INVISIBLE);
                buttonHandler.invisible();
                favorite.setVisibility(View.INVISIBLE);
                mCodeScanner.startPreview();
            }
        });
        return root;
    }

    private void setUpScanner() {
        this.favorite = root.findViewById(R.id.favorite);
        this.imageView = root.findViewById(R.id.imageView);
        this.dataBaseHandler = new DataBaseHandler();
        button = root.findViewById(R.id.button);
        scannerView = root.findViewById(R.id.scanner_view);
        this.buttonHandler = new ButtonHandler(button, favorite, dataBaseHandler);
        buttonHandler.invisible();
        imageView.setVisibility(View.INVISIBLE);
        mCodeScanner = new CodeScanner(activity, scannerView);
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(TITULO);
    }


    private void getProduto(String produto) {
        int value = dataBaseHandler.getProduto(produto);
        if (value != 0) { // Value == 0 => Não existe produto
            int pegadaEcologica = dataBaseHandler.getPegadaEcologica(produto);
            imageView.setImageResource(value);
            imageView.setVisibility(View.VISIBLE);
            imageView.setBackgroundColor(Color.TRANSPARENT);
            buttonHandler.newProduct(produto, value, pegadaEcologica);
            favorite.setVisibility(View.VISIBLE);
            if(dataBaseHandler.isFavorite(produto)){
                favorite.setImageResource(R.drawable.favorite);
            } else {
                favorite.setImageResource(R.drawable.pre_favorite);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}