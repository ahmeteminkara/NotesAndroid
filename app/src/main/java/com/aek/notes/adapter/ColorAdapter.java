package com.aek.notes.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import com.aek.notes.R;

import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.Holder> {
    private final Context context;
    private List<String> colorList;
    private final Consumer<String> onSelect;

    public ColorAdapter(Context context, List<String> colorList, Consumer<String> onSelect) {
        this.context = context;
        this.colorList = colorList;
        this.onSelect = onSelect;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_palette_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Holder viewHolder = (Holder) holder;
        viewHolder.colorItemCard.setCardBackgroundColor(Color.parseColor(colorList.get(position)));
        viewHolder.colorItemCard.setOnClickListener(view -> {
            if (ColorAdapter.this.onSelect != null) ColorAdapter.this.onSelect.accept(colorList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return colorList == null ? 0 : colorList.size();
    }

    public void addColorList(List<String> colorList) {
        this.colorList = colorList;
    }

    public class Holder extends RecyclerView.ViewHolder {

        CardView colorItemCard;

        public Holder(@NonNull View itemView) {
            super(itemView);
            colorItemCard = itemView.findViewById(R.id.colorItemCard);

        }
    }
}
