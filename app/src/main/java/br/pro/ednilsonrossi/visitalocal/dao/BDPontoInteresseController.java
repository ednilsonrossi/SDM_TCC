package br.pro.ednilsonrossi.visitalocal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.pro.ednilsonrossi.visitalocal.model.Coordenada;
import br.pro.ednilsonrossi.visitalocal.model.Local;
import br.pro.ednilsonrossi.visitalocal.model.PontoInteresse;

public class BDPontoInteresseController {

    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private BancoDados bancoDados;

    public BDPontoInteresseController(Context context) {
        this.context = context;
        bancoDados = new BancoDados(context);
    }

    public boolean criar(PontoInteresse pontoInteresse){
        long retorno;
        ContentValues valores = new ContentValues();
        valores.put(BancoDados.PONTO_NOME, pontoInteresse.getNome());
        valores.put(BancoDados.PONTO_LATITUDE, pontoInteresse.getCoordenada().getLatitude());
        valores.put(BancoDados.PONTO_LONGITUDE, pontoInteresse.getCoordenada().getLongitude());
        valores.put(BancoDados.PONTO_IMAGEM, pontoInteresse.getImagem());
        valores.put(BancoDados.PONTO_AUDIO, pontoInteresse.getAudio());
        valores.put(BancoDados.PONTO_ID_LOCAL, pontoInteresse.getLocal().getId());

        sqLiteDatabase = bancoDados.getWritableDatabase();

        retorno = sqLiteDatabase.insert(BancoDados.TABLE_PONTOINTERESSE, null, valores);

        return retorno != -1;
    }

    public List recuperaTodos(Local local){
        ArrayList<PontoInteresse> todos = null;
        Cursor cursor;

        PontoInteresse pontoInteresse;

        String campos[] = {
                bancoDados.PONTO_NOME,
                bancoDados.PONTO_LATITUDE,
                bancoDados.PONTO_LONGITUDE,
                bancoDados.PONTO_IMAGEM,
                bancoDados.PONTO_AUDIO,
                bancoDados.PONTO_ID};

        String where = bancoDados.PONTO_ID_LOCAL + " = " + local.getId();

        sqLiteDatabase = bancoDados.getReadableDatabase();
        cursor = sqLiteDatabase.query(bancoDados.TABLE_PONTOINTERESSE, campos, where, null, null, null, bancoDados.PONTO_NOME, null);

        if(cursor != null){

            todos = new ArrayList<>(cursor.getCount());

            while (cursor.moveToNext()){

                pontoInteresse = new PontoInteresse(local,
                        cursor.getInt(5),
                        cursor.getString(0),
                        new Coordenada(cursor.getDouble(1), cursor.getDouble(2)),
                        cursor.getString(3),
                        cursor.getString(4));

                todos.add(pontoInteresse);
            }
            cursor.close();
        }
        return todos;
    }

}
