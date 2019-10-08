package br.pro.ednilsonrossi.visitalocal.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    private FloatingActionButton novoPontoInteresseActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espacos);

        novoPontoInteresseActionButton = (FloatingActionButton) findViewById(R.id.fab_adicionar_ponto_interesse);
        novoPontoInteresseActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                novoPontoInteresse();
            }
        });

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

    private void novoPontoInteresse(){
        Intent intent = new Intent(this, EspacoDetalhesActivity.class);
        intent.putExtra("id_local", local.getId());
        startActivity(intent);
    }
}
