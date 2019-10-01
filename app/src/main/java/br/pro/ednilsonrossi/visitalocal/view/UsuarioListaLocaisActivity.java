package br.pro.ednilsonrossi.visitalocal.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.pro.ednilsonrossi.visitalocal.R;
import br.pro.ednilsonrossi.visitalocal.dao.BDLocalController;
import br.pro.ednilsonrossi.visitalocal.model.Local;

public class UsuarioListaLocaisActivity extends AppCompatActivity {

    private RecyclerView locaisRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    UsuarioLocalAdapter localAdapter;

    private List<Local> localList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_lista_locais);

        recuperaLocais();
        inicializaLocalRecyclerView();

    }

    public void recuperaLocais(){
        BDLocalController bdLocalController = new BDLocalController(this);
        localList = bdLocalController.recuperaTodos();
    }

    private void inicializaLocalRecyclerView(){
        locaisRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_usuario_locais);
        layoutManager = new LinearLayoutManager(this);
        locaisRecyclerView.setLayoutManager(layoutManager);
        localAdapter = new UsuarioLocalAdapter(this, localList);
        locaisRecyclerView.setAdapter(localAdapter);
    }
}
