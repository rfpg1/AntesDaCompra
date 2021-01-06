package com.example.beforeyoubuy;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
public class DataBaseHandler {

    private Resources resources;
    private HashMap<String, Integer> produtos;
    private ArrayList<Produto> listaDeProdutos;
    private final long ONE_MEGABYTE = 1024 * 1024;

    private Produto lastProduct;

    private final String INSTANCE_FIREBASE_REALTIME = "https://beforeyoubuy-1a840-default-rtdb.europe-west1.firebasedatabase.app/";

    private DatabaseReference database;

    public DataBaseHandler(Resources resources){
        lastProduct = null;
        this.resources = resources;
        this.produtos = new HashMap<>();
        listaDeProdutos = new ArrayList<>();
        Log.e("BD", "Inicio");
        this.database = FirebaseDatabase.getInstance(INSTANCE_FIREBASE_REALTIME).getReference();
        Log.e("BD", "Fim");
        loadProdutos();
        loadDataBase();
    }

    public void addImage(String path, String nome){

    }

    public DatabaseReference getDatabase(String nome) {
        return FirebaseDatabase.getInstance(INSTANCE_FIREBASE_REALTIME).getReference().child("produtos").child(nome);
    }

    public void setDatabase(DatabaseReference database) {
        this.database = database;
    }

    private void loadDataBase() {
        for (String nome : produtos.keySet()) {
            int idProduto = produtos.get(nome);
            Log.e("ID: ", idProduto + "");
            Produto produto = new Produto(nome, String.valueOf(idProduto));
            listaDeProdutos.add(produto);
            database.child("produtos").child(nome).setValue(produto);
        }
    }

    private void loadProdutos() {
        produtos.put("araujo", R.drawable.araujo);
        Log.e("ID: ", R.drawable.araujo + "");
        produtos.put("dio", R.drawable.dio);
        Log.e("ID: ", R.drawable.dio + "");
    }
}
