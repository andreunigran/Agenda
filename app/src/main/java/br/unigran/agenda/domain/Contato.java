package br.unigran.agenda.domain;

import android.graphics.Bitmap;

public class Contato {
    private String nome;
    private String numeroTelefone;
    private Bitmap foto;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Bitmap getFoto() {
        return foto;
    }
    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }
}
