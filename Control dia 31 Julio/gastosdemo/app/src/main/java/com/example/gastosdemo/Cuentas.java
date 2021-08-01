package com.example.gastosdemo;


public class Cuentas {
    private String codigo;
    private int valor;

    public Cuentas(String codigo, int valor) {
        this.codigo = codigo;
        this.valor = valor;
    }

    public Cuentas() {

    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}