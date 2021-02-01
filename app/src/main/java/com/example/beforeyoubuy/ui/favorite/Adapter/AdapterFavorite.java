package com.example.beforeyoubuy.ui.favorite.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beforeyoubuy.R;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.FavoriteViewHolder> {


    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_favorite, parent, false);
        return new FavoriteViewHolder(itemLista);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {

    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder{
        private ImageView imagem;
        private TextView nome;
        private TextView pegada;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imagem = itemView.findViewById(R.id.icon);
            this.nome = itemView.findViewById(R.id.nome);
            this.pegada = itemView.findViewById(R.id.pegada);
        }

        public ImageView getImagem() {
            return imagem;
        }

        public void setImagem(ImageView imagem) {
            this.imagem = imagem;
        }

        public TextView getNome() {
            return nome;
        }

        public void setNome(TextView nome) {
            this.nome = nome;
        }

        public TextView getPegada() {
            return pegada;
        }

        public void setPegada(TextView pegada) {
            this.pegada = pegada;
        }
    }
}
