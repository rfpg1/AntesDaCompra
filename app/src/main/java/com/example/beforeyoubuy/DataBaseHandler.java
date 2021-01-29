package com.example.beforeyoubuy;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
public class DataBaseHandler {

    private HashMap<String, Integer> produtos;
    private ArrayList<Produto> listaDeProdutos;

    private final String INSTANCE_FIREBASE_REALTIME = "https://beforeyoubuy-1a840-default-rtdb.europe-west1.firebasedatabase.app/";

    private DatabaseReference database;

    public DataBaseHandler(){
        this.produtos = new HashMap<>();
        listaDeProdutos = new ArrayList<>();
        this.database = FirebaseDatabase.getInstance(INSTANCE_FIREBASE_REALTIME).getReference();
        loadProdutos();
        loadDataBase();
    }

    public DatabaseReference getDatabase(String nome) {
        return FirebaseDatabase.getInstance(INSTANCE_FIREBASE_REALTIME).getReference().child("produtos").child(nome);
    }

    private void loadDataBase() {
        for (String nome : produtos.keySet()) {
            int idProduto = produtos.get(nome);
            Produto produto = new Produto(nome, String.valueOf(idProduto));
            listaDeProdutos.add(produto);
            database.child("produtos").child(nome).setValue(produto);
        }
    }

    private void loadProdutos() {
        produtos.put("araujo", R.drawable.araujo);
        produtos.put("dio", R.drawable.dio);
    }
}
