package com.yuri.clicklar.Model;

import java.util.List;

public class Casa {
    private String id;
    private String uidDono;
    private List<String> imagemBase64;
    private String nomeCasa;
    private String statusCasa;
    private String precoCasaAntigo;
    private String precoCasa;
    private String promocao;
    private String qntQuarto;
    private String qntArea;
    private String qntGaragem;
    private String qntBanheiro;
    private String localizacao;
    private String valorCondominio;
    private String bairro;

    public Casa() {
    }

    public Casa(String bairro, String id, String fotocasa, String nomeCasa, String statusCasa, String precoCasaAntigo, String precoCasa, String promocao, String qntQuarto, String qntArea, String qntGaragem, String qntBanheiro, String localizacao, String valorCondominio) {
        this.id=id;
        this.nomeCasa = nomeCasa;
        this.statusCasa = statusCasa;
        this.precoCasaAntigo = precoCasaAntigo;
        this.precoCasa = precoCasa;
        this.promocao = promocao;
        this.qntQuarto = qntQuarto;
        this.qntArea = qntArea;
        this.qntGaragem = qntGaragem;
        this.qntBanheiro = qntBanheiro;
        this.localizacao = localizacao;
        this.imagemBase64 = imagemBase64;
        this.bairro = bairro;
        this.valorCondominio = valorCondominio;
    }

    public String getUidDono() {
        return uidDono;
    }

    public void setUidDono(String uidDono) {
        this.uidDono = uidDono;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getImagemBase64() {
        return imagemBase64;
    }

    public void setImagemBase64(List<String> imagemBase64) {
        this.imagemBase64 = imagemBase64;
    }

    public String getNomeCasa() {
        return nomeCasa;
    }

    public void setNomeCasa(String nomeCasa) {
        this.nomeCasa = nomeCasa;
    }

    public String getStatusCasa() {
        return statusCasa;
    }

    public void setStatusCasa(String statusCasa) {
        this.statusCasa = statusCasa;
    }

    public String getPrecoCasaAntigo() {
        return precoCasaAntigo;
    }

    public void setPrecoCasaAntigo(String precoCasaAntigo) {
        this.precoCasaAntigo = precoCasaAntigo;
    }

    public String getPrecoCasa() {
        return precoCasa;
    }

    public void setPrecoCasa(String precoCasa) {
        this.precoCasa = precoCasa;
    }

    public String getPromocao() {
        return promocao;
    }

    public void setPromocao(String promocao) {
        this.promocao = promocao;
    }

    public String getQntQuarto() {
        return qntQuarto;
    }

    public void setQntQuarto(String qntQuarto) {
        this.qntQuarto = qntQuarto;
    }

    public String getQntArea() {
        return qntArea;
    }

    public void setQntArea(String qntArea) {
        this.qntArea = qntArea;
    }

    public String getQntGaragem() {
        return qntGaragem;
    }

    public void setQntGaragem(String qntGaragem) {
        this.qntGaragem = qntGaragem;
    }

    public String getQntBanheiro() {
        return qntBanheiro;
    }

    public void setQntBanheiro(String qntBanheiro) {
        this.qntBanheiro = qntBanheiro;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public String getValorCondominio() {
        return valorCondominio;
    }

    public void setValorCondominio(String valorCondominio) {
        this.valorCondominio = valorCondominio;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
