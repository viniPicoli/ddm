package com.example.ddm;

import java.util.Date;

public class Person {

    int id;
    String nome;
    String sobrenome;
    String telefone;
    String email;

    public Person (){

    }

    public Person (int _id, String _nome, String _sobrenome, String _telefone, String _email){
        this.id = _id;
        this.nome = _nome;
        this.sobrenome = _sobrenome;
        this.telefone = _telefone;
        this.email = _email;

    }

    public Person (String _nome, String _sobrenome, String _telefone, String _email){
        this.nome = _nome;
        this.sobrenome = _sobrenome;
        this.telefone = _telefone;
        this.email = _email;

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

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}



