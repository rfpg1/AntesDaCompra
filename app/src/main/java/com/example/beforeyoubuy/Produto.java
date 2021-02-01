package com.example.beforeyoubuy;

import java.util.Objects;

public class Produto {
    private int pegadaEcologica;
    private String name;
    private int id;

    public Produto(String name, int id, int pegadaEcologica){
        this.name = name;
        this.id = id;
        this.pegadaEcologica = pegadaEcologica;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString(){
        return "ID: " + id + "\nNOME: " + name + "\nPegada Ecologica " + pegadaEcologica;
    }

    public int getPegadaEcologica() {
        return pegadaEcologica;
    }

    public void setPegadaEcologica(int pegadaEcologica) {
        this.pegadaEcologica = pegadaEcologica;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return pegadaEcologica == produto.pegadaEcologica &&
                id == produto.id &&
                Objects.equals(name, produto.name);
    }
}
