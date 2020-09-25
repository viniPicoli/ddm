package com.example.ddm.ui;

public class User {

    int id;
    String login;
    String senha;

    public User (){

    }

    public User (int _id,String _login, String _senha){
        this.id = _id;
        this.login = _login;
        this.senha = _senha;
    }

    public User (String _login, String _senha){
        this.login = _login;
        this.senha = _senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
