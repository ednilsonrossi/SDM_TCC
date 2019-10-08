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
import br.pro.ednilsonrossi.visitalocal.model.Local;

public class AdministradorActivity extends AppCompatActivity {

    private FloatingActionButton addLocalFAB;
    private RecyclerView locaisRecyclerView;
    private LocalAdapter localAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Local> locais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
        carregaDadosLocais();
        inicializaLocalRecyclerView();
        inicializaFloatingActionButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaDadosLocais();
        localAdapter.notifyDataSetChanged();
    }

    private void inicializaLocalRecyclerView(){
        locaisRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_locais);
        layoutManager = new LinearLayoutManager(this);
        locaisRecyclerView.setLayoutManager(layoutManager);
        localAdapter = new LocalAdapter(this, locais);
        locaisRecyclerView.setAdapter(localAdapter);

    }

    private void inicializaFloatingActionButton(){
        addLocalFAB = (FloatingActionButton) findViewById(R.id.fab_adicionar_local);
        addLocalFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarLocal();
            }
        });
    }

    public void carregaDadosLocais(){
        BDLocalController bdLocalController = new BDLocalController(this);
        locais = bdLocalController.recuperaTodos();
    }

    private void adicionarLocal(){
        Intent in = new Intent(this, LocalDetalhesActivity.class);
        startActivity(in);
    }
}
