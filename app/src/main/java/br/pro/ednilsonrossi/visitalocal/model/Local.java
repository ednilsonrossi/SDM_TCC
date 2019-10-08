package br.pro.ednilsonrossi.visitalocal.model;

import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

public class Local {
    private int id;
    private String nome;
    private Coordenada coordenada;
    private String imagem;
    private String audio;
    private List<PontoInteresse> pontosInteresse;

    public Local() {
        init();
    }

    public Local(String nome, Coordenada coordenada, String imagemPath, String audioPath) {
        init();
        this.nome = nome;
        this.coordenada = coordenada;
        this.imagem = imagemPath;
        this.audio = audioPath;
    }

    private void init(){
        id = -1;
        imagem = null;
        audio = null;
        pontosInteresse = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(Coordenada coordenada) {
        this.coordenada = coordenada;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public void addPontoInteresse(PontoInteresse pontoInteresse){
        pontosInteresse.add(pontoInteresse);
    }

    public List<PontoInteresse> getPontosInteresse(){
        return pontosInteresse;
    }

}
