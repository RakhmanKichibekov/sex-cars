package com.example.diplom2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.diplom2.adapters.DataAdapter;
import com.example.diplom2.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchCommentUser extends AppCompatActivity {
    private static final int MAX_MESSAGE_LENGTH = 150;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");
    FloatingActionButton floatingActionButtonSend;
    private EditText userNameFileld;
    private ArrayList<String> listData;
    ListView listView;
    private ArrayAdapter<String> adapter;
    EditText editTextMessage;

    RecyclerView messageRecycleView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_comment_user);
        userNameFileld = findViewById(R.id.userNameField);
        floatingActionButtonSend = findViewById(R.id.send_message_btn);
        editTextMessage = findViewById(R.id.message_input);
        listData = new ArrayList<>();
        messageRecycleView = findViewById(R.id.messges_recycler);
//        messageRecycleView.setLayoutManager(new LinearLayoutManager(this));
//        DataAdapter dataAdapter = new DataAdapter(this, listData);
//        messageRecycleView.setAdapter(dataAdapter);
    }

    public void goNumber(View view) {
        Intent intent = new Intent(this, Number.class);
        startActivity(intent);
    }

    public void goComment2(View view) {
        String number = userNameFileld.getText().toString();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (listData.size() > 0) {
                    listData.clear();
                }
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    assert user != null;
                    if (user.getCarNumber().equals(number)) {
                        messageRecycleView.setLayoutManager(new LinearLayoutManager(SearchCommentUser.this));
                        DataAdapter dataAdapter = new DataAdapter(SearchCommentUser.this, listData);
                        messageRecycleView.setAdapter(dataAdapter);
//                        String msg = dataSnapshot.getValue(String.class);
//                        listData.add(msg);
//                        messageRecycleView.smoothScrollToPosition(listData.size());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        myRef.addValueEventListener(listener);
    }

    public void goComment(View view) {
        floatingActionButtonSend.setOnClickListener(new View.OnClickListener() {
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
        String number = userNameFileld.getText().toString();

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println(ds.getValue());
                    User user = ds.getValue(User.class);
                    assert user != null;
                    if (user.getCarNumber().equals(number)) {
                        messageRecycleView.setLayoutManager(new LinearLayoutManager(SearchCommentUser.this));
                        DataAdapter dataAdapter = new DataAdapter(SearchCommentUser.this, listData);
                        messageRecycleView.setAdapter(dataAdapter);
                        String msg = dataSnapshot.getValue(String.class);
                        listData.add(msg);
                        adapter.notifyDataSetChanged();
                        messageRecycleView.smoothScrollToPosition(listData.size());
                    }
                }
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

    public void goCom(View view) {
        Intent intent = new Intent(this, Comments.class);
        startActivity(intent);
    }
}