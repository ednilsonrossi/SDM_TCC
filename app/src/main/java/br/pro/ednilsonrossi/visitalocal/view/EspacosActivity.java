package br.pro.ednilsonrossi.visitalocal.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.pro.ednilsonrossi.visitalocal.R;
import br.pro.ednilsonrossi.visitalocal.dao.BDLocalController;
import br.pro.ednilsonrossi.visitalocal.dao.BDPontoInteresseController;
import br.pro.ednilsonrossi.visitalocal.model.Local;
import br.pro.ednilsonrossi.visitalocal.model.PontoInteresse;

public class EspacosActivity extends AppCompatActivity {

    private Local local;
    private List<PontoInteresse> pontoInteresseList;

    private RecyclerView pontosRecyclerView;
    private EspacosAdapter espacosAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espacos);

        recuperateLocal();
        inicializarRecyclerView();

    }

    private void recuperateLocal(){
        int localId = getIntent().getIntExtra("localId", -1);
        if(localId != -1) {
            BDLocalController bancoLocal = new BDLocalController(this);
            local = bancoLocal.recuperar(localId);
            recuperaPontosInteresse();
        }else{
            finish();
        }
    }

    private void recuperaPontosInteresse(){
        pontoInteresseList = new BDPontoInteresseController(this).recuperaTodos(local);
    }

    private void inicializarRecyclerView(){
        pontosRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_pontos_interesse);
        layoutManager = new LinearLayoutManager(this);
        pontosRecyclerView.setLayoutManager(layoutManager);
        espacosAdapter = new EspacosAdapter(this, pontoInteresseList);
        pontosRecyclerView.setAdapter(espacosAdapter);
    }
}
