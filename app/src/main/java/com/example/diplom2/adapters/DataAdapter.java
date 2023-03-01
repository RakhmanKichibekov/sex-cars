package com.example.diplom2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom2.R;
import com.example.diplom2.ViewHolder;
import com.example.diplom2.ChatNew;
import com.example.diplom2.models.Message;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<ViewHolder> {
    ArrayList<String> messages;
    ArrayList<Message> messages1;
    LayoutInflater inflater;
    View view;

    public DataAdapter(Context context, ArrayList<String> messages) {
        this.messages = messages;
        this.inflater = LayoutInflater.from(context);
    }

    public DataAdapter(ChatNew context, ArrayList<Message> arrayMessages) {
        this.messages1 = arrayMessages;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.message_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String msg = messages.get(position);
        holder.mesageText.setText(msg);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
