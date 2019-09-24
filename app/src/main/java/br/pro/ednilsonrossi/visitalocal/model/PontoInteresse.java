package br.pro.ednilsonrossi.visitalocal.model;

import br.pro.ednilsonrossi.visitalocal.model.enums.Gravacao;

public class PontoInteresse {
    private int id;
    private String nome;
    private Coordenada coordenada;
    private String imagem;
    private String audio;

    public PontoInteresse() {
        init();
    }

    public PontoInteresse(String nome, Coordenada coordenada, String imagem, String audio) {
        init();
        this.nome = nome;
        this.coordenada = coordenada;
        this.imagem = imagem;
        this.audio = audio;
    }

    public PontoInteresse(int id, String nome, Coordenada coordenada, String imagem, String audio) {
        this.id = id;
        this.nome = nome;
        this.coordenada = coordenada;
        this.imagem = imagem;
        this.audio = audio;
    }

    private void init(){
        id = -1;
        imagem = null;
        audio = null;
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
}
