package com.example.appmenu;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Adapter3 extends RecyclerView.Adapter<Adapter3.ViewHolder> {
    private List<Plat> items;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;
    private int seleccionat = -1;

    // data is passed into the constructor
    Adapter3(Context context, List<Plat> data) {
        this.mInflater = LayoutInflater.from(context);
        this.items = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.nom.setText(items.get(position).nom);
        if(seleccionat == position) holder.tick.setBackground(context.getResources().getDrawable(R.drawable.tick));
        else holder.tick.setBackground(context.getResources().getDrawable(R.drawable.no_tick));
        Log.i("FOTO", (items.get(position).url));
        Glide.with(context).load(items.get(position).url)
                .placeholder(R.drawable.buit)
                .error(R.drawable.buit)
                .into(holder.foto);

        holder.tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (seleccionat != position) seleccionat = position;
                else seleccionat = -1;
                notifyDataSetChanged();
            }
        });
    }

    public boolean isSelected(){
        return seleccionat != -1;
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return items.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView tick, foto;
        TextView nom, info;

        ViewHolder(View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.item_nom);
            tick = itemView.findViewById(R.id.item_tick);
            info = itemView.findViewById(R.id.item_info);
            foto = itemView.findViewById(R.id.item_foto);
            info.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick3(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return items.get(id).nom;
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick3(View view, int position);
    }
}
