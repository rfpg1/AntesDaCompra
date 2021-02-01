package com.example.beforeyoubuy;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
public class DataBaseHandler {

    private final int PEGADA_ECOLOGICA = 0;
    private final String FAVORITO = "favoritos";
    private ArrayList<Produto> listaDeProdutos;
    private ArrayList<Produto> favoritos;

    private final String INSTANCE_FIREBASE_REALTIME = "https://beforeyoubuy-1a840-default-rtdb.europe-west1.firebasedatabase.app/";

    private DatabaseReference database;

    public DataBaseHandler(){
        listaDeProdutos = new ArrayList<>();
        this.database = FirebaseDatabase.getInstance(INSTANCE_FIREBASE_REALTIME).getReference();
        this.favoritos = new ArrayList<>();
        loadProdutos();
    }

    private void loadProdutos() {
        listaDeProdutos.add(new Produto("araujo", R.drawable.araujo, PEGADA_ECOLOGICA));
        listaDeProdutos.add(new Produto("dio", R.drawable.dio, PEGADA_ECOLOGICA));
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap p = (HashMap) snapshot.getValue();
                for(Object o : p.keySet()){
                    if(o.toString().equals(FAVORITO)){
                        HashMap fav = (HashMap) p.get(o);
                        for(Object obj : fav.keySet()){
                            HashMap key = (HashMap) fav.get(obj);
                            String name = (String) key.get("name");
                            int value = ((Long) key.get("id")).intValue();
                            int pegadaEcologica = ((Long) key.get("pegadaEcologica")).intValue();
                            Produto produto = new Produto(name, value, pegadaEcologica);
                            if(!isFavorite(produto.getName()))
                                favoritos.add(produto);
                        }
                    } else if(o.toString().equals("produtos")){
                        //TODO
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Base de dados", "Cancelado");
            }
        });
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
        return favoritos;
    }
}
