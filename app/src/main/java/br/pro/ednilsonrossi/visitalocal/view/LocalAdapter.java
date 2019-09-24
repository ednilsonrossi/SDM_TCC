package br.pro.ednilsonrossi.visitalocal.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import br.pro.ednilsonrossi.visitalocal.R;
import br.pro.ednilsonrossi.visitalocal.model.Local;

public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.LocalViewHolder> {
    private List<Local> locais;
    private Context context;

    public LocalAdapter(Context context, List<Local> locais) {
        this.locais = locais;
        this.context = context;
    }

    @NonNull
    @Override
    public LocalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recyclerview_local, viewGroup, false);

        return new LocalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalViewHolder viewHolder, final int i) {
        viewHolder.nomeTextView.setText(locais.get(i).getNome());

        if(locais.get(i).getImagem() != null) {
            viewHolder.fotoImageView.setImageURI(Uri.fromFile(new File(locais.get(i).getImagem())));
        }else{
            viewHolder.fotoImageView.setImageResource(R.drawable.ic_camera);
        }

        viewHolder.paiLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreEspacos(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locais.size();
    }


    private void abreEspacos(int posicao){
        Intent in = new Intent(context, EspacosActivity.class);
        in.putExtra("localId", locais.get(posicao).getId());
        context.startActivity(in);
    }


    protected class LocalViewHolder extends RecyclerView.ViewHolder{

        protected TextView nomeTextView;
        protected ImageView fotoImageView;
        protected RelativeLayout paiLayout;

        public LocalViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeTextView = (TextView) itemView.findViewById(R.id.textview_nome_local);
            fotoImageView = (ImageView) itemView.findViewById(R.id.imageview_foto_local);
            paiLayout = (RelativeLayout) itemView.findViewById(R.id.pai_layout);
        }
    }
}
