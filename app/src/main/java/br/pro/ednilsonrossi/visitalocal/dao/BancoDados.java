package br.pro.ednilsonrossi.visitalocal.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDados extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private static final String DATABASE_NAME = "VisitaLocal.db";


    public static final String TABLE_LOCAL = "Local";
    public static final String LOCAL_ID = "id";
    public static final String LOCAL_NOME = "nome";
    public static final String LOCAL_LATITUDE = "latitude";
    public static final String LOCAL_LONGITUDE = "longitude";
    public static final String LOCAL_IMAGEM = "imagem";
    public static final String LOCAL_AUDIO = "audio";


    public static final String TABLE_PONTOINTERESSE = "Ponto_Interesse";
    public static final String PONTO_ID = "id";
    public static final String PONTO_NOME = "nome";
    public static final String PONTO_LATITUDE = "latitude";
    public static final String PONTO_LONGITUDE = "longitude";
    public static final String PONTO_IMAGEM = "imagem";
    public static final String PONTO_AUDIO = "audio";
    public static final String PONTO_ID_LOCAL = "id_local";




    public BancoDados(Context context) {
        super(context, DATABASE_NAME, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql;

        //Tabela Local
        sql = "CREATE TABLE " + TABLE_LOCAL + " (";
        sql += LOCAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += LOCAL_NOME + " TEXT, ";
        sql += LOCAL_IMAGEM + " TEXT, ";
        sql += LOCAL_AUDIO + " TEXT, ";
        sql += LOCAL_LATITUDE + " REAL, ";
        sql += LOCAL_LONGITUDE + " REAL)";
        db.execSQL(sql);

        //Tabela Ponto_Interesse
        sql = "CREATE TABLE " + TABLE_PONTOINTERESSE + " (";
        sql += PONTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += PONTO_NOME + " TEXT, ";
        sql += PONTO_LATITUDE + " REAL, ";
        sql += PONTO_LONGITUDE + " REAL, ";
        sql += PONTO_IMAGEM + " TEXT, ";
        sql += PONTO_AUDIO + " TEXT, ";
        sql += PONTO_ID_LOCAL + " INTEGER, ";
        sql += "FOREIGN KEY (" + PONTO_ID_LOCAL + ") REFERENCES " + TABLE_LOCAL + "(" + LOCAL_ID + ")";
        sql += ")";
        db.execSQL(sql);

        //Insere Local
        sql = "INSERT INTO LOCAL (nome, latitude, longitude) VALUES ('Casa', 0, 0)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql;
        sql = "DROP TABLE IF EXISTS " + TABLE_LOCAL + "; ";
        sql += "DROP TABLE IF EXISTS " + TABLE_PONTOINTERESSE + ";";
        db.execSQL(sql);
        onCreate(db);
    }
}
