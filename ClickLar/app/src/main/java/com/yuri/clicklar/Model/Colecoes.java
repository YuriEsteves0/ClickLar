package com.yuri.clicklar.Model;

public class Colecoes {

    private String idUsuario;
    private String uidCasa;

    public Colecoes(String idUsuario, String idCasa) {
        this.idUsuario = idUsuario;
        this.uidCasa = idCasa;
    }

    public Colecoes() {

    }

    public String getUidCasa() {
        return uidCasa;
    }

    public void setUidCasa(String uidCasa) {
        this.uidCasa = uidCasa;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
