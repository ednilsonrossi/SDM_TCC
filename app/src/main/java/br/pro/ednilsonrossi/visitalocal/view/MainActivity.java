package br.pro.ednilsonrossi.visitalocal.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import br.pro.ednilsonrossi.visitalocal.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.administrador:
                intent = new Intent(this, AdministradorActivity.class);
                startActivity(intent);
                break;
            case R.id.usuario:

                break;

            case R.id.sair:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
