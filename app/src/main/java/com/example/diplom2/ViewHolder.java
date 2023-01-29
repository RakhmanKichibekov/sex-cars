package com.example.diplom2;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom2.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView mesageText;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mesageText = itemView.findViewById(R.id.message_item);
    }
}
