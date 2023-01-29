package com.example.diplom2;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom2.adapters.DataAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Chats extends AppCompatActivity {

    private static int MAX_MESSAGE_LENGTH = 150;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("messages");
    Button button;
    EditText editTextMessage;
    RecyclerView messageRecycleView;
    ArrayList<String> arrayMessages = new ArrayList<>();


    private String[] namesArr = new String[]{"Moren", "Rakhman", "Polina", "Igor"};
    private ListView listView;

    private GestureDetectorCompat gd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        button = findViewById(R.id.send_message_btn);
        editTextMessage = findViewById(R.id.message_input);
        messageRecycleView = findViewById(R.id.messges_recycler);

        messageRecycleView.setLayoutManager(new LinearLayoutManager(this));
        DataAdapter dataAdapter = new DataAdapter(this, arrayMessages);
        messageRecycleView.setAdapter(dataAdapter);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editTextMessage.getText().toString();
                if (msg.equals("")) {
                    Toast.makeText(getApplicationContext(), "Введите сообщение!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (msg.length() == MAX_MESSAGE_LENGTH) {
                    Toast.makeText(getApplicationContext(), "Длина сообщения до 150 символов!", Toast.LENGTH_SHORT).show();
                    return;
                }

                myRef.push().setValue(msg);
                editTextMessage.setText("");
            }
        });
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                String msg = dataSnapshot.getValue(String.class);
                arrayMessages.add(msg);
                dataAdapter.notifyDataSetChanged();
                messageRecycleView.smoothScrollToPosition(arrayMessages.size());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}