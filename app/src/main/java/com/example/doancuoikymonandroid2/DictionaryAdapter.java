package com.example.doancuoikymonandroid2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.ViewHolder> {
    private List<String> dictionaryList;
    private OnItemClickListener onItemClickListener;

    public DictionaryAdapter(List<String> dictionaryList, OnItemClickListener onItemClickListener) {
        this.dictionaryList = dictionaryList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dictionary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String word = dictionaryList.get(position);
        holder.dictionaryItemTextView.setText(word);

        // Set sự kiện click cho mỗi mục
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(word);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dictionaryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dictionaryItemTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dictionaryItemTextView = itemView.findViewById(R.id.dictionaryItemTextView);
        }
    }

    // Interface để xử lý sự kiện click
    public interface OnItemClickListener {
        void onItemClick(String word);
    }
}


