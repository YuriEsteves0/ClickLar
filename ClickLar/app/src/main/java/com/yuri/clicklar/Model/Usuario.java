package com.yuri.clicklar.Model;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Usuario {
    private String idUsu;
    private String nome;
    private String descricaoPerfil;
    private String email;
    private String senha;
    private String NPE;
    private String TNPE;
    private boolean ativo;
    private String dataCriacao;
    private boolean premium;
    private boolean valido;
    private int visM = 0;
    private int visT = 0;
    private String nivelUsuario;

    public Usuario() {}

    public Usuario(String nome, String email, String senha, String NPE, String TNPE, boolean ativo,
                   String dataCriacao, boolean premium, boolean valido, String idUsu, String nivelUsuario) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.NPE = NPE;
        this.TNPE = TNPE;
        this.ativo = ativo;
        this.dataCriacao = dataCriacao;
        this.premium = premium;
        this.valido = valido;
        this.idUsu = idUsu;
        this.nivelUsuario = nivelUsuario;
    }



    public String getDescricaoPerfil() {
        return descricaoPerfil;
    }

    public void setDescricaoPerfil(String descricaoPerfil) {
        this.descricaoPerfil = descricaoPerfil;
    }

    public String getNivelUsuario() {
        return nivelUsuario;
    }

    public void setNivelUsuario(String nivelUsuario) {
        this.nivelUsuario = nivelUsuario;
    }

    public int getVisM() {
        return visM;
    }

    public void setVisM(int visM) {
        this.visM = visM;
    }

    public int getVisT() {
        return visT;
    }

    public void setVisT(int visT) {
        this.visT = visT;
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
                ", premium=" + premium +
                ", valido=" + valido +
                '}';
    }
}
