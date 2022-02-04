package com.example.api_payphone_pasarelapagos.Adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.api_payphone_pasarelapagos.MainActivity;
import com.example.api_payphone_pasarelapagos.Model.EquiposElectronicos;
import com.example.api_payphone_pasarelapagos.R;

import java.io.BufferedReader;
import java.util.List;

public class AdapterEquipos extends RecyclerView.Adapter<AdapterEquipos.ViewHolder>
        implements View.OnClickListener {

    Context ctx;
    List<EquiposElectronicos> lstEquipos;
    View.OnClickListener listener;

    public AdapterEquipos(Context ctx, List<EquiposElectronicos> lstEquipos) {
        this.ctx = ctx;
        this.lstEquipos = lstEquipos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.activity_layout_equipos, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        EquiposElectronicos equiposElectronicos = lstEquipos.get(position);
        holder.nombre.setText(equiposElectronicos.getName());
        holder.precio.setText(String.valueOf(equiposElectronicos.getPrecio()) + " $");
        holder.img.setImageResource(equiposElectronicos.getImgEquipo());

        //Accionar el boton onclick
        holder.btnCompra.setOnClickListener(listener);
        //Guardar la posicion al dar clic en el boton
        holder.btnCompra.setTag(position);
    }

    @Override
    public int getItemCount() {
        return lstEquipos.size();
    }

    public void OnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, precio;
        ImageView img;
        Button btnCompra;


        public ViewHolder(View itemView) {
            super(itemView);

            nombre = (TextView) itemView.findViewById(R.id.txtvNombre);
            precio = (TextView) itemView.findViewById(R.id.txtvPrecio);
            img = (ImageView) itemView.findViewById(R.id.imgEquipos);
            btnCompra = (Button) itemView.findViewById(R.id.btnComprar);

        }
    }
}
