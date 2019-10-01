package br.pro.ednilsonrossi.visitalocal.view;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
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
import br.pro.ednilsonrossi.visitalocal.model.PontoInteresse;

public class EspacosAdapter extends RecyclerView.Adapter<EspacosAdapter.EspacoViewHolder>{
    private List<PontoInteresse> pontoInteresseList;
    private Context context;

    public EspacosAdapter(Context context, List<PontoInteresse> pontoInteresseList) {
        this.pontoInteresseList = pontoInteresseList;
        this.context = context;
    }

    @NonNull
    @Override
    public EspacoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recyclerview_espacos, viewGroup, false);

        return new EspacoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EspacoViewHolder espacoViewHolder, int i) {
        espacoViewHolder.nomeTextView.setText(pontoInteresseList.get(i).getNome());

        if(pontoInteresseList.get(i).getImagem() != null) {
            espacoViewHolder.fotoImageView.setImageURI(Uri.fromFile(new File(pontoInteresseList.get(i).getImagem())));
        }else{
            espacoViewHolder.fotoImageView.setImageResource(R.drawable.ic_camera);
        }

        /*
        espacoViewHolder.paiLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return pontoInteresseList.size();
    }

    protected class EspacoViewHolder extends RecyclerView.ViewHolder {
        protected TextView nomeTextView;
        protected ImageView fotoImageView;
        protected RelativeLayout paiLayout;

        public EspacoViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeTextView = (TextView) itemView.findViewById(R.id.textview_nome_local);
            fotoImageView = (ImageView) itemView.findViewById(R.id.imageview_foto_local);
            paiLayout = (RelativeLayout) itemView.findViewById(R.id.pai_layout);
        }
    }
}
