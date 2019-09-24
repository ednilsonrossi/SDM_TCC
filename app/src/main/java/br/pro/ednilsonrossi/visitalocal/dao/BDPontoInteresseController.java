package br.pro.ednilsonrossi.visitalocal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.pro.ednilsonrossi.visitalocal.model.PontoInteresse;

public class BDPontoInteresseController {

    private SQLiteDatabase sqLiteDatabase;
    private BancoDados bancoDados;

    public BDPontoInteresseController(Context context) {
        bancoDados = new BancoDados(context);
    }


}
