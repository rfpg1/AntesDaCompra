package com.example.beforeyoubuy;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
public class DataBaseHandler {

    private static DataBaseHandler INSTANCE = null;

    private final String REMOVER = "remover";
    private final int PEGADA_ECOLOGICA = 0;
    private final String FAVORITO = "favoritos";
    private ArrayList<Produto> listaDeProdutos;
    private ArrayList<Produto> favoritos;

    private final String INSTANCE_FIREBASE_REALTIME = "https://beforeyoubuy-1a840-default-rtdb.europe-west1.firebasedatabase.app/";

    private DatabaseReference database;

    public static DataBaseHandler getInstance(){
        if(INSTANCE == null){
            INSTANCE = new DataBaseHandler();
        }
        return INSTANCE;
    }

    protected DataBaseHandler(){
        listaDeProdutos = new ArrayList<>();
        this.database = FirebaseDatabase.getInstance(INSTANCE_FIREBASE_REALTIME).getReference();
        this.favoritos = new ArrayList<>();
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.e("Child added", "");
                Log.e("snapshot", snapshot.toString());
                Log.e("snapshot key", snapshot.getKey());
                Log.e("snapshot value", snapshot.getValue().toString());

                if(snapshot.getKey().equals(FAVORITO)){
                    HashMap key = (HashMap) snapshot.getValue();
                    Log.e("DataSnapShot key", key.toString());
                    for(Object s : key.keySet()){
                        HashMap obj = (HashMap) key.get(s);
                        Log.e("Obj", obj.toString());
                        String name = (String) obj.get("name");
                        int value = ((Long) obj.get("id")).intValue();
                        int pegadaEcologica = ((Long) obj.get("pegadaEcologica")).intValue();
                        Produto produto = new Produto(name, value, pegadaEcologica);
                        if(!isFavorite(produto.getName())){
                            favoritos.add(produto);
                            Log.e("Favorito " , "Adicionado");
                        }
                    }
                } else if(snapshot.getKey().equals("produtos")){
                    //TODO
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //Do nothing
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                //Do nothing
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //Do nothing
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Base de dados", "Cancelado");
            }
        });
        loadProdutos();
        Log.e("Adicionafilho a remover", "REMOVER");
        database.child(REMOVER).setValue(REMOVER);
        database.child(REMOVER).removeValue();
        Log.e("Adicionafilho a remover", "Acabado");
    }

    private void loadProdutos() {
        listaDeProdutos.add(new Produto("araujo", R.drawable.araujo, PEGADA_ECOLOGICA));
        listaDeProdutos.add(new Produto("dio", R.drawable.dio, PEGADA_ECOLOGICA));
    }

    /**
     *
     * @param nome
     * @param id
     * @param pegadaEcologica
     * @requires nao seja favorito!
     */
    public void addFavorite(String nome, int id, int pegadaEcologica){
        Log.e("Adicionado Favorito", nome);
        favoritos.add(new Produto(nome, id, pegadaEcologica));
        database.child(FAVORITO).child(nome).setValue(new Produto(nome, id, pegadaEcologica));
    }

    public boolean isFavorite(String favorito) {
        for(Produto p : favoritos){
            Log.e("Produto", p.toString());
            if(p.getName().equals(favorito)){
                return true;
            }
        }
        return false;
    }

    public void removeFavorite(String favorito) {
        Log.e("Removido Favorito", favorito);
        database.child(FAVORITO).child(favorito).removeValue();
        removeProduto(favorito);
    }

    private void removeProduto(String favorito) {
        Produto p = getP(favorito);
        if(p != null){
            favoritos.remove(p);
        }
    }

    private Produto getP(String favorito) {
        for(Produto p : listaDeProdutos){
            if(p.getName().equals(favorito)){
                return p;
            }
        }
        return null;
    }

    public int getProduto(String produto) {
        for(Produto p : listaDeProdutos){
            if(p.getName().equals(produto)){
                return p.getId();
            }
        }
        return 0;
    }

    public int getPegadaEcologica(String produto){
        for(Produto p : listaDeProdutos){
            if(p.getName().equals(produto)){
                return p.getPegadaEcologica();
            }
        }
        return 0;
    }

    public ArrayList<Produto> getFavoritos() {
        Log.e("Favoritos", favoritos.toString());
        return favoritos;
    }
}
