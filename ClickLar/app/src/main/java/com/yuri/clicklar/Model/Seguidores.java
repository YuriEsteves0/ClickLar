package com.yuri.clicklar.Model;

public class Seguidores {
    private String idSeguidores;
    private String uidUsuarioSeguido;
    private String uidUsuarioSeguidor;

    public Seguidores() {
    }

    public Seguidores(String idSeguidores, String uidUsuarioSeguido, String uidUsuarioSeguidor) {
        this.uidUsuarioSeguido = uidUsuarioSeguido;
        this.uidUsuarioSeguidor = uidUsuarioSeguidor;
        this.idSeguidores = idSeguidores;
    }

    public String getIdSeguidores() {
        return idSeguidores;
    }

    public void setIdSeguidores(String idSeguidores) {
        this.idSeguidores = idSeguidores;
    }

    public String getUidUsuarioSeguido() {
        return uidUsuarioSeguido;
    }

    public void setUidUsuarioSeguido(String uidUsuarioSeguido) {
        this.uidUsuarioSeguido = uidUsuarioSeguido;
    }

    public String getUidUsuarioSeguidor() {
        return uidUsuarioSeguidor;
    }

    public void setUidUsuarioSeguidor(String uidUsuarioSeguidor) {
        this.uidUsuarioSeguidor = uidUsuarioSeguidor;
    }
}
