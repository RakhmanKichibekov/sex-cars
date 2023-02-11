package com.example.diplom2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.diplom2.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Number extends AppCompatActivity {
    private EditText userNameFileld;
    ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listData;
    //Создание переменных для БД
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    //Переменная для картинки
    String image;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_number);
        listView = findViewById(R.id.resultTextView);
        userNameFileld = findViewById(R.id.userNameField);

        //Для заполнения списка
        listData = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.name_item, R.id.user_name, listData);
        listView.setAdapter(adapter);
        //setContentView(R.layout.activity_number);

        //Вход в БД
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("users");

        getDataFromDB();

        //Взятие картинки из бд
        image = String.valueOf(db.getReference("image"));
    }

    public void goNumber(View view) {
        String email = userNameFileld.getText().toString();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (listData.size() > 0) {
                    listData.clear();
                }
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    assert user != null;
                    if (user.getEmail().equals(email)) {
                        listData.add(user.getCarNumber());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        users.addValueEventListener(listener);
    }

    public void goAdd(View view) {
        Intent intent = new Intent(this, Add.class);
        startActivity(intent);
    }

    public void goPhotos(View view) {
        Intent intent = new Intent(this, MyPhotos.class);
        startActivity(intent);
    }

    public void goChats(View view) {
        Intent intent = new Intent(this, Chats.class);
        startActivity(intent);
    }

    public void goComment(View view) {
        Intent intent = new Intent(this, SearchCommentUser.class);
        startActivity(intent);
    }

    private void getDataFromDB() {
        //Добавление номера по почте в список
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (listData.size() > 0) {
                    listData.clear();
                }
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    assert user != null;
                    if (Objects.equals(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail(), user.getEmail()))
                        listData.add(user.getCarNumber());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        users.addValueEventListener(listener);
    }

}