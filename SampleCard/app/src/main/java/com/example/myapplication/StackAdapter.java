package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StackAdapter extends RecyclerView.Adapter<StackAdapter.MyViewHolder> {

    Context context;
    List<String> abc;

    StackAdapter(Context context,List<String> abc)
    {
        this.abc=abc;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);

        return new MyViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.text.setText(abc.get(position));

    }

    @Override
    public int getItemCount() {
        return abc.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        public MyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            text=itemView.findViewById(R.id.sampleText);
        }
    }
}
