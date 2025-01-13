package com.yuri.clicklar.Model;

import java.util.List;

public class Usuario {
    private String idUsu;
    private String nome;
    private String email;
    private String senha;
    private String NPE;
    private String TNPE;
    private boolean ativo;
    private String dataCriacao;
    private String idFavorito;
    private boolean premium;
    private boolean valido;

    public Usuario() {}

    public Usuario(String nome, String email, String senha, String NPE, String TNPE, boolean ativo,
                   String dataCriacao, String idFavorito, boolean premium, boolean valido, String idUsu) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.NPE = NPE;
        this.TNPE = TNPE;
        this.ativo = ativo;
        this.dataCriacao = dataCriacao;
        this.idFavorito = idFavorito;
        this.premium = premium;
        this.valido = valido;
        this.idUsu = idUsu;
    }

    public String getIdUsu() {
        return idUsu;
    }

    public void setIdUsu(String idUsu) {
        this.idUsu = idUsu;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNPE() {
        return NPE;
    }

    public void setNPE(String NPE) {
        this.NPE = NPE;
    }

    public String getTNPE() {
        return TNPE;
    }

    public void setTNPE(String TNPE) {
        this.TNPE = TNPE;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getIdFavorito() {
        return idFavorito;
    }

    public void setIdFavorito(String idFavorito) {
        this.idFavorito = idFavorito;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", NPE='" + NPE + '\'' +
                ", TNPE=" + TNPE +
                ", ativo=" + ativo +
                ", dataCriacao='" + dataCriacao + '\'' +
                ", idFavorito='" + idFavorito + '\'' +
                ", premium=" + premium +
                ", valido=" + valido +
                '}';
    }
}
