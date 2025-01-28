package com.yuri.clicklar.Model;

public class Mensagens {
    private String mensagem;
    private String uidUsu;
    private String data;
    private String hora;

    public Mensagens(String mensagem, String uidUsu, String data, String hora) {
        this.mensagem = mensagem;
        this.uidUsu = uidUsu;
        this.data = data;
        this.hora = hora;
    }

    public Mensagens() {
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getUidUsu() {
        return uidUsu;
    }

    public void setUidUsu(String uidUsu) {
        this.uidUsu = uidUsu;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
