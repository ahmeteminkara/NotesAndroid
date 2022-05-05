package com.aek.notes.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aek.notes.R;
import com.aek.notes.core.util.DateUtils;
import com.aek.notes.model.ModelNote;

import java.util.Date;
import java.util.List;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {
    private Context context;
    private List<ModelNote> list;
    private OnTouchListener onTouchListener;

    public MainListAdapter(Context context, List<ModelNote> list, OnTouchListener onTouchListener) {
        this.context = context;
        this.list = list;
        this.onTouchListener = onTouchListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.e(MainListAdapter.class.getSimpleName(), "init " + list.get(position).title);

        holder.title.setText(list.get(position).title);
        holder.content.setText(list.get(position).content);
        holder.date.setText(DateUtils.dateToString("MMM d yyyy, HH:mm:ss", new Date(list.get(position).createdTime)));

        holder.cardItem.setCardBackgroundColor(Color.parseColor(list.get(position).colorHex));
        holder.cardItem.setLongClickable(true);
        holder.cardItem.setOnLongClickListener(view -> {
            onTouchListener.onLongTouch(list.get(position));
            return true;
        });
        holder.cardItem.setOnClickListener(view -> onTouchListener.onTouch(list.get(position)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setDataList(List<ModelNote> modelNotes) {
        list.clear();
        list.addAll(modelNotes);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, content, date;
        public CardView cardItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardItem = itemView.findViewById(R.id.cardItem);
            title = itemView.findViewById(R.id.tvTitle);
            content = itemView.findViewById(R.id.tvContent);
            date = itemView.findViewById(R.id.tvDate);
        }
    }

    public static interface OnTouchListener {
        public void onTouch(ModelNote note);

        public void onLongTouch(ModelNote note);
    }
}
