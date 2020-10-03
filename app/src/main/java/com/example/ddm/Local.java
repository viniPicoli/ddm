package com.example.ddm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Local {

    int id;
    int personid;
    int avaliacaopos;
    int avaliacaoneg;
    private String descricao;
    String titulo;
    String cidade;
    String rua;
    String uf;
    String bairro;
    String numero;
    String complemento;
    String cep;
    String latitude;
    String longitude;

    private SQLiteDatabase db;
    public Local(){
    }

    public Local(int _id,int _personid,int _avaliacaopos, int _avaliacaoneg, String _descricao, String _titulo, String _cidade, String _rua, String _uf, String _bairro,String _numero, String _complemento, String _cep, String _latitude, String _longitude){
    this.id = _id;
    this.personid = _personid;
    this.avaliacaopos = _avaliacaopos;
    this.avaliacaoneg = _avaliacaoneg;
    this.descricao = _descricao;
    this.titulo = _titulo;
    this.cidade = _cidade;
    this.rua = _rua;
    this.uf = _uf;
    this.bairro = _bairro;
    this.numero = _numero;
    this.complemento = _complemento;
    this.cep = _cep;
    this.latitude = _latitude;
    this.longitude = _longitude;
    }

    public Local(int _personid,int _avaliacaopos, int _avaliacaoneg, String _descricao, String _titulo, String _cidade, String _rua, String _uf, String _bairro,String _numero, String _complemento, String _cep, String _latitude, String _longitude){
        this.personid = _personid;
        this.avaliacaopos = _avaliacaopos;
        this.avaliacaoneg = _avaliacaoneg;
        this.descricao = _descricao;
        this.titulo = _titulo;
        this.cidade = _cidade;
        this.rua = _rua;
        this.uf = _uf;
        this.bairro = _bairro;
        this.numero = _numero;
        this.complemento = _complemento;
        this.cep = _cep;
        this.latitude = _latitude;
        this.longitude = _longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonid() {
        return personid;
    }

    public void setPersonid(int personid) {
        this.personid = personid;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLatitude() {
        return latitude;
    }

    public int getAvaliacaopos() {
        return avaliacaopos;
    }

    public void setAvaliacaopos(int avaliacaopos) {
        this.avaliacaopos = avaliacaopos;
    }

    public int getAvaliacaoneg() {
        return avaliacaoneg;
    }

    public void setAvaliacaoneg(int avaliacaoneg) {
        this.avaliacaoneg = avaliacaoneg;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
