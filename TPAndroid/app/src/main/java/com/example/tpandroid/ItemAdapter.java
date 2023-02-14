package com.example.tpandroid;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
        public List<Item> list;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView tvNom;
            public TextView tvAge;
            public TextView tvPourcent;

            public LinearLayout layout;


            public TextView tvCreate;
            public MyViewHolder(LinearLayout v) {
                super(v);
                tvNom = v.findViewById(R.id.tvNom);
                tvAge = v.findViewById(R.id.tvAge);
                tvPourcent = v.findViewById(R.id.tvPourcent);
                tvCreate = v.findViewById(R.id.tvCreate);
                layout = v;

            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public ItemAdapter() {
            list = new ArrayList<>();
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
            // create a new view
            LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_item, parent, false);
            MyViewHolder vh = new MyViewHolder(v);
            Log.i("DEBOGAGE", "appel a onCreateViewHolder");
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            Item personneCourante = list.get(position);
            holder.tvNom.setText(personneCourante.nom);
            holder.tvAge.setText(""+personneCourante.date);
            holder.tvPourcent.setText(""+personneCourante.pourcentage);// TODO setText sur un integer crash
            holder.tvCreate.setText(""+personneCourante.tempsEcouler);


            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), ConsultationActivity.class);
                    view.getContext().startActivity(i);
                }
            });

            Log.i("DEBOGAGE", "appel a onBindViewHolder " + position);
        }

        // renvoie la taille de la liste
        @Override
        public int getItemCount() {
            return list.size();
        }
}
