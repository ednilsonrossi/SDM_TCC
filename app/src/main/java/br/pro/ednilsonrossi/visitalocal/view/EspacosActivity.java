package br.pro.ednilsonrossi.visitalocal.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.pro.ednilsonrossi.visitalocal.R;
import br.pro.ednilsonrossi.visitalocal.dao.BDLocalController;
import br.pro.ednilsonrossi.visitalocal.model.Local;

public class EspacosActivity extends AppCompatActivity {

    private Local local;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espacos);

        recuperateLocal();

    }

    private void recuperateLocal(){
        int localId = getIntent().getIntExtra("localId", -1);
        if(localId != -1) {
            BDLocalController bancoLocal = new BDLocalController(this);
            local = bancoLocal.recuperar(localId);
        }else{
            finish();
        }
    }
}
