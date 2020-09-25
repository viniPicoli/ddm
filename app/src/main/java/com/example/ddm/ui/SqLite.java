package com.example.ddm.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqLite extends SQLiteOpenHelper {

    //Variaveis do banco
    private static final int VERSAO_BANCO = 1;
    private static final String BANCO_NOME = "db_SqLite";

    //Variaveis table Person
    private static  final String TABELA_PERSON = "tb_person";
    private static  final String PERSON_ID  = "id";
    private static  final String PERSON_NOME = "nome";
    private static  final String PERSON_SOBRENOME = "sobrenome";
    private static  final String PERSON_TELEFONE = "telefone";
    private static  final String PERSON_EMAIL = "email";


    //Variaveis table User
    private static  final String TABELA_USER = "tb_user";
    private static  final String USER_ID = "id";
    private static  final String USER_LOGIN = "login";
    private static  final String USER_SENHA = "senha";

    //Variaveis table Local
    private static  final String TABELA_LOCAL = "tb_local";
    private static  final String LOCAL_ID = "id";
    private static  final String LOCAL_PERSONID = "personid";
    private static  final String LOCAL_DESCRICAO = "descricao";
    private static  final String LOCAL_TITULO = "titulo";
    private static  final String LOCAL_CIDADE = "cidade";
    private static  final String LOCAL_RUA = "rua";
    private static  final String LOCAL_UF = "uf";
    private static  final String LOCAL_BAIRRO = "bairro";
    private static  final String LOCAL_COMPLEMENTO = "complemento";
    private static  final String LOCAL_CEP = "cep";
    private static  final String LOCAL_LATITUDE = "latitude";
    private static  final String LOCAL_LONGITUDE = "longitude";

    public SqLite(@Nullable Context context) {
        super(context, BANCO_NOME, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Cria tabelas
        String QUERY_PERSON = "CREATE TABLE " + TABELA_PERSON + " ("
                + PERSON_ID + " INTEGER PRIMARY KEY, " + PERSON_NOME + " TEXT, "
                + PERSON_TELEFONE+ " TEXT, " + PERSON_EMAIL + " TEXT, "
                + PERSON_SOBRENOME + " TEXT)";
        db.execSQL(QUERY_PERSON);

        String QUERY_USER = "CREATE TABLE " + TABELA_USER + " ("
                + USER_ID + " INTEGER PRIMARY KEY, " + USER_LOGIN + " TEXT, "
                + USER_SENHA + " TEXT)";

        db.execSQL(QUERY_USER);

        String QUERY_LOCAL = "CREATE TABLE " + TABELA_LOCAL + " ("
                + LOCAL_ID + "INTEGER PRIMARY KEY, " + LOCAL_PERSONID + " INTEGER, "
                + LOCAL_DESCRICAO + " TEXT, " + LOCAL_TITULO + " TEXT, "
                + LOCAL_CIDADE + " TEXT, " + LOCAL_RUA + " TEXT, "
                + LOCAL_UF + " TEXT, " + LOCAL_BAIRRO + " TEXT, "
                + LOCAL_COMPLEMENTO + " TEXT, " + LOCAL_CEP + " TEXT, "
                + LOCAL_LATITUDE + " TEXT, " + LOCAL_LONGITUDE + " TEXT)";
        db.execSQL(QUERY_LOCAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // crud

    void addPerson (Person person){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values_person = new ContentValues();

        values_person.put(PERSON_NOME, person.getNome());
        values_person.put(PERSON_SOBRENOME, person.getSobrenome());
        values_person.put(PERSON_EMAIL, person.getEmail());
        values_person.put(PERSON_TELEFONE, person.getTelefone());

        db.insert(TABELA_PERSON, null, values_person);
        db.close();

    }

}
