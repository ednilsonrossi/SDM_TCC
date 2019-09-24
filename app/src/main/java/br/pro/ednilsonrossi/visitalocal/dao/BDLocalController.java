package br.pro.ednilsonrossi.visitalocal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.pro.ednilsonrossi.visitalocal.model.Coordenada;
import br.pro.ednilsonrossi.visitalocal.model.Local;
import br.pro.ednilsonrossi.visitalocal.model.PontoInteresse;

public class BDLocalController {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private BancoDados bancoDados;

    public BDLocalController(Context context) {
        this.context = context;
        bancoDados = new BancoDados(context);
    }

    public List recuperaTodos(){
        ArrayList<Local> todos = null;
        Cursor cursor;
        Local local;
        String campos[] = {
                bancoDados.LOCAL_NOME,
                bancoDados.LOCAL_LATITUDE,
                bancoDados.LOCAL_LONGITUDE,
                bancoDados.LOCAL_IMAGEM,
                bancoDados.LOCAL_AUDIO,
                bancoDados.LOCAL_ID};

        sqLiteDatabase = bancoDados.getReadableDatabase();
        cursor = sqLiteDatabase.query(bancoDados.TABLE_LOCAL, campos, null, null, null, null, bancoDados.LOCAL_NOME, null);
        if(cursor != null){
            todos = new ArrayList<>(cursor.getCount());

            while (cursor.moveToNext()){
                local = new Local(cursor.getString(0),
                        new Coordenada(cursor.getFloat(1), cursor.getFloat(2)),
                        cursor.getString(3),
                        cursor.getString(4));
                local.setId(cursor.getInt(5));

                List<PontoInteresse> pontos = new BDPontoInteresseController(context).recuperaTodos(local);
                for(PontoInteresse p : pontos){
                    local.addPontoInteresse(p);
                }

                todos.add(local);
            }
            cursor.close();
        }
        return todos;
    }

    public Local recuperar(int localId){
        Cursor cursor;
        Local local = null;
        String campos[] = {
                bancoDados.LOCAL_NOME,
                bancoDados.LOCAL_LATITUDE,
                bancoDados.LOCAL_LONGITUDE,
                bancoDados.LOCAL_IMAGEM,
                bancoDados.LOCAL_AUDIO,
                bancoDados.LOCAL_ID};

        String where = bancoDados.LOCAL_ID + " = " + localId;

        sqLiteDatabase = bancoDados.getReadableDatabase();
        cursor = sqLiteDatabase.query(bancoDados.TABLE_LOCAL, campos, where, null, null, null, bancoDados.LOCAL_ID, null);
        if(cursor != null){
            if(cursor.moveToNext()){
                local = new Local(cursor.getString(0),
                        new Coordenada(cursor.getFloat(1), cursor.getFloat(2)),
                        cursor.getString(3),
                        cursor.getString(4));
                local.setId(cursor.getInt(5));
            }
            cursor.close();
        }
        return local;
    }

    public boolean criar(Local local){
        long retorno;
        ContentValues valores = new ContentValues();
        valores.put(BancoDados.LOCAL_NOME, local.getNome());
        valores.put(BancoDados.LOCAL_LATITUDE, local.getCoordenada().getLatitude());
        valores.put(BancoDados.LOCAL_LONGITUDE, local.getCoordenada().getLongitude());
        valores.put(BancoDados.LOCAL_IMAGEM, local.getImagem());
        valores.put(BancoDados.LOCAL_AUDIO, local.getAudio());

        sqLiteDatabase = bancoDados.getWritableDatabase();

        retorno = sqLiteDatabase.insert(BancoDados.TABLE_LOCAL, null, valores);

        return retorno != -1;
    }


}
