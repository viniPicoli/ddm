package com.example.ddm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {

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

    public DataBase(@Nullable Context context) {
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

    // crud add

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

    void addUser (User user){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values_user = new ContentValues();

        values_user.put(USER_LOGIN, user.getLogin());
        values_user.put(USER_SENHA, user.getSenha());

        db.insert(TABELA_USER, null, values_user);
        db.close();
}
    void addLocal (Local local){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values_local = new ContentValues();

        values_local.put(LOCAL_PERSONID, local.getPersonid());
        values_local.put(LOCAL_BAIRRO, local.getBairro());
        values_local.put(LOCAL_CEP, local.getCep());
        values_local.put(LOCAL_CIDADE, local.getCidade());
        values_local.put(LOCAL_DESCRICAO, local.getDescricao());
        values_local.put(LOCAL_TITULO, local.getTitulo());
        values_local.put(LOCAL_RUA, local.getRua());
        values_local.put(LOCAL_UF, local.getUf());
        values_local.put(LOCAL_COMPLEMENTO, local.getComplemento());
        values_local.put(LOCAL_LATITUDE, local.getLatitude());
        values_local.put(LOCAL_LONGITUDE, local.getLongitude());

        db.insert(TABELA_LOCAL, null, values_local);
        db.close();
    }

    // crud delete

    public void apagarPerson(Person person){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABELA_PERSON, PERSON_ID + " = ?", new String[] {String.valueOf(person.getId())});

        db.close();
        }

    void apagarLocal(Local local){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABELA_LOCAL, LOCAL_ID + " = ?", new String[] {String.valueOf(local.getId())});

        db.close();

    }

    void apagarLUser(User user){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABELA_USER, USER_ID + " = ?", new String[] {String.valueOf(user.getId())});

        db.close();

    }

    // Crud select

    Person selecionarPerson(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_PERSON, new String[]{PERSON_ID, PERSON_NOME, PERSON_TELEFONE,
                        PERSON_EMAIL, PERSON_SOBRENOME}, PERSON_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        Person person = new Person(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        return person;
    }

    User selecionarUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_USER, new String[]{USER_ID, USER_LOGIN, USER_SENHA},
                USER_ID + " = ?", new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        return user;
    }

    Local selecionarLocal(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_LOCAL, new String[]{LOCAL_ID, LOCAL_PERSONID, LOCAL_DESCRICAO,
                LOCAL_TITULO,LOCAL_CIDADE,LOCAL_RUA,LOCAL_UF,LOCAL_BAIRRO,LOCAL_COMPLEMENTO,LOCAL_CEP,
                LOCAL_LATITUDE,LOCAL_LONGITUDE},
                USER_ID + " = ?", new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        Local local = new Local(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7),
                cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11));
        return local;
    }

    //Crud Update

    void updatePerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values_person = new ContentValues();

        values_person.put(PERSON_NOME, person.getNome());
        values_person.put(PERSON_SOBRENOME, person.getSobrenome());
        values_person.put(PERSON_EMAIL, person.getEmail());
        values_person.put(PERSON_TELEFONE, person.getTelefone());

        db.update(TABELA_PERSON, values_person, PERSON_ID + " = ?",
                new String[]{String.valueOf(person.getId())});
    }

        void updateUser(User user){
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values_user = new ContentValues();

            values_user.put(USER_LOGIN, user.getLogin());
            values_user.put(USER_SENHA, user.getSenha());

            db.update(TABELA_USER,values_user,USER_ID + " = ?",
                    new String[] { String.valueOf(user.getId())});
    }

         void updateLocal(Local local){
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values_local = new ContentValues();

             values_local.put(LOCAL_PERSONID, local.getPersonid());
             values_local.put(LOCAL_BAIRRO, local.getBairro());
             values_local.put(LOCAL_CEP, local.getCep());
             values_local.put(LOCAL_CIDADE, local.getCidade());
             values_local.put(LOCAL_DESCRICAO, local.getDescricao());
             values_local.put(LOCAL_TITULO, local.getTitulo());
             values_local.put(LOCAL_RUA, local.getRua());
             values_local.put(LOCAL_UF, local.getUf());
             values_local.put(LOCAL_COMPLEMENTO, local.getComplemento());
             values_local.put(LOCAL_LATITUDE, local.getLatitude());
             values_local.put(LOCAL_LONGITUDE, local.getLongitude());

             db.update(TABELA_LOCAL,values_local,LOCAL_ID + " = ?",
                    new String[] { String.valueOf(local.getId())});
    }
}
