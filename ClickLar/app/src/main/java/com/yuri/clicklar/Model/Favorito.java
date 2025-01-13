package com.yuri.clicklar.Model;

public class Favorito {
    private String idUsuario;

    public Favorito(String idUsuario, String idCasa) {
        this.idUsuario = idUsuario;
    }

    public Favorito() {

    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
