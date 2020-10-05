package com.example.ddm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewLocal extends RecyclerView.Adapter<ViewLocal.ViewHolder>{

    Context c;
    List<Local> locals;

//    Local[] locals;
    Context context;

    public ViewLocal(Context c, List<Local> locals) {
        this.locals = locals;
//        this.activity = activity;
    }
    public ViewLocal() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_list, null);
        return new ViewHolder(view);


//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view = layoutInflater.inflate(R.layout.movie_item_list, parent, false );
//        ViewHolder viewHolder = new ViewHolder(view);
//
//        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewName.setText(locals.get(position).getTitulo());
        holder.textDescription.setText(locals.get(position).getDescricao());

//        final Local localList = locals[position];
//        holder.textViewName.setText(localList.getTitulo());
//        holder.textDescription.setText(localList.getDescricao());


//        ----------------VINICIUS FAZER----------------

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, localList.getDescricao(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return locals.size();
        //        return locals.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName;
        TextView textDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textName);
            textDescription = itemView.findViewById(R.id.textDescription);
        }
    }
}
