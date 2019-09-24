package br.pro.ednilsonrossi.visitalocal.model;

import br.pro.ednilsonrossi.visitalocal.model.enums.Gravacao;

public class PontoInteresse {
    private int id;
    private String nome;
    private Coordenada coordenada;
    private String imagem;
    private String audio;
    private Local local;

    public PontoInteresse() {
        init();
    }

    public PontoInteresse(Local local, String nome, Coordenada coordenada, String imagem, String audio) {
        init();
        this.local = local;
        this.nome = nome;
        this.coordenada = coordenada;
        this.imagem = imagem;
        this.audio = audio;
    }

    public PontoInteresse(Local local, int id, String nome, Coordenada coordenada, String imagem, String audio) {
        init();
        this.local = local;
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
        local = null;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
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
