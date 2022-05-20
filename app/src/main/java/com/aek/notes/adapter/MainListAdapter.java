package com.aek.notes.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aek.notes.R;
import com.aek.notes.core.util.DateUtils;
import com.aek.notes.model.ModelNote;
import com.aek.notes.viewmodel.ViewModelNote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {
    private final List<ModelNote> noteList;
    private final OnTouchListener onTouchListener;

    @NonNull
    private static <K extends Comparable, V> Map<K, V> sortByKeys(Map<K, V> map) {
        Map<K, V> treeMap = new TreeMap<>((a, b) -> {
            return b.compareTo(a);
        });
        treeMap.putAll(map);
        return treeMap;
    }

    public MainListAdapter(Map<Integer, ModelNote> noteMap, OnTouchListener onTouchListener) {
        this.noteList = new ArrayList<>();
        this.onTouchListener = onTouchListener;

        noteMap = sortByKeys(noteMap);
        noteList.addAll(noteMap.values());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText(noteList.get(position).title);
        holder.content.setText(noteList.get(position).content);
        holder.date.setText(DateUtils.dateToString("MMM d yyyy, HH:mm:ss", new Date(noteList.get(position).createdTime)));

        holder.imgCheck.setVisibility(
                ViewModelNote.getInstance().isSelect(noteList.get(position))
                        ? View.VISIBLE : View.INVISIBLE
        );

        holder.cardItem.setCardBackgroundColor(Color.parseColor(noteList.get(position).colorHex));
        holder.cardItem.setLongClickable(true);
        holder.cardItem.setOnLongClickListener(view -> {
            onTouchListener.onLongTouch(noteList.get(position));
            return true;
        });
        holder.cardItem.setOnClickListener(view -> onTouchListener.onTouch(holder.cardItem,noteList.get(position)));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setDataList(Map<Integer, ModelNote> noteMap) {
        noteList.clear();
        noteMap = sortByKeys(noteMap);
        noteList.addAll(noteMap.values());
    }

    public int updateDataItem(ModelNote model) {
        int index = noteList.indexOf(model);
        noteList.set(index, model);
        return index;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, content, date;
        public CardView cardItem;
        public ImageView imgCheck;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            cardItem = itemView.findViewById(R.id.cardItem);
            title = itemView.findViewById(R.id.tvTitle);
            content = itemView.findViewById(R.id.tvContent);
            date = itemView.findViewById(R.id.tvDate);
            imgCheck = itemView.findViewById(R.id.imgCheck);

            imgCheck.setVisibility(View.INVISIBLE);
        }
    }

    public interface OnTouchListener {
        void onTouch(View view,ModelNote note);

        void onLongTouch(ModelNote note);
    }
}
